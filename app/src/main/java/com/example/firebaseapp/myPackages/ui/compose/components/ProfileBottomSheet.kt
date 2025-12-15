package com.example.firebaseapp.myPackages.ui.compose.components

import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontStyle.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firebaseapp.R
import com.example.firebaseapp.myPackages.data.local.UserData.Companion.getUser
import com.example.firebaseapp.myPackages.data.models.NoteContent
import com.example.firebaseapp.myPackages.data.models.User
import com.example.firebaseapp.myPackages.data.remote.firebase.database.repoImp.DataRepoImp
import com.example.firebaseapp.myPackages.data.remote.firebase.storage.repoImp.StorageRepoImp
import com.example.firebaseapp.myPackages.ui.compose.screens.SharedItemsContent
import com.example.firebaseapp.myPackages.utils.showError


@Composable
fun ProfileBottomSheet(
    user: User,
    onImageChang:(String?)->Unit = {},
    onDismiss: () -> Unit,
    onItemClick:(NoteContent?) -> Unit = {},
    ){

    var sharedItemsCount by remember { mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    var profileImage by remember { mutableStateOf(user.profileImage) }
    var storageRepo by remember { mutableStateOf(StorageRepoImp()) }
    var dataRepo by remember { mutableStateOf(DataRepoImp()) }
    val context = LocalContext.current

    val imagePicker = rememberImagePickerWithCrop { uri ->
        storageRepo.uploadProfileImage(
            imageUri = uri,
            onSuccess = { downloadUrl ->
                onImageChang(downloadUrl)
                profileImage = downloadUrl
                dataRepo.updateRemoteUser(
                    getUser().copy(
                        profileImage = downloadUrl
                    ),
                    onSuccess = {},
                    onFailure = {
                        showError(context)
                    }
                )
            },
            onFailure = {
                showError(context)
            }
        )
    }

    CustomBottomSheet(onDismiss = onDismiss) {
       Box{
           Column(
               modifier = Modifier
                   .fillMaxSize()
                   .background(Black),
               horizontalAlignment = Alignment.CenterHorizontally
           ) {
               Column(
                   modifier = Modifier
                       .padding(horizontal = 20.dp)
                       .fillMaxWidth(),
                   verticalArrangement = Arrangement.Center,
                   horizontalAlignment = Alignment.CenterHorizontally
               ) {

                   Box(
                       contentAlignment = Alignment.BottomEnd,

                       ) {
                       ProfileImage(
                           imageUrl = profileImage,
                           size = 140
                       )
                       if(user.id == getUser().id){
                           Box(
                               modifier = Modifier
                                   .clickable {
                                       showDialog = true
                                   }
                                   .size(30.dp)
                                   .clip(CircleShape)
                                   .background(Color.White),
                               contentAlignment = Alignment.Center
                           ) {
                               Icon(
                                   painter = painterResource(R.drawable.edit_icon),
                                   contentDescription = null,
                                   tint = Color(0xFF558B2F),
                                   modifier = Modifier.size(20.dp)
                               )
                           }
                       }
                   }
                   Spacer(modifier = Modifier
                       .fillMaxWidth()
                       .height(10.dp))
                   Text(
                       text = user.userName ?: "",
                       fontSize = 24.sp,
                       fontWeight = Bold,
                       color = Color.White,
                       modifier = Modifier.padding(bottom = 2.dp)
                   )
                   Spacer(modifier = Modifier
                       .fillMaxWidth()
                       .height(10.dp))
                   Text(
                       text = user.joinDate ?: "",
                       fontSize = 12.sp,
                       color = Color.White,
                       modifier = Modifier.padding(bottom = 2.dp)
                   )
               }
               Spacer(modifier = Modifier
                   .fillMaxWidth()
                   .height(20.dp))

               Column(
                   modifier = Modifier
                       .fillMaxWidth()
               ) {
                   if(user.id == getUser().id) {
                       Item(
                           title = "Email:",
                           body = user.email ?: ""
                       )
                   }
                   Box(
                       modifier = Modifier
                           .weight(1f)
                           .fillMaxSize(),
                   ){
                       SharedItemsContent(
                           onItemDetailsNav = {onItemClick(it)},
                           sharedItems = {
                               sharedItemsCount = it
                           },
                           title = {
                               Text(
                                   text = "${stringResource(R.string.shared_items)} $sharedItemsCount",
                                   style = MaterialTheme.typography.headlineMedium,
                                   color = Color(0xFF558B2F),
                                   fontSize = 16.sp,
                                   fontWeight = Bold,
                                   textAlign = TextAlign.Start,
                                   modifier = Modifier.fillMaxWidth()
                               )
                           }
                       )
                   }
               }
           }
           if(showDialog){
               ProfileDialog(
                   onUpdateClick = {
                       imagePicker.openPicker()
                       showDialog = false
                   },
                   onRemoveClick = {
                       profileImage = null
                       onImageChang(null)
                       dataRepo.updateRemoteUser(
                           getUser().copy(
                               profileImage = null
                           ),
                           onSuccess = {},
                           onFailure = {
                               showError(context)
                           }
                       )
                       showDialog = false
                   }
               )
           }
       }
    }

}
@Composable
fun Item(
    title:String,
    body:String,
){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)){
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = Bold,
            color = Color(0xFF558B2F)
        )
        Spacer(modifier = Modifier
            .width(4.dp))

        Text(
            text = body,
            fontSize = 16.sp,
            fontStyle = Normal,
            color = Color.White
        )
    }
}