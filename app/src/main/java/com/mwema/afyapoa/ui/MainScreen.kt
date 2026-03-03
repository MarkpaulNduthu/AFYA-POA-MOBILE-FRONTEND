package com.mwema.afyapoa.ui

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mwema.afyapoa.AfyaPoa
import com.mwema.afyapoa.data.ApiImplementation
import com.mwema.afyapoa.ui.home.HomeScreen
import com.mwema.afyapoa.ui.records.RecordsScreen
import com.mwema.afyapoa.ui.settings.SettingsScreen
import com.mwema.afyapoa.ui.settings.editProfile.EditProfileScreen
import com.mwema.afyapoa.ui.settings.editProfile.EditProfileScreenViewModel
import com.mwema.afyapoa.utils.Routes
import com.mwema.afyapoa.utils.factory.EditProfileViewModelFactory

@Composable
fun MainScreen(
    onNavigate: (String) -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val application = LocalContext.current.applicationContext as AfyaPoa
    val editProfileViewModel = viewModel<EditProfileScreenViewModel>(
        factory = EditProfileViewModelFactory(
            ApiImplementation(application.api, application.tokenManager)
        )
    )

    val bottomNavItems = listOf(
        BottomNavItem(
            name = "Home",
            route = Routes.HOME,
            unSelectedIcon = Icons.Outlined.Home,
            selectedIcon = Icons.Filled.Home,
        ),
        BottomNavItem(
            name = "Records",
            route = Routes.RECORDS,
            unSelectedIcon = Icons.Outlined.Description,
            selectedIcon = Icons.Filled.Description
        ),
        BottomNavItem(
            name = "Settings",
            route = Routes.SETTINGS,
            unSelectedIcon = Icons.Outlined.Settings,
            selectedIcon = Icons.Filled.Settings,
        )
    )
    Scaffold(
        bottomBar = {
            NavigationBar(
                tonalElevation = 10.dp,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                bottomNavItems.forEach { navItem ->
                    val selected = currentRoute == navItem.route
                    NavigationBarItem(
                        selected = selected,
                        colors = NavigationBarItemDefaults.colors(

                            // ICON COLORS
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurface,

                            // TEXT COLORS
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurface,

                            // INDICATOR (selected pill background)
                            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                        ),
                        icon = {
                            Icon(
                                imageVector = if (selected) navItem.selectedIcon else navItem.unSelectedIcon,
                                contentDescription = navItem.name,
                                modifier = Modifier,
                            )
                        },
                        label = {
                            Text(text = navItem.name, style = MaterialTheme.typography.labelMedium)
                        },
                        onClick = {
                            navController.navigate(navItem.route) {
                                popUpTo(
                                    navController.graph.startDestinationRoute ?: return@navigate
                                ) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME
        ) {
            composable(Routes.HOME) {
                HomeScreen(modifier = Modifier.padding(innerPadding)) {
                    onNavigate(it)
                }
            }
            composable(Routes.RECORDS) {
                RecordsScreen(modifier = Modifier.padding(innerPadding))
            }
            composable(Routes.SETTINGS) {
                SettingsScreen(
                    modifier = Modifier
                        .padding(innerPadding),
                    onThemeToggle = {},
                    onMedicalInfo = {},
                    onSecurity = {},
                    onLogout = {
                        application.tokenManager.clear()
                        onNavigate(Routes.LOGIN)
                    },
                ) { route ->
                    navController.navigate(route)
                }
            }
            composable(Routes.EDITPROFILE) {
                EditProfileScreen(
                    modifier = Modifier,
                    editProfileViewModel
                )
            }
        }
    }
}

data class BottomNavItem(
    val name: String,
    val route: String,
    val unSelectedIcon: ImageVector,
    val selectedIcon: ImageVector,
)