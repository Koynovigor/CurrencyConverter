package com.l3on1kl.currencyconverter

import com.l3on1kl.currencyconverter.domain.model.Account
import com.l3on1kl.currencyconverter.domain.model.Rate
import com.l3on1kl.currencyconverter.domain.usecase.FilterPurchasableCurrenciesUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FilterPurchasableCurrenciesUseCaseTest {

    private lateinit var useCase: FilterPurchasableCurrenciesUseCase

    @Before
    fun setUp() {
        useCase = FilterPurchasableCurrenciesUseCase()
    }

    @Test
    fun `only currencies with sufficient balance are returned`() {
        val accounts = listOf(
            Account(code = "USD", amount = 50.0),
            Account(code = "EUR", amount = 20.0)
        )

        val rates = listOf(
            Rate(code = "USD", amount = 30.0),
            Rate(code = "EUR", amount = 25.0),
            Rate(code = "JPY", amount = 1000.0)
        )

        val result = useCase(rates, accounts)

        assertEquals(1, result.size)
        assertEquals("USD", result.first().code)
    }

    @Test
    fun `returns empty list if no currency has sufficient balance`() {
        val accounts = listOf(
            Account(code = "USD", amount = 10.0)
        )
        val rates = listOf(
            Rate(code = "USD", amount = 50.0)
        )

        val result = useCase(rates, accounts)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `returns all rates if all are affordable`() {
        val accounts = listOf(
            Account(code = "USD", amount = 100.0),
            Account(code = "EUR", amount = 100.0)
        )
        val rates = listOf(
            Rate(code = "USD", amount = 30.0),
            Rate(code = "EUR", amount = 40.0)
        )

        val result = useCase(rates, accounts)

        assertEquals(2, result.size)
        assertTrue(result.any { it.code == "USD" })
        assertTrue(result.any { it.code == "EUR" })
    }
}
