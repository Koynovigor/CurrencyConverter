package com.l3on1kl.currencyconverter.data.repository

import android.content.Context
import androidx.core.content.edit
import com.l3on1kl.currencyconverter.domain.entity.Currency
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CurrencyPrefsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val CURRENCY_KEY = "currency_prefs"
    private val SELECTED_LIST_CODE_KEY = "selected_list_currency"
    private val BALANCE_VISIBLE_KEY = "balance_visible"

    private val prefs = context.getSharedPreferences(CURRENCY_KEY, Context.MODE_PRIVATE)

    fun saveCurrency(currency: Currency) {
        prefs.edit { putString(CURRENCY_KEY, currency.name) }
    }

    fun loadCurrency(): Currency {
        val code = prefs.getString(CURRENCY_KEY, null)
        return Currency.entries.firstOrNull { it.name == code } ?: Currency.RUB
    }


    fun saveListSelection(code: String) {
        prefs.edit { putString(SELECTED_LIST_CODE_KEY, code) }
    }

    fun loadListSelection(): String {
        return prefs.getString(SELECTED_LIST_CODE_KEY, null) ?: Currency.RUB.name
    }


    fun saveBalanceVisible(visible: Boolean) {
        prefs.edit { putBoolean(BALANCE_VISIBLE_KEY, visible) }
    }

    fun loadBalanceVisible(): Boolean {
        return prefs.getBoolean(BALANCE_VISIBLE_KEY, true)
    }
}
