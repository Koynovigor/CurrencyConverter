package com.l3on1kl.currencyconverter.ui.exchange

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l3on1kl.currencyconverter.domain.model.Exchange
import com.l3on1kl.currencyconverter.domain.usecase.GetRatesFlowUseCase
import com.l3on1kl.currencyconverter.domain.usecase.MakeExchangeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRates: GetRatesFlowUseCase,
    private val makeExchange: MakeExchangeUseCase
) : ViewModel() {

    private val toCode: String = savedStateHandle["to"] ?: error("Missing 'to'")
    private val fromCode: String = savedStateHandle["from"] ?: error("Missing 'from'")
    val amount: Double = savedStateHandle.get<Float>("amount")?.toDouble()
        ?: error("Missing or invalid 'amount'")

    private val _uiState = MutableStateFlow<Exchange?>(null)
    val uiState: StateFlow<Exchange?> = _uiState

    init {
        viewModelScope.launch {
            val rates = getRates(fromCode, 1.0).first()
            val rate = rates.find { it.code == toCode }?.amount?.let { 1 / it } ?: return@launch
            _uiState.value = Exchange(
                fromCode = fromCode,
                toCode = toCode,
                toAmount = amount,
                fromAmount = amount * rate,
                rate = rate
            )
        }
    }

    fun buy() = viewModelScope.launch {
        _uiState.value?.let { makeExchange(it) }
    }
}
