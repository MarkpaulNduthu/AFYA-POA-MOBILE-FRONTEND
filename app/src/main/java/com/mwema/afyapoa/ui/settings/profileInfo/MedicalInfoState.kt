package com.mwema.afyapoa.ui.settings.profileInfo

data class MedicalInfoState(
    val bloodType: String = "",
    val contacts: List<EmergencyContact> = emptyList(),
    val chronicConditions: List<String> = emptyList(),
    val allergies: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSaved: Boolean = false
)
