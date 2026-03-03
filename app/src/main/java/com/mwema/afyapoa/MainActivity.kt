package com.mwema.afyapoa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.mwema.afyapoa.ui.MainScreen
import com.mwema.afyapoa.ui.home.HomeScreen
import com.mwema.afyapoa.ui.login.LoginScreen
import com.mwema.afyapoa.ui.signup.SignUPScreen
import com.mwema.afyapoa.ui.theme.AfyaPoaTheme
import com.mwema.afyapoa.utils.Routes
import okhttp3.Route

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AfyaPoaTheme {
                val navController = rememberNavController()
                val applicationContext = LocalContext.current.applicationContext as AfyaPoa
                NavHost(
                    navController = navController,
                    startDestination = applicationContext.tokenManager.getToken()?.let {
                        Routes.MAINSCREEN
                    } ?: Routes.LOGIN
                ) {
                    composable(route = Routes.LOGIN) {
                        LoginScreen(modifier = Modifier) {
                            navController.navigate(it)
                        }
                    }
                    composable(route = Routes.MAINSCREEN) {
                        MainScreen(){
                            navController.navigate(it)
                        }
                    }
                    composable(Routes.SIGNUP) {
                        SignUPScreen(Modifier) {
                            navController.navigate(it)
                        }
                    }
                }
            }
        }
    }
}
