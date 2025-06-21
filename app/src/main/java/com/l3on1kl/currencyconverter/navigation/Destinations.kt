package com.l3on1kl.currencyconverter.navigation

object Destinations {
    const val Currencies = "currencies"
    const val Profile = "profile"
    const val Exchange = "exchange"
    const val Transactions = "transactions"

    fun Exchange(toCode: String, fromCode: String, amount: Double) =
        "$Exchange/$toCode/$fromCode/$amount"
}
