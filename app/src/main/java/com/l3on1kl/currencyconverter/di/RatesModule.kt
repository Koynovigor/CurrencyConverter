package com.l3on1kl.currencyconverter.di

import com.l3on1kl.currencyconverter.data.dataSource.remote.RatesService
import com.l3on1kl.currencyconverter.data.dataSource.remote.RemoteRatesServiceImpl
import com.l3on1kl.currencyconverter.data.repository.RatesRepositoryImpl
import com.l3on1kl.currencyconverter.domain.repository.RatesRepository
import com.l3on1kl.currencyconverter.domain.usecase.GetRatesFlowUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RatesModule {

    @Provides
    fun provideRatesService(): RatesService {
        return object : RatesService {
            val delegate = RemoteRatesServiceImpl()

            override suspend fun getRates(
                baseCurrencyCode: String,
                amount: Double
            ) = delegate.getRates(baseCurrencyCode, amount)
        }
    }

    @Provides
    fun provideRatesRepository(service: RatesService): RatesRepository =
        RatesRepositoryImpl(service)

    @Provides
    fun provideGetRatesUseCase(repo: RatesRepository): GetRatesFlowUseCase =
        GetRatesFlowUseCase(repo)
}
