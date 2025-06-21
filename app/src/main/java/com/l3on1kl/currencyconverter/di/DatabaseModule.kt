package com.l3on1kl.currencyconverter.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.l3on1kl.currencyconverter.data.dataSource.room.ConverterDatabase
import com.l3on1kl.currencyconverter.data.dataSource.room.account.dao.AccountDao
import com.l3on1kl.currencyconverter.data.dataSource.room.account.dbo.AccountDbo
import com.l3on1kl.currencyconverter.data.dataSource.room.transaction.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext ctx: Context): ConverterDatabase =
        Room.databaseBuilder(ctx, ConverterDatabase::class.java, "converter.db")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        Room.databaseBuilder(ctx, ConverterDatabase::class.java, "converter.db")
                            .build()
                            .accountDao()
                            .insertAll(AccountDbo("RUB", 75_000.0))
                    }
                }
            })
            .build()

    @Provides
    fun provideAccountDao(db: ConverterDatabase): AccountDao =
        db.accountDao()

    @Provides
    fun provideTransactionDao(db: ConverterDatabase): TransactionDao =
        db.transactionDao()
}
