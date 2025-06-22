package com.l3on1kl.currencyconverter

import com.l3on1kl.currencyconverter.domain.model.Account
import com.l3on1kl.currencyconverter.domain.repository.AccountRepository
import com.l3on1kl.currencyconverter.domain.usecase.ObserveAccountsUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class ObserveAccountsUseCaseTest {

    private lateinit var repository: AccountRepository
    private lateinit var useCase: ObserveAccountsUseCase

    @Before
    fun setUp() {
        repository = mock()
        useCase = ObserveAccountsUseCase(repository)
    }

    @Test
    fun `returns flow of accounts from repository`() = runTest {
        val expectedAccounts = listOf(
            Account(code = "RUB", amount = 500.0),
            Account(code = "USD", amount = 100.0)
        )
        whenever(repository.observeAccounts()).thenReturn(flowOf(expectedAccounts))

        val result = useCase().first()

        assertEquals(expectedAccounts, result)
        verify(repository).observeAccounts()
    }
}
