package com.l3on1kl.currencyconverter.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.l3on1kl.currencyconverter.R
import com.l3on1kl.currencyconverter.navigation.Destinations
import com.l3on1kl.currencyconverter.ui.currencies.CurrenciesViewModel
import com.l3on1kl.currencyconverter.ui.currencies.CurrencyUiModel
import com.l3on1kl.currencyconverter.ui.currencies.components.CurrencyRow
import com.l3on1kl.currencyconverter.ui.currencies.components.formatAmount
import com.l3on1kl.currencyconverter.ui.currencies.components.getCurrencyName
import com.l3on1kl.currencyconverter.ui.currencies.components.getCurrencySymbol
import com.l3on1kl.currencyconverter.ui.currency_picker.CurrencyPickerSheet
import com.l3on1kl.currencyconverter.ui.theme.BackgroundApp
import com.l3on1kl.currencyconverter.ui.theme.BorderCard
import com.l3on1kl.currencyconverter.ui.theme.OnBackgroundApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val parentEntry = remember(navController.currentBackStackEntry) {
        navController.getBackStackEntry(Destinations.Currencies)
    }
    val currencyViewModel: CurrenciesViewModel = hiltViewModel(parentEntry)

    val accounts by viewModel.accounts.collectAsState()
    val currency by currencyViewModel.balanceCurrency.collectAsState()
    val balance by currencyViewModel.balance.collectAsState()
    val balanceVisible by currencyViewModel.balanceVisible.collectAsState()
    var sheetVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundApp)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.profile)
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back),
                                tint = BorderCard
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                navController.navigate(Destinations.Transactions)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = stringResource(R.string.history),
                                tint = BorderCard
                            )
                        }
                    },
                    scrollBehavior = null,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = BackgroundApp,
                        titleContentColor = OnBackgroundApp
                    )
                )
            },
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .background(Color.Transparent),
                contentPadding = PaddingValues(
                    top = padding.calculateTopPadding() + 16.dp,
                    bottom = padding.calculateBottomPadding() + 16.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.balance) + ":",
                            color = OnBackgroundApp,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            if (balanceVisible) {
                                AssistChip(
                                    onClick = { sheetVisible = true },
                                    label = {
                                        Text(
                                            text = "${formatAmount(balance, 2)} ${currency.name}",
                                            color = OnBackgroundApp,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    },
                                    leadingIcon = {
                                        Text(
                                            getCurrencySymbol(currency.name),
                                            color = OnBackgroundApp,
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                    },
                                )
                            }
                            IconButton(
                                onClick = {
                                    currencyViewModel.toggleBalanceVisible()
                                }
                            ) {
                                Icon(
                                    imageVector = if (balanceVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = stringResource(R.string.toggle_balance),
                                    tint = BorderCard
                                )
                            }
                        }
                    }
                }


                item {
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    Text(
                        text = stringResource(R.string.assets) + ":",
                        color = OnBackgroundApp,
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                items(accounts, key = { it.code }) { acc ->
                    CurrencyRow(
                        rate = CurrencyUiModel(
                            code = acc.code,
                            name = getCurrencyName(acc.code),
                            symbol = getCurrencySymbol(acc.code),
                            amount = acc.amount
                        ),
                        amountText = formatAmount(acc.amount),
                        onClick = { },
                        clickEnabled = false
                    )
                }
            }
        }

        if (sheetVisible) {
            CurrencyPickerSheet(
                current = currency,
                onSelect = { selected ->
                    currencyViewModel.setBalanceCurrency(selected)
                },
                onDismiss = { sheetVisible = false }
            )
        }

    }
}
