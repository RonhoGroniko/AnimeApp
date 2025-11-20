package com.sharapov.core_navigation

import android.os.Bundle
import com.sharapov.core_navigation.NavItemAnimeFilter.Companion.toQueryValue
import com.sharapov.core_navigation.utils.parcelable

sealed class Screen(val route: String) {

    data object Main : Screen(MAIN_ROUTE)
    data object AiringUpcoming : Screen(AIRING_UPCOMING_ROUTE)
    data object Details : Screen("$DETAILS_ROUTE/{$DETAILS_ID_KEY}") {

        fun createRoute(id: Int): String {
            return "$DETAILS_ROUTE/$id"
        }

        fun getId(arguments: Bundle?): Int {
            return arguments?.getString(DETAILS_ID_KEY)?.toInt() ?: 0
        }
    }

    data object Search : Screen(SEARCH_ROUTE)
    data object SearchWithFilter : Screen("$SEARCH_ROUTE/{$SEARCH_FILTER_KEY}") {

        fun createRoute(filter: NavItemAnimeFilter): String {
            val filterJson = toQueryValue(filter)
            return "$SEARCH_ROUTE/${filterJson}"
        }

        fun getFilter(arguments: Bundle?): NavItemAnimeFilter {
            return arguments?.parcelable<NavItemAnimeFilter>(SEARCH_FILTER_KEY)
                ?: throw RuntimeException("Args is null")
        }
    }

    data object Favorites : Screen(FAVORITES_ROUTE)
    data object Profile : Screen(PROFILE_ROUTE)

    data object Settings : Screen(SETTINGS_ROUTE)

    companion object {

        private const val DETAILS_ID_KEY = "id"
        private const val DETAILS_ROUTE = "details"

        private const val MAIN_ROUTE = "main"

        const val SEARCH_FILTER_KEY = "filter"
        private const val SETTINGS_ROUTE = "settings"

        private const val SEARCH_ROUTE = "search"

        private const val FAVORITES_ROUTE = "favorites"
        private const val PROFILE_ROUTE = "profile"
        private const val AIRING_UPCOMING_ROUTE = "airing_upcoming"

    }
}
