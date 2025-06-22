package com.l3on1kl.currencyconverter.domain.usecase

import com.l3on1kl.currencyconverter.domain.repository.AccountRepository
import javax.inject.Inject

class ObserveAccountsUseCase @Inject constructor(
    private val repo: AccountRepository
) {
    operator fun invoke() = repo.observeAccounts()
}
