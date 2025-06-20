package com.l3on1kl.currencyconverter.data.dataSource.remote

import com.l3on1kl.currencyconverter.data.dataSource.remote.dto.RateDto

interface RatesService {
    suspend fun getRates(
        baseCurrencyCode: String,
        amount: Double
    ): List<RateDto>
}