package com.l3on1kl.currencyconverter.domain.usecase


import com.l3on1kl.currencyconverter.domain.model.Transaction
import com.l3on1kl.currencyconverter.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val repo: TransactionRepository
) {
    operator fun invoke(): Flow<List<Transaction>> = repo.observeTransactions()
}
