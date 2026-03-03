package com.mwema.afyapoa.ui.settings

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MedicalInformation
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mwema.afyapoa.AfyaPoa
import com.mwema.afyapoa.data.ApiImplementation
import com.mwema.afyapoa.utils.Routes
import com.mwema.afyapoa.utils.UIEvent
import com.mwema.afyapoa.utils.factory.SettingsViewModelFactory

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onThemeToggle: (Boolean) -> Unit,
    onMedicalInfo: () -> Unit,
    onSecurity: () -> Unit,
    onLogout: () -> Unit,
    onNavigate: (String) -> Unit
) {
    val context = LocalContext.current.applicationContext as AfyaPoa
    val viewModel = viewModel<SettingsViewModel>(
        factory = SettingsViewModelFactory(ApiImplementation(context.api, context.tokenManager))
    )
    val userName = viewModel.userName
    val registrationNumber = viewModel.registrationNumber
    val imageUrl = viewModel.imageUrl
    val uiState = viewModel.uiState
    val snackBarHostState = remember { SnackbarHostState() }

    uiState.error?.let {
        LaunchedEffect(true) {
            snackBarHostState.showSnackbar("Something went Wrong")
            viewModel.clearError()
        }
    }

    LaunchedEffect(uiState.isSuccess) {
        snackBarHostState.showSnackbar("Success")
        viewModel.clearSuccess()
    }

    LaunchedEffect(true) {
        viewModel.uiChannel.collect { event ->
            when (event) {
                is UIEvent.Navigate -> {
                    onNavigate(event.route)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileCard(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 25.dp),
            name = userName,
            registrationNumber = registrationNumber,
            imageUrl = imageUrl,
            onClick = {
                viewModel.executeEvent(SettingsScreenEvents.OnProfileCardClicked)
            }

        )
        Spacer(modifier = Modifier.height(24.dp))


        SettingsSection(title = "Account") {
            SettingsItem(
                icon = Icons.Default.MedicalInformation,
                title = "Medical Information",
                subtitle = "Blood Group, Allergies",
                onClick = onMedicalInfo
            )
            SettingsItem(
                icon = Icons.Default.Security,
                title = "Security",
                onClick = onSecurity
            )
        }

        SettingsSection(title = "Preferences") {

            ThemeToggleItem(
//                Handle: Add darkmode and lightmode functionality
                isDarkMode = true,
                onToggle = onThemeToggle
            )

            SettingsItem(
                icon = Icons.Default.Notifications,
                title = "Notifications",
                onClick = { /* navigate */ }
            )
        }

        SettingsSection(title = "Support") {

            SettingsItem(
                icon = Icons.Default.Info,
                title = "About AfyaPoa",
                onClick = { onNavigate(Routes.ABOUT) }
            )

            SettingsItem(
                icon = Icons.Default.PrivacyTip,
                title = "Privacy Policy",
                onClick = { }
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        LogoutButton(onLogout)

        Spacer(modifier = Modifier.height(40.dp))

        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }
        }
    }
}