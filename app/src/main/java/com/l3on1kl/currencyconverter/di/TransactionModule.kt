package com.l3on1kl.currencyconverter.di

import com.l3on1kl.currencyconverter.data.dataSource.room.account.dao.AccountDao
import com.l3on1kl.currencyconverter.data.dataSource.room.transaction.dao.TransactionDao
import com.l3on1kl.currencyconverter.data.repository.TransactionRepositoryImpl
import com.l3on1kl.currencyconverter.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TransactionModule {

    @Provides
    @Singleton
    fun provideTransactionRepository(
        transactionDao: TransactionDao,
        accountDao: AccountDao
    ): TransactionRepository {
        return TransactionRepositoryImpl(accountDao, transactionDao)
    }
}
