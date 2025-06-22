package com.l3on1kl.currencyconverter

import com.l3on1kl.currencyconverter.data.dataSource.room.account.dao.AccountDao
import com.l3on1kl.currencyconverter.data.dataSource.room.account.dbo.AccountDbo
import com.l3on1kl.currencyconverter.domain.usecase.EnsureInitialCurrencyExistsUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class EnsureInitialCurrencyExistsUseCaseTest {

    private lateinit var accountDao: AccountDao
    private lateinit var useCase: EnsureInitialCurrencyExistsUseCase

    @Before
    fun setup() {
        accountDao = mock()
        useCase = EnsureInitialCurrencyExistsUseCase(accountDao)
    }

    @Test
    fun `does not insert RUB if it already exists`() = runTest {
        whenever(accountDao.getByCode("RUB")).thenReturn(AccountDbo("RUB", 100.0))

        useCase()

        verify(accountDao, never()).insertAll(any())
    }

    @Test
    fun `inserts RUB if it does not exist`() = runTest {
        whenever(accountDao.getByCode("RUB")).thenReturn(null)

        useCase()

        verify(accountDao).insertAll(AccountDbo("RUB", 75_000.0))
    }
}
