package com.mwema.afyapoa.ui.settings.profileInfo

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import androidx.lifecycle.ViewModel
import com.mwema.afyapoa.data.ApiImplementation

class MedicalInfoViewModel(
    val apiImplementation: ApiImplementation
) : ViewModel() {


    private val _state = MutableStateFlow(MedicalInfoState())
    val state: StateFlow<MedicalInfoState> = _state

    fun onEvent(event: MedicalInfoEvent) {
        when (event) {

            is MedicalInfoEvent.OnBloodTypeChanged -> {
                _state.value = _state.value.copy(
                    bloodType = event.bloodType
                )
            }

            // -------------------------
            // Emergency Contacts
            // -------------------------

            MedicalInfoEvent.AddContact -> {
                _state.value = _state.value.copy(
                    contacts = _state.value.contacts + EmergencyContact()
                )
            }

            is MedicalInfoEvent.RemoveContact -> {
                val updated = _state.value.contacts.toMutableList()
                updated.removeAt(event.index)
                _state.value = _state.value.copy(contacts = updated)
            }

            is MedicalInfoEvent.UpdateContact -> {
                val updated = _state.value.contacts.toMutableList()
                updated[event.index] = event.contact
                _state.value = _state.value.copy(contacts = updated)
            }

            // -------------------------
            // Chronic Conditions
            // -------------------------

            MedicalInfoEvent.AddChronicCondition -> {
                _state.value = _state.value.copy(
                    chronicConditions = _state.value.chronicConditions + ""
                )
            }

            is MedicalInfoEvent.RemoveChronicCondition -> {
                val updated = _state.value.chronicConditions.toMutableList()
                updated.removeAt(event.index)
                _state.value = _state.value.copy(chronicConditions = updated)
            }

            is MedicalInfoEvent.UpdateChronicCondition -> {
                val updated = _state.value.chronicConditions.toMutableList()
                updated[event.index] = event.value
                _state.value = _state.value.copy(chronicConditions = updated)
            }

            // -------------------------
            // Allergies
            // -------------------------

            MedicalInfoEvent.AddAllergy -> {
                _state.value = _state.value.copy(
                    allergies = _state.value.allergies + ""
                )
            }

            is MedicalInfoEvent.RemoveAllergy -> {
                val updated = _state.value.allergies.toMutableList()
                updated.removeAt(event.index)
                _state.value = _state.value.copy(allergies = updated)
            }

            is MedicalInfoEvent.UpdateAllergy -> {
                val updated = _state.value.allergies.toMutableList()
                updated[event.index] = event.value
                _state.value = _state.value.copy(allergies = updated)
            }

            // -------------------------
            // Save
            // -------------------------

            MedicalInfoEvent.Save -> {
                saveMedicalInfo()
            }
        }
    }

    private fun saveMedicalInfo() {
        viewModelScope.launch {

            // Simple validation example
            if (_state.value.bloodType.isBlank()) {
                _state.value = _state.value.copy(
                    error = "Please select blood type"
                )
                return@launch
            }

            _state.value = _state.value.copy(isLoading = true)

            try {
                val result = apiImplementation.saveMedicalInfo(state)
                result.fold(
                    onSuccess = {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            isSaved = true,
                            error = null
                        )
                    },
                    onFailure = { throwable ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            isSaved = false,
                            error = throwable.message
                        )
                    }
                )


            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Failed to save information"
                )
            }
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }

    fun clearSuccess() {
        _state.value = _state.value.copy(isSaved = false)
    }

}