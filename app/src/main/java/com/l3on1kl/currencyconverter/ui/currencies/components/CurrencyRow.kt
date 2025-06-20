package com.l3on1kl.currencyconverter.ui.currencies.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.l3on1kl.currencyconverter.domain.entity.Currency
import com.l3on1kl.currencyconverter.domain.entity.getFlagResId
import com.l3on1kl.currencyconverter.ui.currencies.CurrencyUiModel
import com.l3on1kl.currencyconverter.ui.theme.BackgroundApp
import com.l3on1kl.currencyconverter.ui.theme.BorderCard
import com.l3on1kl.currencyconverter.ui.theme.GradientMain
import com.l3on1kl.currencyconverter.ui.theme.OnBackgroundApp

@Composable
fun CurrencyRow(
    modifier: Modifier = Modifier,
    rate: CurrencyUiModel,
    onClick: () -> Unit,
    selected: Boolean = false,
    clickEnabled: Boolean = true
) {

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                enabled = clickEnabled
            ),
        shape = MaterialTheme.shapes.small,
        color = Color.Transparent,
        contentColor = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .background(
                    BackgroundApp.copy(alpha = 0.87f),
                )
                .border(
                    width = 3.dp,
                    brush = if (selected) GradientMain else Brush.verticalGradient(
                        listOf(
                            BorderCard,
                            BorderCard
                        )
                    ),
                    shape = MaterialTheme.shapes.small
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(
                    id = getFlagResId(
                        LocalContext.current,
                        Currency.valueOf(rate.code)
                    )
                ),
                contentDescription = rate.name,
                modifier = Modifier.size(48.dp),
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.width(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = rate.code,
                    style = MaterialTheme.typography.titleLarge,
                    color = OnBackgroundApp
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "${formatAmount(rate.amount)} ${rate.symbol}",
                        style = MaterialTheme.typography.titleLarge,
                        color = OnBackgroundApp
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = rate.name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnBackgroundApp.copy(alpha = 0.87f)
                    )
                }
            }
        }
    }
}
