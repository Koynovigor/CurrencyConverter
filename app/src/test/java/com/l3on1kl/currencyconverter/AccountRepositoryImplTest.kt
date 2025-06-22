package com.l3on1kl.currencyconverter

import com.l3on1kl.currencyconverter.data.dataSource.room.account.dao.AccountDao
import com.l3on1kl.currencyconverter.data.dataSource.room.account.dbo.AccountDbo
import com.l3on1kl.currencyconverter.data.repository.AccountRepositoryImpl
import com.l3on1kl.currencyconverter.domain.model.Account
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class AccountRepositoryImplTest {

    private lateinit var dao: AccountDao
    private lateinit var repository: AccountRepositoryImpl

    @Before
    fun setUp() {
        dao = mock()
        repository = AccountRepositoryImpl(dao)
    }

    @Test
    fun `observeAccounts maps AccountDbo to Account correctly`() = runTest {
        val dbos = listOf(
            AccountDbo(code = "RUB", amount = 1000.0),
            AccountDbo(code = "USD", amount = 50.0)
        )
        whenever(dao.getAllAsFlow()).thenReturn(flowOf(dbos))

        val result = repository.observeAccounts().first()

        val expected = listOf(
            Account(code = "RUB", amount = 1000.0),
            Account(code = "USD", amount = 50.0)
        )
        assertEquals(expected, result)
        verify(dao).getAllAsFlow()
    }
}
