package com.l3on1kl.currencyconverter.ui.currencies.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.l3on1kl.currencyconverter.domain.entity.Currency
import com.l3on1kl.currencyconverter.domain.entity.stringResName
import com.l3on1kl.currencyconverter.domain.entity.stringResSymbol
import java.text.NumberFormat
import java.util.Locale

@Composable
fun getCurrencyName(code: String): String {
    return Currency.entries.find { it.name == code }?.let {
        stringResource(id = it.stringResName)
    } ?: code
}

@Composable
fun getCurrencySymbol(code: String): String {
    return Currency.entries.find { it.name == code }?.let {
        stringResource(id = it.stringResSymbol)
    } ?: code
}

fun formatAmount(value: Double, maxFractionDigits: Int = 6): String {
    val format = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        maximumFractionDigits = maxFractionDigits
        minimumFractionDigits = 0
        isGroupingUsed = true
    }
    return format.format(value)
}

private val validAmountRegex = Regex("^\\d+(\\.\\d+)?$")
fun isAmountValid(text: String): Boolean {
    val normalized = text.replace(',', '.')
    return validAmountRegex.matches(normalized)
}
