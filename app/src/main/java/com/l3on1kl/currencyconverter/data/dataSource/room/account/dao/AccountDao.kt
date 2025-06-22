package com.l3on1kl.currencyconverter.data.dataSource.room.account.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.l3on1kl.currencyconverter.data.dataSource.room.account.dbo.AccountDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg accounts: AccountDbo)

    @Query("SELECT * FROM accounts")
    suspend fun getAll(): List<AccountDbo>

    @Query("SELECT * FROM accounts")
    fun getAllAsFlow(): Flow<List<AccountDbo>>

    @Query("UPDATE accounts SET amount = amount + :delta WHERE currency_code = :code")
    suspend fun updateBalance(code: String, delta: Double)

    @Query("SELECT * FROM accounts WHERE currency_code = :code LIMIT 1")
    suspend fun getByCode(code: String): AccountDbo?
}