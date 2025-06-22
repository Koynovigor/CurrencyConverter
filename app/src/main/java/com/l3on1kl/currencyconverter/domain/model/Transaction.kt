package com.l3on1kl.currencyconverter.domain.model

import java.time.LocalDateTime

data class Transaction(
    val from: String,
    val to: String,
    val fromAmount: Double,
    val toAmount: Double,
    val dateTime: LocalDateTime
)