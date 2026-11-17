package com.mwema.afyapoa.data.model

import com.mwema.afyapoa.ui.settings.profileInfo.EmergencyContact

data class MedicalInfoRequest(
    val bloodType: String,
    val allergies: List<String>,
    val chronicConditions: List<String>,
    val emergencyContacts: List<EmergencyContact>
)
