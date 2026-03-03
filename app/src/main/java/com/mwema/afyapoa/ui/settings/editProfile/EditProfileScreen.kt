package com.mwema.afyapoa.ui.settings.editProfile

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun EditProfileScreen(
    modifier: Modifier,
    viewModel: EditProfileScreenViewModel
) {
    val context = LocalContext.current
    var username = viewModel.currentUserName
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val uiState = viewModel.uiState
    val snackBarHostState = remember { SnackbarHostState() }


    // Image picker
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri = uri
    }

    LaunchedEffect(uiState.success) {
        snackBarHostState.showSnackbar("Profile updated Successfully")
        viewModel.clearSuccess()
    }
    uiState.error?.let {
        LaunchedEffect(true) {
            snackBarHostState.showSnackbar("Failed to update Profile")
            viewModel.clearError()
        }
    }



    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(24.dp))

        // Profile Image
        if (selectedImageUri != null) {
            AsyncImage(
                model = selectedImageUri,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile Image"
            )
        }
        Spacer(Modifier.height(12.dp))

        TextButton(
            onClick = { launcher.launch("image/*") }
        ) {
            Text("Change Profile Photo")
        }

        Spacer(Modifier.height(32.dp))

        // Username
        OutlinedTextField(
            value = username,
            onValueChange = { viewModel.currentUserName = it },
            label = {
                Text(
                    "Preferred Username", style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )

        Spacer(Modifier.height(40.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                selectedImageUri?.let { viewModel.updateProfile(context, username, it) }
            }
        ) {
            Text("Save Changes")
        }

        when {
            uiState.isUpdating -> {
                CircularProgressIndicator()
            }
        }

    }
}