package com.l3on1kl.currencyconverter

import com.l3on1kl.currencyconverter.data.dataSource.room.account.dao.AccountDao
import com.l3on1kl.currencyconverter.data.dataSource.room.account.dbo.AccountDbo
import com.l3on1kl.currencyconverter.data.dataSource.room.transaction.dao.TransactionDao
import com.l3on1kl.currencyconverter.data.dataSource.room.transaction.dbo.TransactionDbo
import com.l3on1kl.currencyconverter.data.repository.TransactionRepositoryImpl
import com.l3on1kl.currencyconverter.domain.model.Exchange
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class TransactionRepositoryImplTest {

    private lateinit var accountDao: AccountDao
    private lateinit var transactionDao: TransactionDao
    private lateinit var repository: TransactionRepositoryImpl

    @Before
    fun setUp() {
        accountDao = mock()
        transactionDao = mock()
        repository = TransactionRepositoryImpl(accountDao, transactionDao)
    }

    @Test
    fun `makeExchange inserts transaction and updates balances if fromAccount has enough`() =
        runTest {
            val exchange = Exchange(
                fromCode = "RUB",
                toCode = "USD",
                fromAmount = 1000.0,
                toAmount = 13.0,
                rate = 76.9
            )

            whenever(accountDao.getByCode("RUB")).thenReturn(AccountDbo("RUB", 2000.0))
            whenever(accountDao.getByCode("USD")).thenReturn(null)

            repository.makeExchange(exchange)

            val captor = argumentCaptor<Array<out TransactionDbo>>()
            verify(transactionDao).insertAll(*captor.capture())

            val inserted = captor.firstValue.single()
            assert(inserted.from == "RUB")
            assert(inserted.to == "USD")
            assert(inserted.fromAmount == 1000.0)
            assert(inserted.toAmount == 13.0)

            verify(accountDao).insertAll(AccountDbo("USD", 0.0))
            verify(accountDao).updateBalance("RUB", -1000.0)
            verify(accountDao).updateBalance("USD", 13.0)
        }

    @Test
    fun `makeExchange does nothing if not enough balance`() = runTest {
        val exchange = Exchange(
            fromCode = "RUB",
            toCode = "USD",
            fromAmount = 1000.0,
            toAmount = 13.0,
            rate = 76.9
        )

        whenever(accountDao.getByCode("RUB")).thenReturn(AccountDbo("RUB", 500.0))

        repository.makeExchange(exchange)

        verify(transactionDao, never()).insertAll(any())
        verify(accountDao, never()).updateBalance(any(), any())
        verify(accountDao, never()).insertAll(any())
    }
}
