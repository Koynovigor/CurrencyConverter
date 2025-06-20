package com.l3on1kl.currencyconverter.data.dataSource.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.l3on1kl.currencyconverter.data.dataSource.room.account.dao.AccountDao
import com.l3on1kl.currencyconverter.data.dataSource.room.account.dbo.AccountDbo
import com.l3on1kl.currencyconverter.data.dataSource.room.converter.Converters
import com.l3on1kl.currencyconverter.data.dataSource.room.transaction.dao.TransactionDao
import com.l3on1kl.currencyconverter.data.dataSource.room.transaction.dbo.TransactionDbo

@Database(entities = [AccountDbo::class, TransactionDbo::class], version = 1)
@TypeConverters(Converters::class)
abstract class ConverterDatabase: RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
}