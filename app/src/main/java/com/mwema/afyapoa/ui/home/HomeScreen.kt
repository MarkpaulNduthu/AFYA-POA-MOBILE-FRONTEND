package com.mwema.afyapoa.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.mwema.afyapoa.AfyaPoa
import com.mwema.afyapoa.utils.Routes

@Composable
fun HomeScreen(
    modifier: Modifier,
    onNavigate: (String) -> Unit
){
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        val tokenManager = (LocalContext.current.applicationContext as AfyaPoa).tokenManager
        Text(text="Welcome to Home page")
    }
}