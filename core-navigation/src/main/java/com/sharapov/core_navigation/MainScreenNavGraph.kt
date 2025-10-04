package com.sharapov.core_navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation


fun NavGraphBuilder.mainScreenNavGraph(
    innerPadding: PaddingValues,
    mainScreenContent: @Composable (PaddingValues) -> Unit,
    settingsScreenContent: @Composable (PaddingValues) -> Unit,
    detailsScreenContent: @Composable (PaddingValues, Int) -> Unit
) {
    navigation(
        startDestination = Screen.AiringUpcoming.route,
        route = Screen.Main.route
    ) {
        composable(Screen.AiringUpcoming.route) {
            mainScreenContent(innerPadding)
        }
        composable(Screen.Details.route) {
            val id = Screen.Details.getId(it.arguments)
            detailsScreenContent(innerPadding, id)
        }
        composable(Screen.Settings.route) {
            settingsScreenContent(innerPadding)
        }
    }
}