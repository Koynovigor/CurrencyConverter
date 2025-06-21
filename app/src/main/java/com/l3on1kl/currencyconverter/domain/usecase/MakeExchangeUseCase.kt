package com.l3on1kl.currencyconverter.domain.usecase

import com.l3on1kl.currencyconverter.domain.model.Exchange
import com.l3on1kl.currencyconverter.domain.repository.TransactionRepository
import javax.inject.Inject

class MakeExchangeUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(data: Exchange) {
        transactionRepository.makeExchange(data)
    }
}
