package com.l3on1kl.currencyconverter.domain.usecase

import com.l3on1kl.currencyconverter.domain.entity.Currency
import com.l3on1kl.currencyconverter.domain.repository.AccountRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class GetBalanceUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val getRatesFlowUseCase: GetRatesFlowUseCase
) {
    operator fun invoke(balanceCurrencyFlow: Flow<Currency>): Flow<Double> {
        val accountsFlow = accountRepository.observeAccounts()

        return combine(accountsFlow, balanceCurrencyFlow) { accounts, targetCurrency ->
            accounts to targetCurrency.name
        }.flatMapLatest { (accounts, targetCode) ->
            getRatesFlowUseCase(base = targetCode, amount = 1.0)
                .map { rates ->
                    accounts.sumOf { acc ->
                        when (acc.code) {
                            targetCode -> acc.amount
                            else -> {
                                val rate = rates.firstOrNull { it.code == acc.code }?.amount
                                if (rate != null && rate != 0.0)
                                    acc.amount / rate
                                else 0.0
                            }
                        }
                    }
                }
        }
    }
}
