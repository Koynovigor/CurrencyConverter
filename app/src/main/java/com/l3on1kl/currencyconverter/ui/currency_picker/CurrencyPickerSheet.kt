package com.l3on1kl.currencyconverter.ui.currency_picker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.l3on1kl.currencyconverter.R
import com.l3on1kl.currencyconverter.domain.entity.Currency
import com.l3on1kl.currencyconverter.ui.currencies.components.getCurrencySymbol
import com.l3on1kl.currencyconverter.ui.theme.BackgroundApp
import com.l3on1kl.currencyconverter.ui.theme.BorderCard
import com.l3on1kl.currencyconverter.ui.theme.GradientMain
import com.l3on1kl.currencyconverter.ui.theme.OnBackgroundApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyPickerSheet(
    current: Currency,
    onSelect: (Currency) -> Unit,
    onDismiss: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selected by rememberSaveable { mutableStateOf(current) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = BackgroundApp.copy(),
        contentColor = OnBackgroundApp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.choose_currency),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = "${getCurrencySymbol(selected.name)} ${selected.name}",
                    onValueChange = {},
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 3.dp,
                            color = BorderCard,
                            shape = MaterialTheme.shapes.small
                        )
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent,
                        errorBorderColor = MaterialTheme.colorScheme.error,
                        focusedTrailingIconColor = OnBackgroundApp,
                        unfocusedTrailingIconColor = OnBackgroundApp,
                        disabledTrailingIconColor = OnBackgroundApp,
                        errorTrailingIconColor = MaterialTheme.colorScheme.error,
                        focusedTextColor = OnBackgroundApp,
                        unfocusedTextColor = OnBackgroundApp,
                        disabledTextColor = OnBackgroundApp,
                        errorTextColor = MaterialTheme.colorScheme.error,
                    )
                )

                ExposedDropdownMenu(
                    modifier = Modifier
                        .padding(16.dp),
                    matchAnchorWidth = false,
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    containerColor = BackgroundApp,
                    border = BorderStroke(
                        width = 3.dp,
                        color = BorderCard
                    ),
                    shape = MaterialTheme.shapes.medium,
                ) {
                    Currency.entries.forEach { currency ->
                        DropdownMenuItem(
                            onClick = {
                                selected = currency
                                expanded = false
                            },
                            text = {
                                Row(Modifier.fillMaxWidth()) {
                                    Text(
                                        "${getCurrencySymbol(currency.name)} ${currency.name}",
                                        style = if (currency == selected) MaterialTheme.typography.bodyLarge
                                        else MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.weight(1f),
                                        color = OnBackgroundApp
                                    )
                                    if (currency == selected) {
                                        Icon(
                                            Icons.Filled.Check,
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp),
                                            tint = OnBackgroundApp
                                        )
                                    }
                                }
                            },
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    if (selected != current) onSelect(selected)
                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 3.dp,
                        brush = GradientMain,
                        shape = MaterialTheme.shapes.small
                    ),
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = BackgroundApp
                )
            ) {
                Text(
                    stringResource(R.string.action_select),
                    color = OnBackgroundApp,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
