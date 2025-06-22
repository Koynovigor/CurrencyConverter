package com.l3on1kl.currencyconverter.domain.usecase

import com.l3on1kl.currencyconverter.domain.model.Rate
import com.l3on1kl.currencyconverter.domain.repository.RatesRepository
import kotlinx.coroutines.flow.Flow

class GetRatesFlowUseCase(
    private val repository: RatesRepository
) {
    operator fun invoke(base: String, amount: Double): Flow<List<Rate>> {
        return repository.observeRates(base, amount)
    }
}
