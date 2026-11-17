package com.mwema.afyapoa.ui.settings.profileInfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mwema.afyapoa.AfyaPoa
import com.mwema.afyapoa.data.ApiImplementation
import com.mwema.afyapoa.utils.Routes
import com.mwema.afyapoa.utils.factory.MedicalInfoViewModelFactory

@Composable
fun MedicalInfoScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    val application = LocalContext.current.applicationContext as AfyaPoa
    val snackBarHostState = remember { SnackbarHostState() }
    val viewModel = viewModel<MedicalInfoViewModel>(
        factory = MedicalInfoViewModelFactory(
            ApiImplementation(
                application.api,
                application.tokenManager
            )
        )
    )
    val state by viewModel.state.collectAsState()

    state.error?.let {
        LaunchedEffect(true) {
            snackBarHostState.showSnackbar("Failed to save Medical Information\nPlease try Again!!")
            viewModel.clearError()
        }
    }

    LaunchedEffect(state.isSaved) {
        snackBarHostState.showSnackbar("Saved Medical Information Successfully")
        viewModel.clearSuccess()
        onNavigate(Routes.SETTINGS)
    }


    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item { Text("Medical Information", style = MaterialTheme.typography.headlineLarge) }
        item {
            BloodTypeSection(
                bloodType = state.bloodType,
                onBloodTypeChange = {
                    viewModel.onEvent(MedicalInfoEvent.OnBloodTypeChanged(it))
                }
            )
        }

        item {
            EmergencyContactsSection(
                contacts = state.contacts,
                onAddContact = { viewModel.onEvent(MedicalInfoEvent.AddContact) },
                onRemoveContact = { viewModel.onEvent(MedicalInfoEvent.RemoveContact(it)) },
                onUpdateContact = { index, contact ->
                    viewModel.onEvent(MedicalInfoEvent.UpdateContact(index, contact))
                }
            )
        }

        item {
            DynamicListSection(
                title = "Chronic Conditions",
                items = state.chronicConditions,
                onAdd = { viewModel.onEvent(MedicalInfoEvent.AddChronicCondition) },
                onRemove = { viewModel.onEvent(MedicalInfoEvent.RemoveChronicCondition(it)) },
                onUpdate = { index, value ->
                    viewModel.onEvent(MedicalInfoEvent.UpdateChronicCondition(index, value))
                }
            )
        }

        item {
            DynamicListSection(
                title = "Allergies",
                items = state.allergies,
                onAdd = { viewModel.onEvent(MedicalInfoEvent.AddAllergy) },
                onRemove = { viewModel.onEvent(MedicalInfoEvent.RemoveAllergy(it)) },
                onUpdate = { index, value ->
                    viewModel.onEvent(MedicalInfoEvent.UpdateAllergy(index, value))
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { viewModel.onEvent(MedicalInfoEvent.Save) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                enabled = !state.isLoading
            ) {
                Text("Save Information")
            }
        }
        when {
            state.isLoading -> {
                item { CircularProgressIndicator() }
            }
        }
    }
}