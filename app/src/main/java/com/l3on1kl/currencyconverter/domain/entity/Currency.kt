package com.l3on1kl.currencyconverter.domain.entity

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.l3on1kl.currencyconverter.R
import com.l3on1kl.currencyconverter.domain.entity.Currency.AUD
import com.l3on1kl.currencyconverter.domain.entity.Currency.BGN
import com.l3on1kl.currencyconverter.domain.entity.Currency.BRL
import com.l3on1kl.currencyconverter.domain.entity.Currency.CAD
import com.l3on1kl.currencyconverter.domain.entity.Currency.CHF
import com.l3on1kl.currencyconverter.domain.entity.Currency.CNY
import com.l3on1kl.currencyconverter.domain.entity.Currency.CZK
import com.l3on1kl.currencyconverter.domain.entity.Currency.DKK
import com.l3on1kl.currencyconverter.domain.entity.Currency.EUR
import com.l3on1kl.currencyconverter.domain.entity.Currency.GBP
import com.l3on1kl.currencyconverter.domain.entity.Currency.HKD
import com.l3on1kl.currencyconverter.domain.entity.Currency.HRK
import com.l3on1kl.currencyconverter.domain.entity.Currency.HUF
import com.l3on1kl.currencyconverter.domain.entity.Currency.IDR
import com.l3on1kl.currencyconverter.domain.entity.Currency.ILS
import com.l3on1kl.currencyconverter.domain.entity.Currency.INR
import com.l3on1kl.currencyconverter.domain.entity.Currency.ISK
import com.l3on1kl.currencyconverter.domain.entity.Currency.JPY
import com.l3on1kl.currencyconverter.domain.entity.Currency.KRW
import com.l3on1kl.currencyconverter.domain.entity.Currency.MXN
import com.l3on1kl.currencyconverter.domain.entity.Currency.MYR
import com.l3on1kl.currencyconverter.domain.entity.Currency.NOK
import com.l3on1kl.currencyconverter.domain.entity.Currency.NZD
import com.l3on1kl.currencyconverter.domain.entity.Currency.PHP
import com.l3on1kl.currencyconverter.domain.entity.Currency.PLN
import com.l3on1kl.currencyconverter.domain.entity.Currency.RON
import com.l3on1kl.currencyconverter.domain.entity.Currency.RUB
import com.l3on1kl.currencyconverter.domain.entity.Currency.SEK
import com.l3on1kl.currencyconverter.domain.entity.Currency.SGD
import com.l3on1kl.currencyconverter.domain.entity.Currency.THB
import com.l3on1kl.currencyconverter.domain.entity.Currency.TRY
import com.l3on1kl.currencyconverter.domain.entity.Currency.USD
import com.l3on1kl.currencyconverter.domain.entity.Currency.ZAR
import com.murgupluoglu.flagkit.FlagKit

enum class Currency {
    USD,
    GBP,
    EUR,
    AUD,
    BGN,
    BRL,
    CAD,
    CHF,
    CNY,
    CZK,
    DKK,
    HKD,
    HRK,
    HUF,
    IDR,
    ILS,
    INR,
    ISK,
    JPY,
    KRW,
    MXN,
    MYR,
    NOK,
    NZD,
    PHP,
    PLN,
    RON,
    RUB,
    SEK,
    SGD,
    THB,
    TRY,
    ZAR,
}

val Currency.stringResName: Int
    @StringRes
    get() = when (this) {
        EUR -> R.string.currency_eur
        AUD -> R.string.currency_aud
        BGN -> R.string.currency_bgn
        BRL -> R.string.currency_brl
        CAD -> R.string.currency_cad
        CHF -> R.string.currency_chf
        CNY -> R.string.currency_cny
        CZK -> R.string.currency_czk
        DKK -> R.string.currency_dkk
        GBP -> R.string.currency_gbp
        HKD -> R.string.currency_hkd
        HRK -> R.string.currency_hrk
        HUF -> R.string.currency_huf
        IDR -> R.string.currency_idr
        ILS -> R.string.currency_ils
        INR -> R.string.currency_inr
        ISK -> R.string.currency_isk
        JPY -> R.string.currency_jpy
        KRW -> R.string.currency_krw
        MXN -> R.string.currency_mxn
        MYR -> R.string.currency_myr
        NOK -> R.string.currency_nok
        NZD -> R.string.currency_nzd
        PHP -> R.string.currency_php
        PLN -> R.string.currency_pln
        RON -> R.string.currency_ron
        RUB -> R.string.currency_rub
        SEK -> R.string.currency_sek
        SGD -> R.string.currency_sgd
        THB -> R.string.currency_thb
        TRY -> R.string.currency_try
        USD -> R.string.currency_usd
        ZAR -> R.string.currency_zar
    }

val Currency.stringResSymbol: Int
    @StringRes
    get() = when (this) {
        EUR -> R.string.symbol_eur
        AUD -> R.string.symbol_aud
        BGN -> R.string.symbol_bgn
        BRL -> R.string.symbol_brl
        CAD -> R.string.symbol_cad
        CHF -> R.string.symbol_chf
        CNY -> R.string.symbol_cny
        CZK -> R.string.symbol_czk
        DKK -> R.string.symbol_dkk
        GBP -> R.string.symbol_gbp
        HKD -> R.string.symbol_hkd
        HRK -> R.string.symbol_hrk
        HUF -> R.string.symbol_huf
        IDR -> R.string.symbol_idr
        ILS -> R.string.symbol_ils
        INR -> R.string.symbol_inr
        ISK -> R.string.symbol_isk
        JPY -> R.string.symbol_jpy
        KRW -> R.string.symbol_krw
        MXN -> R.string.symbol_mxn
        MYR -> R.string.symbol_myr
        NOK -> R.string.symbol_nok
        NZD -> R.string.symbol_nzd
        PHP -> R.string.symbol_php
        PLN -> R.string.symbol_pln
        RON -> R.string.symbol_ron
        RUB -> R.string.symbol_rub
        SEK -> R.string.symbol_sek
        SGD -> R.string.symbol_sgd
        THB -> R.string.symbol_thb
        TRY -> R.string.symbol_try
        USD -> R.string.symbol_usd
        ZAR -> R.string.symbol_zar
    }

@DrawableRes
fun getFlagResId(context: Context, currency: Currency): Int {
    val countryCode = when (currency) {
        USD -> "us"
        GBP -> "gb"
        EUR -> "eu"
        AUD -> "au"
        BGN -> "bg"
        BRL -> "br"
        CAD -> "ca"
        CHF -> "ch"
        CNY -> "cn"
        CZK -> "cz"
        DKK -> "dk"
        HKD -> "hk"
        HRK -> "hr"
        HUF -> "hu"
        IDR -> "id"
        ILS -> "il"
        INR -> "in"
        ISK -> "is"
        JPY -> "jp"
        KRW -> "kr"
        MXN -> "mx"
        MYR -> "my"
        NOK -> "no"
        NZD -> "nz"
        PHP -> "ph"
        PLN -> "pl"
        RON -> "ro"
        RUB -> "ru"
        SEK -> "se"
        SGD -> "sg"
        THB -> "th"
        TRY -> "tr"
        ZAR -> "za"
    }

    return FlagKit.getResId(context, countryCode)
}
