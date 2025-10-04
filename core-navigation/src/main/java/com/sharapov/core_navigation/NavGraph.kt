package com.sharapov.core_navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun NavGraph(
    navigationState: NavigationState,
    mainScreenContent: @Composable (PaddingValues) -> Unit,
    settingsScreenContent: @Composable (PaddingValues) -> Unit,
    detailsScreenContent: @Composable (PaddingValues, Int) -> Unit,
    profileScreenContent: @Composable (PaddingValues) -> Unit,
    searchScreenContent: @Composable (PaddingValues) -> Unit,
    favoritesScreenContent: @Composable (PaddingValues) -> Unit,
) {

    Scaffold(
        bottomBar = { BaseNavigationBar(navigationState) }
    ) { paddingValues ->
        NavHost(
            navController = navigationState.navController,
            startDestination = Screen.Main.route
        ) {
            mainScreenNavGraph(
                mainScreenContent = mainScreenContent,
                detailsScreenContent = detailsScreenContent,
                settingsScreenContent = settingsScreenContent,
                innerPadding = paddingValues
            )
            composable(Screen.Search.route) {
                profileScreenContent(paddingValues)
            }
            composable(Screen.Favorites.route) {
                favoritesScreenContent(paddingValues)
            }
            composable(Screen.Profile.route) {
                searchScreenContent(paddingValues)
            }
        }
    }
}
