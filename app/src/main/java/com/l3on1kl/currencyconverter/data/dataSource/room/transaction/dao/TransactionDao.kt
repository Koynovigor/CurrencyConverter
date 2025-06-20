package com.l3on1kl.currencyconverter.data.dataSource.room.transaction.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.l3on1kl.currencyconverter.data.dataSource.room.transaction.dbo.TransactionDbo

@Dao
interface TransactionDao {
    @Insert
    suspend fun insertAll(vararg transactions: TransactionDbo)

    @Query("SELECT * FROM transactions")
    suspend fun getAll(): List<TransactionDbo>
}