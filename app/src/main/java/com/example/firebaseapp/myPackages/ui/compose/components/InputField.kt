package com.example.firebaseapp.myPackages.ui.compose.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    cornerRadius: Int = 10,
    minHeight: Int = 50,
    bgColor: Color = Color.Transparent,
    isRequired : Boolean = false,
    isSingleLine: Boolean = true,
    showLabel: Boolean = true,
    isValid: Boolean = true,
    isEnabled: Boolean = true,
    hintText: String = "",
    errorMessage: String = "",
    text: String = "",
    clearFocus:Boolean = false,
    onTextChange: (String) -> Unit = {},
) {

    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(text,clearFocus) {
        if (text.isEmpty()||clearFocus) {
            focusManager.clearFocus(force = true)
        }
    }

    Column(modifier.imePadding()) {
        TextField(
            enabled = isEnabled,
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                if (isFocused.not())
                    Text(
                        text = buildAnnotatedString {
                            append(hintText)
                            if (isRequired) {
                                withStyle(style = SpanStyle(color = Red)) {
                                    append("*")
                                }
                            }
                        },
                        color = Gray,
                        style = MaterialTheme.typography.bodyLarge
                    )
            },
            label = {
                if (isFocused.not() || showLabel)
                    Text(
                        text = buildAnnotatedString {
                            append(hintText)
                            if (isRequired) {
                                withStyle(style = SpanStyle(color = Red)) {
                                    append("*")
                                }
                            }
                        },
                        color = Gray,
                        style = MaterialTheme.typography.bodyLarge
                    )
            },

            singleLine = isSingleLine,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.None
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            modifier = modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color =  if (isValid.not()) Red else Gray,
                    RoundedCornerShape(cornerRadius.dp)
                )
                .defaultMinSize(minHeight = minHeight.dp)
                .background(bgColor, RoundedCornerShape(cornerRadius.dp))
                .onFocusChanged { focusState -> isFocused = focusState.isFocused },

            colors = TextFieldDefaults.colors(
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Gray,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedLabelColor = Gray,
                unfocusedLabelColor = Gray,
                disabledTextColor = Gray,
                focusedTextColor = Gray,
                unfocusedTextColor = Gray
            ),

            textStyle = MaterialTheme.typography.bodyLarge
        )

        if (!isValid && errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 10.dp, top = 4.dp)
            )
        }
    }

}
