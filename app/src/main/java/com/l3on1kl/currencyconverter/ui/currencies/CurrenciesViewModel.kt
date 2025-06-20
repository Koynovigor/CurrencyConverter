package com.l3on1kl.currencyconverter.ui.currencies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l3on1kl.currencyconverter.data.repository.CurrencyPrefsRepository
import com.l3on1kl.currencyconverter.domain.entity.Currency
import com.l3on1kl.currencyconverter.domain.model.Rate
import com.l3on1kl.currencyconverter.domain.usecase.GetBalanceUseCase
import com.l3on1kl.currencyconverter.domain.usecase.GetRatesFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val currencyPrefsRepository: CurrencyPrefsRepository,
    private val getRatesFlowUseCase: GetRatesFlowUseCase,
    getBalanceUseCase: GetBalanceUseCase
) : ViewModel() {

    private val _selection = MutableStateFlow(currencyPrefsRepository.loadListSelection())
    val selection: StateFlow<String> = _selection

    private val _base = MutableStateFlow(currencyPrefsRepository.loadListSelection())
    val base: StateFlow<String> = _base

    private val _amount = MutableStateFlow(1.0)

    private val _balanceCurrency = MutableStateFlow(currencyPrefsRepository.loadCurrency())
    val balanceCurrency: StateFlow<Currency> = _balanceCurrency

    fun select(code: String) {
        _selection.value = code
        _base.value = code
        currencyPrefsRepository.saveListSelection(code)
    }

    val balance: StateFlow<Double> = getBalanceUseCase(_balanceCurrency)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0.0)

    fun setBalanceCurrency(currency: Currency) {
        _balanceCurrency.value = currency
        currencyPrefsRepository.saveCurrency(currency)
    }

    private val _balanceVisible = MutableStateFlow(currencyPrefsRepository.loadBalanceVisible())
    val balanceVisible: StateFlow<Boolean> = _balanceVisible
    fun toggleBalanceVisible() {
        val new = !_balanceVisible.value
        _balanceVisible.value = new
        currencyPrefsRepository.saveBalanceVisible(new)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val rates: StateFlow<List<Rate>> = combine(_base, _amount) { base, amount ->
        base to amount
    }.flatMapLatest { (base, amount) ->
        getRatesFlowUseCase(base, amount)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )
}
