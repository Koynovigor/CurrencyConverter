package com.l3on1kl.currencyconverter.domain.repository

import com.l3on1kl.currencyconverter.domain.model.Exchange
import com.l3on1kl.currencyconverter.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun makeExchange(data: Exchange)
    fun observeTransactions(): Flow<List<Transaction>>
}
