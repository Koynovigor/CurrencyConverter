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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.l3on1kl.currencyconverter.R
import com.l3on1kl.currencyconverter.domain.entity.Currency
import com.l3on1kl.currencyconverter.domain.entity.getFlagResId
import com.l3on1kl.currencyconverter.ui.currencies.CurrencyUiModel
import com.l3on1kl.currencyconverter.ui.theme.BackgroundApp
import com.l3on1kl.currencyconverter.ui.theme.BorderCard
import com.l3on1kl.currencyconverter.ui.theme.GradientMain
import com.l3on1kl.currencyconverter.ui.theme.OnBackgroundApp
import com.l3on1kl.currencyconverter.ui.utils.InlineEditableText
import com.l3on1kl.currencyconverter.ui.utils.SuffixTransformation

@Composable
fun CurrencyRow(
    modifier: Modifier = Modifier,
    rate: CurrencyUiModel,
    onClick: () -> Unit,
    isSelected: Boolean = false,
    enabled: Boolean = isSelected,
    amountText: String,
    isUserAmount: Boolean = false,
    clickEnabled: Boolean = true,
    onAmountChange: (String) -> Unit = { },
    accountAmount: Double? = null,
    widthBorder: Dp = 3.dp,
    colorUnselected: Color = BorderCard,
    onClear: () -> Unit = { },
) {
    val focusRequester = remember { FocusRequester() }
    val focusState = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

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
                    width = widthBorder,
                    brush = if (isSelected) GradientMain else Brush.verticalGradient(
                        listOf(
                            colorUnselected,
                            colorUnselected
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
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = rate.code,
                        style = MaterialTheme.typography.titleLarge,
                        color = OnBackgroundApp
                    )
                    if (accountAmount != null) {
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = stringResource(R.string.balance) +
                                    ": " + formatAmount(accountAmount, 2) +
                                    " ${rate.symbol}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = BorderCard
                        )
                    }
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        InlineEditableText(
                            text = amountText,
                            onTextChange = {
                                val fixed = it.replace(',', '.')
                                if (isSelected) onAmountChange(fixed)
                            },
                            onFocusGained = {
                                if (isSelected) onAmountChange("")
                            },
                            onFocusLostInvalid = {
                                if (isSelected) onClear()
                            },
                            enabled = enabled,
                            readOnly = !isSelected,
                            textStyle = MaterialTheme.typography.titleLarge,
                            textColor = OnBackgroundApp,
                            visualTransformation = SuffixTransformation(" ${rate.symbol}"),
                            modifier = Modifier
                                .alignByBaseline()
                                .focusRequester(focusRequester)
                                .onFocusChanged { focusState.value = it.isFocused }
                        )

                        if (isSelected && isUserAmount) {
                            Spacer(
                                modifier = Modifier.width(12.dp)
                            )
                            IconButton(
                                onClick = {
                                    focusManager.clearFocus()
                                    onClear()
                                },
                                modifier = Modifier
                                    .size(24.dp)
                                    .alignByBaseline(),
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = BorderCard
                                )
                            ) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = null
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(2.dp))

                    Text(
                        text = rate.name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnBackgroundApp.copy(alpha = .87f)
                    )
                }
            }
        }
    }
}
