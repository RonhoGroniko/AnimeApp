package com.sharapov.core_navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.sharapov.core_domain.usecases.AnimeFilter
import com.sharapov.core_navigation.mapper.toNavItem

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

    fun navigateToSearch(filter: AnimeFilter) {
        navController.navigate(Screen.SearchWithFilter.createRoute(filter.toNavItem())) {
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