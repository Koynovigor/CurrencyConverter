package com.l3on1kl.currencyconverter.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.l3on1kl.currencyconverter.ui.currencies.components.isAmountValid

@Composable
fun InlineEditableText(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    onFocusGained: () -> Unit = {},
    onFocusLostInvalid: () -> Unit = {},
    defaultValue: String = "1.0",
    textStyle: TextStyle = TextStyle.Default,
    textColor: Color = LocalContentColor.current,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    var hasFocus by remember { mutableStateOf(false) }

    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        enabled = enabled,
        readOnly = readOnly,
        singleLine = true,
        textStyle = textStyle.copy(
            color = textColor,
            textAlign = TextAlign.End
        ),
        visualTransformation = visualTransformation,
        cursorBrush = SolidColor(textColor),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
            .onFocusChanged { state ->
                val isNowFocused = state.isFocused

                if (isNowFocused && !hasFocus) {
                    if (text == defaultValue) {
                        onTextChange("")
                    }
                    onFocusGained()
                }

                if (!isNowFocused && hasFocus) {
                    if (text.isBlank() || !isAmountValid(text)) {
                        onTextChange(defaultValue)
                        onFocusLostInvalid()
                    }
                }

                hasFocus = isNowFocused
            }
            .background(Color.Transparent)
            .padding(0.dp),
        decorationBox = { innerTextField ->
            innerTextField()
        }
    )
}

