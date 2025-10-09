package com.sharapov.core_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation


fun NavGraphBuilder.mainScreenNavGraph(
    mainScreenContent: @Composable () -> Unit,
    settingsScreenContent: @Composable () -> Unit,
    detailsScreenContent: @Composable (Int) -> Unit,
    searchScreenWithGenreContent: @Composable (String) -> Unit,
) {
    navigation(
        startDestination = Screen.AiringUpcoming.route,
        route = Screen.Main.route
    ) {
        composable(Screen.AiringUpcoming.route) { mainScreenContent() }

        composable(Screen.Details.route) {
            val id = Screen.Details.getId(it.arguments)
            detailsScreenContent(id)
        }

        composable(Screen.SearchWithGenre.route) {
            val genre = Screen.SearchWithGenre.getGenre(it.arguments)
            searchScreenWithGenreContent(genre)
        }

        composable(Screen.Settings.route) { settingsScreenContent() }
    }
}