package com.example.firebaseapp.myPackages.ui.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.firebaseapp.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteBottomSheet(
    onClick: () -> Unit,
    btnName:String,
    title:String,
    content: String,
    onDismiss: () -> Unit,
    isLoading: Boolean,
    onContentChange: (String) -> Unit,
    onTitleChange: (String) -> Unit,
) {
   var titleError by remember { mutableStateOf("") }
   var contentError by remember { mutableStateOf("") }
    val context = LocalContext.current

    if(title.isNotEmpty())
        titleError = ""

    if(content.isNotEmpty())
        contentError = ""

    CustomBottomSheet(onDismiss = onDismiss) {
        Box {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // ##  Button
                    Box(
                        modifier = Modifier
                            .clickable {
                                if (title.isNotEmpty() && content.isNotEmpty())
                                    onClick()
                                else {
                                    if (title.isEmpty())
                                        titleError = context.getString(R.string.title_is_empty)
                                    if (content.isEmpty())
                                        contentError = context.getString(R.string.content_is_empty)
                                }
                            }
                            .width(100.dp)
                            .height(38.dp)
                            .background(
                                color = Color(0xFF558B2F),
                                shape = RoundedCornerShape(150.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isLoading) {
                            LoadingView()
                        } else {
                            Text(
                                text = btnName,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White,
                                modifier = Modifier
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                    // ## Cancel Button
                    Text(
                        text = stringResource(R.string.cancel),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF558B2F),
                        modifier = Modifier.clickable {
                            onDismiss()
                        }
                    )

                }
                Spacer(
                    modifier = Modifier
                        .height(14.dp)
                        .fillMaxWidth()
                )
                //## input fields
                LazyColumn(
                    modifier = Modifier
                        .imePadding()
                        .weight(1f)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    item {
                        //## Title
                        InputField(
                            isSingleLine = true,
                            showLabel = false,
                            hintText = stringResource(R.string.title),
                            text = title,
                            onTextChange = {
                                onTitleChange(it)
                            },
                            modifier = Modifier,
                            errorMessage = titleError,
                        )
                    }
                    item {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                        )
                    }
                    item {
                        //## Note
                        InputField(
                            isSingleLine = false,
                            showLabel = false,
                            minHeight = 400,
                            hintText = stringResource(R.string.write_note),
                            text = content,
                            onTextChange = {
                                if (140 - it.length >= 0) {
                                    onContentChange(it)
                                }
                            },
                            modifier = Modifier,
                            errorMessage = contentError,
                        )
                    }
                }
            }
        }

    }
}
