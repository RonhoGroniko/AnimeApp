package com.sharapov.core_navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sharapov.core_domain.usecases.AnimeFilter


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavGraph(
    navigationState: NavigationState,
    mainScreenContent: @Composable () -> Unit,
    settingsScreenContent: @Composable () -> Unit,
    detailsScreenContent: @Composable (Int) -> Unit,
    searchScreenWithGenreContent: @Composable (AnimeFilter) -> Unit,
    profileScreenContent: @Composable () -> Unit,
    searchScreenContent: @Composable () -> Unit,
    favoritesScreenContent: @Composable () -> Unit,
) {
    Scaffold(bottomBar = { BaseNavigationBar(navigationState) }) {
        NavHost(
            navController = navigationState.navController,
            startDestination = Screen.Main.route
        ) {
            mainScreenNavGraph(
                mainScreenContent = mainScreenContent,
                settingsScreenContent = settingsScreenContent,
                detailsScreenContent = detailsScreenContent,
                searchScreenWithGenreContent = searchScreenWithGenreContent
            )

            composable(Screen.Search.route) {
                searchScreenContent()
            }
            composable(Screen.Favorites.route) { favoritesScreenContent() }
            composable(Screen.Profile.route) { profileScreenContent() }
        }
    }
}
