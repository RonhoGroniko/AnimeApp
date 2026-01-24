package com.sharapov.core_navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.sharapov.core_ui.theme.icons.Bookmark
import com.sharapov.core_ui.theme.icons.CustomIcons
import com.sharapov.core_ui.theme.icons.Home
import com.sharapov.core_ui.theme.icons.Person
import com.sharapov.core_ui.theme.icons.Search
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen : NavKey {

    @Serializable
    data object Main : Screen, BottomNavItem {
        override val selectedIcon: ImageVector = CustomIcons.Filled.Home
        override val unselectedIcon: ImageVector = CustomIcons.Outlined.Home
        override val title: String = "Home"
    }

    @Serializable
    data object Search : Screen, BottomNavItem {
        override val selectedIcon: ImageVector = CustomIcons.Filled.Search
        override val unselectedIcon: ImageVector = CustomIcons.Filled.Search
        override val title: String = "Search"
    }

    @Serializable
    data class SearchWithGenre(val genre: String) : Screen // genre as string

    @Serializable
    data object Favorites : Screen, BottomNavItem {
        override val selectedIcon: ImageVector = CustomIcons.Filled.Bookmark
        override val unselectedIcon: ImageVector = CustomIcons.Outlined.Bookmark
        override val title: String = "Favorites"
    }

    @Serializable
    data object Profile : Screen, BottomNavItem {
        override val selectedIcon: ImageVector = CustomIcons.Filled.Person
        override val unselectedIcon: ImageVector = CustomIcons.Outlined.Person
        override val title: String = "Profile"
    }

    @Serializable
    data class Details(val id: Long) : Screen

    @Serializable
    data object Settings : Screen
}

private const val MAIN_ROUTE = "main"
private const val SEARCH_ROUTE = "search"
private const val SEARCH_WITH_GENRE_PREFIX = "search_genre:"
private const val SETTINGS_ROUTE = "settings"
private const val PROFILE_ROUTE = "profile"
private const val DETAILS_PREFIX = "details:"
private const val FAVORITES_ROUTE = "favorites"

fun Screen.toRoute(): String = when (this) {
    is Screen.Details -> "$DETAILS_PREFIX$id"
    Screen.Profile -> PROFILE_ROUTE
    Screen.Favorites -> FAVORITES_ROUTE
    Screen.Main -> MAIN_ROUTE
    Screen.Search -> SEARCH_ROUTE
    Screen.Settings -> SETTINGS_ROUTE
    is Screen.SearchWithGenre -> "$SEARCH_WITH_GENRE_PREFIX$genre"
}

fun String.toScreen(): Screen = when {
    this == MAIN_ROUTE -> Screen.Main
    this == PROFILE_ROUTE -> Screen.Profile
    startsWith(DETAILS_PREFIX) ->
        Screen.Details(substringAfter(DETAILS_PREFIX).toLong())

    this == FAVORITES_ROUTE -> Screen.Favorites
    this == SEARCH_ROUTE -> Screen.Search
    this == SETTINGS_ROUTE -> Screen.Settings
    startsWith(SEARCH_WITH_GENRE_PREFIX) -> Screen.SearchWithGenre(
        substringAfter(
            SEARCH_WITH_GENRE_PREFIX
        )
    )

    else -> Screen.Main
}

