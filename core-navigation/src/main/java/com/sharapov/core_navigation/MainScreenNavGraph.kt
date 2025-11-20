package com.sharapov.core_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.sharapov.core_navigation.mapper.toEntity
import com.sharapov.domain_anime.entity.common.AnimeFilter


fun NavGraphBuilder.mainScreenNavGraph(
    mainScreenContent: @Composable () -> Unit,
    settingsScreenContent: @Composable () -> Unit,
    detailsScreenContent: @Composable (Int) -> Unit,
    searchScreenWithGenreContent: @Composable (AnimeFilter) -> Unit,
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

        composable(
            route = Screen.SearchWithFilter.route,
            arguments = listOf(
                navArgument(Screen.SEARCH_FILTER_KEY) {
                    type = NavItemAnimeFilter.NavigationType
                }
            )
        ) {
            val filter = Screen.SearchWithFilter.getFilter(it.arguments)
            searchScreenWithGenreContent(filter.toEntity())
        }

        composable(Screen.Settings.route) { settingsScreenContent() }
    }
}