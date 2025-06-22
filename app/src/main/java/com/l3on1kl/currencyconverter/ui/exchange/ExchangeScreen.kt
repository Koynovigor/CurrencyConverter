package com.l3on1kl.currencyconverter.ui.exchange

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.ArrowCircleDown
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.l3on1kl.currencyconverter.R
import com.l3on1kl.currencyconverter.ui.currencies.CurrencyUiModel
import com.l3on1kl.currencyconverter.ui.currencies.components.CurrencyRow
import com.l3on1kl.currencyconverter.ui.currencies.components.formatAmount
import com.l3on1kl.currencyconverter.ui.currencies.components.getCurrencyName
import com.l3on1kl.currencyconverter.ui.currencies.components.getCurrencySymbol
import com.l3on1kl.currencyconverter.ui.theme.BackgroundApp
import com.l3on1kl.currencyconverter.ui.theme.BorderCard
import com.l3on1kl.currencyconverter.ui.theme.OnBackgroundApp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ExchangeScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<ExchangeViewModel>()
    val state by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundApp)
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Top + WindowInsetsSides.Left + WindowInsetsSides.Right
                )
            ),
        contentAlignment = Alignment.Center,
        content = {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            if (state == null) {
                CircularWavyProgressIndicator(
                    color = OnBackgroundApp,
                    trackColor = BorderCard
                )
            } else {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = stringResource(R.string.exchange)
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
                            scrollBehavior = null,
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = BackgroundApp,
                                titleContentColor = OnBackgroundApp
                            )
                        )
                    },
                    containerColor = Color.Transparent
                ) { padding ->
                    val fromCode = state?.fromCode ?: ""
                    val fromAmount = state?.fromAmount ?: 0.toDouble()
                    val fromSymbol = getCurrencySymbol(fromCode)
                    val toCode = state?.toCode ?: ""
                    val toAmount = state?.toAmount ?: 0.toDouble()
                    val toSymbol = getCurrencySymbol(toCode)

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Transparent)
                            .padding(
                                top = padding.calculateTopPadding() + 16.dp,
                                bottom = padding.calculateBottomPadding() + 16.dp,
                                start = 16.dp,
                                end = 16.dp
                            )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Transparent),
                            contentAlignment = Alignment.Center,
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(32.dp)
                            ) {
                                CurrencyRow(
                                    rate = CurrencyUiModel(
                                        code = fromCode,
                                        name = getCurrencyName(fromCode),
                                        symbol = fromSymbol,
                                        amount = fromAmount
                                    ),
                                    isSelected = true,
                                    enabled = false,
                                    amountText = formatAmount(fromAmount),
                                    widthBorder = 5.dp,
                                    onClick = { }
                                )
                                CurrencyRow(
                                    rate = CurrencyUiModel(
                                        code = toCode,
                                        name = getCurrencyName(toCode),
                                        symbol = toSymbol,
                                        amount = toAmount
                                    ),
                                    isSelected = true,
                                    enabled = false,
                                    amountText = formatAmount(toAmount),
                                    widthBorder = 5.dp,
                                    onClick = { }
                                )
                            }
                            IconButton(
                                modifier = Modifier
                                    .size(96.dp)
                                    .background(
                                        color = Color.Transparent,
                                        shape = CircleShape
                                    ),
                                onClick = {
                                    viewModel.buy()
                                    navController.popBackStack()
                                }
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .background(
                                            color = BackgroundApp,
                                            shape = CircleShape
                                        ),
                                    imageVector = Icons.Rounded.ArrowCircleDown,
                                    contentDescription = stringResource(R.string.exchange),
                                    tint = OnBackgroundApp
                                )
                            }
                        }
                        Text(
                            text = "1 $toSymbol = " + formatAmount(state?.rate ?: 0.toDouble())
                                    + " $fromSymbol",
                            style = MaterialTheme.typography.bodyLarge,
                            color = OnBackgroundApp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                        )
                    }
                }
            }
        }

    )
}
