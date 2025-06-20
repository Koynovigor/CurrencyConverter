package com.l3on1kl.currencyconverter.data.repository

import com.l3on1kl.currencyconverter.data.dataSource.room.account.dao.AccountDao
import com.l3on1kl.currencyconverter.domain.model.Account
import com.l3on1kl.currencyconverter.domain.repository.AccountRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val dao: AccountDao
) : AccountRepository {

    override fun observeAccounts() =
        dao.getAllAsFlow()
            .map { list -> list.map { Account(it.code, it.amount) } }
}