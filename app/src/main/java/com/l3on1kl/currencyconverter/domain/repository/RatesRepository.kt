package com.l3on1kl.currencyconverter.domain.repository

import com.l3on1kl.currencyconverter.domain.model.Rate
import kotlinx.coroutines.flow.Flow

interface RatesRepository {
    fun observeRates(base: String, amount: Double): Flow<List<Rate>>
}
