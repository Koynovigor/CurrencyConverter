package com.l3on1kl.currencyconverter

import com.l3on1kl.currencyconverter.domain.model.Rate
import com.l3on1kl.currencyconverter.domain.repository.RatesRepository
import com.l3on1kl.currencyconverter.domain.usecase.GetRatesFlowUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetRatesFlowUseCaseTest {

    private lateinit var repository: RatesRepository
    private lateinit var useCase: GetRatesFlowUseCase

    @Before
    fun setUp() {
        repository = mock()
        useCase = GetRatesFlowUseCase(repository)
    }

    @Test
    fun `returns flow of rates from repository`() = runTest {
        val expectedRates = listOf(
            Rate(code = "USD", amount = 1.0),
            Rate(code = "EUR", amount = 0.85)
        )
        whenever(repository.observeRates("RUB", 100.0)).thenReturn(flowOf(expectedRates))

        val result = useCase("RUB", 100.0).first()

        assertEquals(expectedRates, result)
        verify(repository).observeRates("RUB", 100.0)
    }
}
