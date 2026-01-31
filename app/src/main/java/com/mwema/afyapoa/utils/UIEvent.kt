package com.mwema.afyapoa.utils

import okhttp3.Route

sealed class UIEvent {
    data class Navigate(val route: String): UIEvent()
}