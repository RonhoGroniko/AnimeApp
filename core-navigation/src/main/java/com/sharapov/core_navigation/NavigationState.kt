package com.sharapov.core_navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController

class NavigationState(
    val navController: NavHostController
) {


    fun navigateTo(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            restoreState = true
        }
    }
}

@Composable
fun rememberNavigationState(
    navController: NavHostController
): NavigationState {
    return remember {
        NavigationState(navController)
    }
}