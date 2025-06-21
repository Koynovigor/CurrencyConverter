package com.l3on1kl.currencyconverter.data.repository

import com.l3on1kl.currencyconverter.data.dataSource.room.account.dao.AccountDao
import com.l3on1kl.currencyconverter.data.dataSource.room.account.dbo.AccountDbo
import com.l3on1kl.currencyconverter.data.dataSource.room.transaction.dao.TransactionDao
import com.l3on1kl.currencyconverter.data.dataSource.room.transaction.dbo.TransactionDbo
import com.l3on1kl.currencyconverter.domain.model.Exchange
import com.l3on1kl.currencyconverter.domain.model.Transaction
import com.l3on1kl.currencyconverter.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao,
    private val transactionDao: TransactionDao
) : TransactionRepository {

    override suspend fun makeExchange(data: Exchange) = withContext(Dispatchers.IO) {
        val currentFromBalance = accountDao.getByCode(data.fromCode)?.amount ?: 0.0
        if (currentFromBalance < data.fromAmount) return@withContext

        transactionDao.insertAll(
            TransactionDbo(
                id = 0,
                from = data.fromCode,
                to = data.toCode,
                fromAmount = data.fromAmount,
                toAmount = data.toAmount,
                dateTime = LocalDateTime.now()
            )
        )

        if (accountDao.getByCode(data.toCode) == null) {
            accountDao.insertAll(AccountDbo(data.toCode, 0.0))
        }

        accountDao.updateBalance(data.fromCode, -data.fromAmount)
        accountDao.updateBalance(data.toCode, +data.toAmount)
    }

    override fun observeTransactions(): Flow<List<Transaction>> =
        transactionDao.getAllAsFlow().map { db ->
            db.map {
                Transaction(
                    from = it.from,
                    to = it.to,
                    fromAmount = it.fromAmount,
                    toAmount = it.toAmount,
                    dateTime = it.dateTime
                )
            }
        }

}
