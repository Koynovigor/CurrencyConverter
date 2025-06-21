package com.l3on1kl.currencyconverter.ui.currencies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l3on1kl.currencyconverter.data.repository.CurrencyPrefsRepository
import com.l3on1kl.currencyconverter.domain.entity.Currency
import com.l3on1kl.currencyconverter.domain.model.Rate
import com.l3on1kl.currencyconverter.domain.usecase.EnsureInitialCurrencyExistsUseCase
import com.l3on1kl.currencyconverter.domain.usecase.GetBalanceUseCase
import com.l3on1kl.currencyconverter.domain.usecase.GetRatesFlowUseCase
import com.l3on1kl.currencyconverter.domain.usecase.ObserveAccountsUseCase
import com.l3on1kl.currencyconverter.ui.currencies.components.isAmountValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val currencyPrefsRepository: CurrencyPrefsRepository,
    private val getRates: GetRatesFlowUseCase,
    observeAccounts: ObserveAccountsUseCase,
    getBalanceUseCase: GetBalanceUseCase,
    private val ensureInitialCurrencyExists: EnsureInitialCurrencyExistsUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            ensureInitialCurrencyExists()
        }
    }

    private val _selection = MutableStateFlow(currencyPrefsRepository.loadListSelection())
    val selection: StateFlow<String> = _selection

    private val _base = MutableStateFlow(currencyPrefsRepository.loadListSelection())
    val base: StateFlow<String> = _base

    private val _amount = MutableStateFlow(DEFAULT_AMOUNT)
    val amount: StateFlow<Double> get() = _amount

    private val _isUserAmount = MutableStateFlow(false)
    val isUserAmount: StateFlow<Boolean> = _isUserAmount

    private val _balanceCurrency = MutableStateFlow(currencyPrefsRepository.loadCurrency())
    val balanceCurrency: StateFlow<Currency> = _balanceCurrency

    fun select(code: String) {
        if (!isAmountValid(amountText.value)) {
            _amountText.value = DEFAULT_AMOUNT.toString()
        }
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

    private val _amountText = MutableStateFlow(DEFAULT_AMOUNT.toString())
    val amountText: StateFlow<String> = _amountText

    fun onAmountChange(raw: String) {
        _amountText.value = raw
        _isUserAmount.value = true
        raw.toDoubleOrNull()?.let {
            _amount.value = it.coerceAtLeast(0.0)
        }
    }

    fun clearAmount() {
        _amountText.value = DEFAULT_AMOUNT.toString()
        _amount.value = DEFAULT_AMOUNT
        _isUserAmount.value = false
    }

    val accounts = observeAccounts()

    @OptIn(ExperimentalCoroutinesApi::class)
    val rates: StateFlow<List<Rate>> =
        combine(_base, _amount) { base, amount -> base to amount }
            .flatMapLatest { (base, amount) -> getRates(base, amount) }
            .combine(_amount) { rates, amount -> rates to amount }
            .combine(accounts) { (rates, _), accs ->
                val baseCode = _base.value
                if (!_isUserAmount.value) {
                    rates
                } else {
                    val balance = accs.associateBy { it.code }
                    rates.filter { rate ->
                        rate.code == baseCode ||
                                (balance[rate.code]?.amount ?: 0.0) >= rate.amount
                    }
                }
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())


    fun validateOrResetAmount() {
        if (!isAmountValid(amountText.value)) {
            _amountText.value = DEFAULT_AMOUNT.toString()
        }
    }

    private companion object {
        const val DEFAULT_AMOUNT = 1.0
    }
}
