package com.l3on1kl.currencyconverter

import com.l3on1kl.currencyconverter.domain.entity.Currency
import com.l3on1kl.currencyconverter.domain.model.Account
import com.l3on1kl.currencyconverter.domain.model.Rate
import com.l3on1kl.currencyconverter.domain.repository.AccountRepository
import com.l3on1kl.currencyconverter.domain.usecase.GetBalanceUseCase
import com.l3on1kl.currencyconverter.domain.usecase.GetRatesFlowUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetBalanceUseCaseTest {

    private lateinit var accountRepository: AccountRepository
    private lateinit var getRatesFlowUseCase: GetRatesFlowUseCase
    private lateinit var useCase: GetBalanceUseCase

    @Before
    fun setUp() {
        accountRepository = mock()
        getRatesFlowUseCase = mock()
        useCase = GetBalanceUseCase(accountRepository, getRatesFlowUseCase)
    }

    @Test
    fun `calculates balance with direct and converted currencies`() = runTest {
        val accounts = listOf(
            Account(code = "RUB", amount = 1000.0),
            Account(code = "USD", amount = 10.0)
        )
        val rates = listOf(
            Rate(code = "USD", amount = 0.01)
        )

        whenever(accountRepository.observeAccounts()).thenReturn(flowOf(accounts))
        whenever(getRatesFlowUseCase("RUB", 1.0)).thenReturn(flowOf(rates))

        val balanceFlow = useCase(flowOf(Currency.RUB))

        val balance = balanceFlow.first()

        assertEquals(2000.0, balance, 0.001)
    }

    @Test
    fun `ignores accounts with missing rate`() = runTest {
        val accounts = listOf(
            Account(code = "RUB", amount = 1000.0),
            Account(code = "EUR", amount = 10.0)
        )

        whenever(accountRepository.observeAccounts()).thenReturn(flowOf(accounts))
        whenever(getRatesFlowUseCase("RUB", 1.0)).thenReturn(flowOf(emptyList()))

        val balanceFlow = useCase(flowOf(Currency.RUB))
        val balance = balanceFlow.first()

        assertEquals(1000.0, balance, 0.001)
    }

    @Test
    fun `ignores zero-rate currencies`() = runTest {
        val accounts = listOf(
            Account(code = "RUB", amount = 1000.0),
            Account(code = "EUR", amount = 10.0)
        )
        val rates = listOf(
            Rate(code = "EUR", amount = 0.0)
        )

        whenever(accountRepository.observeAccounts()).thenReturn(flowOf(accounts))
        whenever(getRatesFlowUseCase("RUB", 1.0)).thenReturn(flowOf(rates))

        val balanceFlow = useCase(flowOf(Currency.RUB))
        val balance = balanceFlow.first()

        assertEquals(1000.0, balance, 0.001)
    }
}
