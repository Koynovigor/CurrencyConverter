package com.l3on1kl.currencyconverter.domain.model

data class Exchange(
    val fromCode: String,
    val toCode: String,
    val fromAmount: Double,
    val toAmount: Double,
    val rate: Double
)
