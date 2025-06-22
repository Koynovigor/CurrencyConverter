package com.l3on1kl.currencyconverter

import com.l3on1kl.currencyconverter.domain.model.Exchange
import com.l3on1kl.currencyconverter.domain.repository.TransactionRepository
import com.l3on1kl.currencyconverter.domain.usecase.MakeExchangeUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class MakeExchangeUseCaseTest {

    private lateinit var repository: TransactionRepository
    private lateinit var useCase: MakeExchangeUseCase

    @Before
    fun setUp() {
        repository = mock()
        useCase = MakeExchangeUseCase(repository)
    }

    @Test
    fun `invokes repository with correct exchange data`() = runTest {
        val exchange = Exchange(
            fromCode = "RUB",
            toCode = "USD",
            fromAmount = 1000.0,
            toAmount = 13.0,
            rate = 76.9
        )

        useCase(exchange)

        verify(repository).makeExchange(exchange)
    }
}
