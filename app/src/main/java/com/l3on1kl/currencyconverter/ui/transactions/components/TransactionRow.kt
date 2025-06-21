package com.l3on1kl.currencyconverter.ui.transactions.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowCircleRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.l3on1kl.currencyconverter.domain.entity.Currency
import com.l3on1kl.currencyconverter.domain.entity.getFlagResId
import com.l3on1kl.currencyconverter.domain.model.Transaction
import com.l3on1kl.currencyconverter.ui.currencies.components.formatAmount
import com.l3on1kl.currencyconverter.ui.currencies.components.getCurrencySymbol
import com.l3on1kl.currencyconverter.ui.theme.BackgroundApp
import com.l3on1kl.currencyconverter.ui.theme.BorderCard
import com.l3on1kl.currencyconverter.ui.theme.OnBackgroundApp
import java.time.format.DateTimeFormatter


@Composable
fun TransactionRow(
    modifier: Modifier = Modifier,
    transaction: Transaction
) {
    val formattedDate = remember(transaction.dateTime) {
        transaction.dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm"))
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.End
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .border(2.dp, BorderCard, RoundedCornerShape(16.dp))
                        .background(BackgroundApp.copy(alpha = 0.87f), RoundedCornerShape(16.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    TransactionCurrencyCard(
                        modifier = Modifier.fillMaxWidth(),
                        code = transaction.from,
                        amount = transaction.fromAmount,
                        reverse = false
                    )
                }

                Spacer(Modifier.width(16.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .border(2.dp, BorderCard, RoundedCornerShape(16.dp))
                        .background(BackgroundApp.copy(alpha = 0.87f), RoundedCornerShape(16.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    TransactionCurrencyCard(
                        modifier = Modifier.fillMaxWidth(),
                        code = transaction.to,
                        amount = transaction.toAmount,
                        reverse = true
                    )
                }
            }

            Icon(
                imageVector = Icons.Outlined.ArrowCircleRight,
                contentDescription = null,
                tint = OnBackgroundApp,
                modifier = Modifier
                    .size(48.dp)
                    .background(BackgroundApp, shape = CircleShape)
                    .zIndex(1f)
            )
        }

        Box(
            modifier = Modifier
                .padding(
                    top = 2.dp,
                    end = 8.dp
                )
                .background(
                    BackgroundApp.copy(alpha = 0.87f),
                    RoundedCornerShape(8.dp)
                )
        ) {
            Text(
                text = formattedDate,
                style = MaterialTheme.typography.bodyMedium,
                color = BorderCard,
                modifier = Modifier.padding(
                    vertical = 2.dp,
                    horizontal = 4.dp
                )
            )
        }

    }
}


@Composable
fun TransactionCurrencyCard(
    modifier: Modifier,
    code: String,
    amount: Double,
    reverse: Boolean = false
) {
    val context = LocalContext.current
    val currency = Currency.entries.firstOrNull { it.name == code }
    val symbol = getCurrencySymbol(code)

    if (currency != null) {
        Column(
            modifier = modifier,
            horizontalAlignment = if (reverse) Alignment.End else Alignment.Start,
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                horizontalArrangement = if (reverse) Arrangement.End else Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!reverse) {
                    Icon(
                        painter = painterResource(id = getFlagResId(context, currency)),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = Color.Unspecified
                    )
                    Spacer(Modifier.width(8.dp))
                }

                Text(
                    text = code,
                    style = MaterialTheme.typography.titleLarge,
                    color = OnBackgroundApp
                )

                if (reverse) {
                    Spacer(Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = getFlagResId(context, currency)),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = Color.Unspecified
                    )
                }
            }

            Text(
                text = formatAmount(amount) + " $symbol",
                style = MaterialTheme.typography.bodyLarge,
                color = OnBackgroundApp,
                textAlign = if (reverse) TextAlign.End else TextAlign.Start
            )

        }
    }
}
