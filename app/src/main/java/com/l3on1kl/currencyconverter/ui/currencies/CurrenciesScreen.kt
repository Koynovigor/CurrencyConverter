package com.l3on1kl.currencyconverter.ui.currencies

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.l3on1kl.currencyconverter.R
import com.l3on1kl.currencyconverter.domain.model.Rate
import com.l3on1kl.currencyconverter.navigation.Destinations
import com.l3on1kl.currencyconverter.ui.animation.Animations
import com.l3on1kl.currencyconverter.ui.animation.smoothScrollToTop
import com.l3on1kl.currencyconverter.ui.currencies.components.CurrenciesTopAppBar
import com.l3on1kl.currencyconverter.ui.currencies.components.CurrencyRow
import com.l3on1kl.currencyconverter.ui.currencies.components.formatAmount
import com.l3on1kl.currencyconverter.ui.currencies.components.getCurrencyName
import com.l3on1kl.currencyconverter.ui.currencies.components.getCurrencySymbol
import com.l3on1kl.currencyconverter.ui.currency_picker.CurrencyPickerSheet
import com.l3on1kl.currencyconverter.ui.theme.BackgroundApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrenciesScreen(
    navController: NavHostController,
    viewModel: CurrenciesViewModel = hiltViewModel()
) {
    val rates by viewModel.rates.collectAsState()
    val selection by viewModel.selection.collectAsState()
    val baseCode by viewModel.base.collectAsState()
    val sortedRates = rates
        .sortedWith(
            compareBy<Rate> { it.code != selection }
                .thenBy { it.code }
        )

    val listState = rememberLazyListState()
    LaunchedEffect(baseCode) {
        smoothScrollToTop(listState)
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val currency by viewModel.balanceCurrency.collectAsState()
    var sheetVisible by rememberSaveable { mutableStateOf(false) }
    val balance by viewModel.balance.collectAsState()
    val balanceVisible by viewModel.balanceVisible.collectAsState()
    val amountText by viewModel.amountText.collectAsState()

    val accounts by viewModel.accounts.collectAsState(initial = emptyList())

    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundApp)
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Top + WindowInsetsSides.Left + WindowInsetsSides.Right
                )
            )
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                    viewModel.validateOrResetAmount()
                })
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CurrenciesTopAppBar(
                    onHistoryClick = { navController.navigate(Destinations.Profile) },
                    onCurrencyClick = { sheetVisible = true },
                    onToggleBalanceClick = { viewModel.toggleBalanceVisible() },
                    balanceVisible = balanceVisible,
                    scrollBehavior = scrollBehavior,
                    currency = currency,
                    balance = balance
                )
            },
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .background(Color.Transparent),
                state = listState,
                contentPadding = PaddingValues(
                    top = padding.calculateTopPadding() + 16.dp,
                    bottom = padding.calculateBottomPadding() + 16.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(sortedRates, key = { it.code }) { rate ->
                    val accountAmount = accounts.firstOrNull { it.code == rate.code }?.amount

                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically(),
                        modifier = Modifier.animateItem(
                            fadeInSpec = Animations.FadeSpec,
                            placementSpec = Animations.ListItemPlacement,
                            fadeOutSpec = Animations.FadeSpec
                        ),
                    ) {
                        CurrencyRow(
                            rate = CurrencyUiModel(
                                code = rate.code,
                                name = getCurrencyName(rate.code),
                                symbol = getCurrencySymbol(rate.code),
                                amount = rate.amount
                            ),
                            isSelected = rate.code == baseCode,
                            amountText = if (rate.code == baseCode) {
                                amountText
                            } else {
                                formatAmount(rate.amount)
                            },
                            isUserAmount = viewModel.isUserAmount.collectAsState().value,
                            onAmountChange = viewModel::onAmountChange,
                            accountAmount = accountAmount,
                            onClear = viewModel::clearAmount,
                            onClick = {
                                if (viewModel.isUserAmount.value) navController
                                    .navigate(
                                        Destinations.Exchange(
                                            toCode = baseCode,
                                            fromCode = rate.code,
                                            amount = viewModel.amount.value
                                        )
                                    )
                                else viewModel.select(rate.code)
                            }
                        )
                    }
                }
            }
        }
    }

    if (sheetVisible) {
        CurrencyPickerSheet(
            current = currency,
            onSelect = { selected ->
                viewModel.setBalanceCurrency(selected)
            },
            onDismiss = { sheetVisible = false }
        )
    }

}

data class CurrencyUiModel(
    val code: String,
    val name: String,
    val symbol: String,
    val amount: Double
)
