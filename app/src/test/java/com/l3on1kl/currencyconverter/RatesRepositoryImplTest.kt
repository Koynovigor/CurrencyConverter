package com.l3on1kl.currencyconverter

import com.l3on1kl.currencyconverter.data.dataSource.remote.RatesService
import com.l3on1kl.currencyconverter.data.dataSource.remote.dto.RateDto
import com.l3on1kl.currencyconverter.data.repository.RatesRepositoryImpl
import com.l3on1kl.currencyconverter.domain.model.Rate
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class RatesRepositoryImplTest {

    private lateinit var service: RatesService
    private lateinit var repository: RatesRepositoryImpl

    @Before
    fun setUp() {
        service = mock()
        repository = RatesRepositoryImpl(service)
    }

    @Test
    fun `observeRates emits mapped rates from service`() = runTest {
        val base = "RUB"
        val amount = 100.0
        val dtos = listOf(
            RateDto("USD", 1.23),
            RateDto("EUR", 1.11)
        )
        whenever(service.getRates(base, amount)).thenReturn(dtos)

        val result = repository.observeRates(base, amount).first()

        val expected = listOf(
            Rate(code = "USD", amount = 1.23),
            Rate(code = "EUR", amount = 1.11)
        )
        assertEquals(expected, result)
        verify(service).getRates(base, amount)
    }
}
