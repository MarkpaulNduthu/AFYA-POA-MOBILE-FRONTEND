package com.mwema.afyapoa.ui.settings.editProfile

data class EditProfileUIState(
    var isUpdating: Boolean = false,
    var error: String? = null,
    var success: Boolean = false
)
