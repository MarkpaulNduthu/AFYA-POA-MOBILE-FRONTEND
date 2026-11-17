package com.mwema.afyapoa.ui.settings.profileInfo

sealed class MedicalInfoEvent {

    data class OnBloodTypeChanged(val bloodType: String) : MedicalInfoEvent()

    object AddContact : MedicalInfoEvent()
    data class RemoveContact(val index: Int) : MedicalInfoEvent()
    data class UpdateContact(val index: Int, val contact: EmergencyContact) : MedicalInfoEvent()

    object AddChronicCondition : MedicalInfoEvent()
    data class RemoveChronicCondition(val index: Int) : MedicalInfoEvent()
    data class UpdateChronicCondition(val index: Int, val value: String) : MedicalInfoEvent()

    object AddAllergy : MedicalInfoEvent()
    data class RemoveAllergy(val index: Int) : MedicalInfoEvent()
    data class UpdateAllergy(val index: Int, val value: String) : MedicalInfoEvent()

    object Save : MedicalInfoEvent()
}