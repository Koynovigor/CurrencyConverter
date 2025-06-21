package com.l3on1kl.currencyconverter.domain.repository

import com.l3on1kl.currencyconverter.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun observeAccounts(): Flow<List<Account>>
}
