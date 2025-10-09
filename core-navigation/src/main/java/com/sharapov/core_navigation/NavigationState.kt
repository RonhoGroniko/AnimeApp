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

    fun navigateToDetails(id: Int) {
        navController.navigate(Screen.Details.createRoute(id)) {
            launchSingleTop = false
            restoreState = false
        }
    }

    fun navigateToSearch(genre: String) {
        navController.navigate(Screen.SearchWithGenre.createRoute(genre)) {
            launchSingleTop = false
            restoreState = false
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