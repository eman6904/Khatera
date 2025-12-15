package com.example.firebaseapp.myPackages.ui.compose.screens

import android.text.Layout
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firebaseapp.R
import com.example.firebaseapp.myPackages.data.local.UserData.Companion.getUser
import com.example.firebaseapp.myPackages.data.models.NoteContent
import com.example.firebaseapp.myPackages.data.remote.firebase.database.repoImp.DataRepoImp
import com.example.firebaseapp.myPackages.ui.compose.components.LoadingView
import com.example.firebaseapp.myPackages.ui.compose.components.NotificationItem
import com.example.firebaseapp.myPackages.utils.showError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

@Composable
fun Notifications(
    onItemDetailsNav: (NoteContent?) -> Unit
) {
    val dataRepo = remember { DataRepoImp() }
    var items by remember { mutableStateOf(emptyList<NoteContent>()) }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val scope = remember { CoroutineScope(Dispatchers.IO + Job()) }

    LaunchedEffect(Unit) {
        isLoading = true
        dataRepo.getAllSharedNotes(
            scope = scope,
            onSuccess = {
                items = it
                isLoading = false
            },
            onFailure = {
                isLoading = false
                showError(context)
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            //## Title
            Text(
                text = stringResource(R.string.shared_notes),
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            //## Notes
            LazyColumn {
                if (isLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = Color(0xFF558B2F),
                                strokeWidth = 4.dp,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }

                items(
                    items = items,
                    key = { it.id ?: it.hashCode().toString() }
                ) { note ->
                    NotificationItem(
                        note = note,
                        onItemClick = { onItemDetailsNav(note) }
                    )
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            dataRepo.cancelRequest()
            scope.cancel()
        }
    }
}
