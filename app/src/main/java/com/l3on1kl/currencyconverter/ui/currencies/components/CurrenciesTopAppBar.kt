package com.l3on1kl.currencyconverter.ui.currencies.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumFlexibleTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.l3on1kl.currencyconverter.R
import com.l3on1kl.currencyconverter.domain.entity.Currency
import com.l3on1kl.currencyconverter.ui.theme.BackgroundApp
import com.l3on1kl.currencyconverter.ui.theme.BorderCard
import com.l3on1kl.currencyconverter.ui.theme.OnBackgroundApp

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class
)
@Composable
fun CurrenciesTopAppBar(
    onHistoryClick: () -> Unit,
    onCurrencyClick: () -> Unit,
    onToggleBalanceClick: () -> Unit,
    balanceVisible: Boolean = true,
    scrollBehavior: TopAppBarScrollBehavior,
    currency: Currency,
    balance: Double
) {
    MediumFlexibleTopAppBar(
        title = {
            Text(stringResource(R.string.app_name))
        },
        subtitle = {
            if (balanceVisible) {
                AssistChip(
                    onClick = onCurrencyClick,
                    label = {
                        Text(
                            text = "${formatAmount(balance, 2)} ${currency.name}",
                            color = BorderCard
                        )
                    },
                    leadingIcon = {
                        Text(
                            getCurrencySymbol(currency.name),
                            color = BorderCard
                        )
                    }
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onToggleBalanceClick) {
                Icon(
                    imageVector = if (balanceVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = stringResource(R.string.toggle_balance),
                    tint = BorderCard
                )
            }
        },
        actions = {
            IconButton(onClick = onHistoryClick) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = BorderCard
                )
            }
        },
        titleHorizontalAlignment = Alignment.CenterHorizontally,
        scrollBehavior = scrollBehavior,
        colors = topAppBarColors(
            containerColor = BackgroundApp,
            scrolledContainerColor = BackgroundApp,
            titleContentColor = OnBackgroundApp,
        )
    )
}
