package com.l3on1kl.currencyconverter.data.repository

import com.l3on1kl.currencyconverter.data.dataSource.remote.RatesService
import com.l3on1kl.currencyconverter.domain.model.Rate
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive

class RatesRepositoryImpl(
    private val service: RatesService
) : RatesRepository {

    override fun observeRates(base: String, amount: Double): Flow<List<Rate>> = flow {
        val initial = service.getRates(base, amount)
            .map { Rate(it.currency, it.value) }
        emit(initial)

        while (currentCoroutineContext().isActive) {
            delay(1_000)
            val dtos = service.getRates(base, amount)
            val rates = dtos.map { Rate(it.currency, it.value) }
            emit(rates)
        }
    }
}
