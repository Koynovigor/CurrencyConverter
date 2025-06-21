package com.l3on1kl.currencyconverter.domain.usecase

import com.l3on1kl.currencyconverter.data.dataSource.room.account.dao.AccountDao
import com.l3on1kl.currencyconverter.data.dataSource.room.account.dbo.AccountDbo
import javax.inject.Inject

class EnsureInitialCurrencyExistsUseCase @Inject constructor(
    private val accountDao: AccountDao
) {
    suspend operator fun invoke() {
        if (accountDao.getByCode("RUB") == null) {
            accountDao.insertAll(AccountDbo("RUB", 75_000.0))
        }
    }
}
