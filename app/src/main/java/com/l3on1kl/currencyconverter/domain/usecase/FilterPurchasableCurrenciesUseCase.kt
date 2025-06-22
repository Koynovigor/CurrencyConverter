package com.l3on1kl.currencyconverter.domain.usecase

import com.l3on1kl.currencyconverter.domain.model.Account
import com.l3on1kl.currencyconverter.domain.model.Rate
import javax.inject.Inject

class FilterPurchasableCurrenciesUseCase @Inject constructor() {
    operator fun invoke(
        rates: List<Rate>,
        accounts: List<Account>
    ): List<Rate> {
        val balance = accounts.associateBy { it.code }
        return rates.filter { rate ->
            (balance[rate.code]?.amount ?: 0.0) >= rate.amount
        }
    }
}
