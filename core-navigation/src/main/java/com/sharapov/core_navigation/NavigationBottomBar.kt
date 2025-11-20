package com.sharapov.core_navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sharapov.core_ui.theme.CustomFonts

@Composable
fun BaseNavigationBar(
    navigationState: NavigationState
) {
    val navItems = listOf(
        NavigationItem(
            screen = Screen.Main,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            title = stringResource(R.string.home),
            startRoute = Screen.AiringUpcoming.route
        ),
        NavigationItem(
            screen = Screen.Search,
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search,
            title = stringResource(R.string.search),
        ),
        NavigationItem(
            screen = Screen.Favorites,
            selectedIcon = Icons.Filled.Bookmark,
            unselectedIcon = Icons.Outlined.BookmarkBorder,
            title = stringResource(R.string.favorites)
        ),
        NavigationItem(
            screen = Screen.Profile,
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            title = stringResource(R.string.profile)
        )
    )

    val navBackStackEntry by navigationState.navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        navItems.forEachIndexed { index, item ->
            val selected = currentDestination.isOnDestination(item.screen.route)
            val atStartOfTab = currentDestination?.route == (item.startRoute ?: item.screen.route)
            NavigationBarItem(
                icon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title,
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontFamily = CustomFonts.Poppins,
                        fontSize = 12.sp
                    )
                },
                selected = selected,
                onClick = {
                    when {
                        selected && atStartOfTab -> {
                            return@NavigationBarItem
                        }
                        selected && !atStartOfTab -> {
                            navigationState.navController.popBackStack(item.startRoute ?: item.screen.route, false)
                        }
                        else -> {
                            navigationState.navigateTo(item.screen.route)
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.secondary,
                    unselectedIconColor = MaterialTheme.colorScheme.inversePrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.inversePrimary,
                    indicatorColor = MaterialTheme.colorScheme.background,
                )
            )
        }
    }
}

data class NavigationItem(
    val screen: Screen,
    val startRoute: String? = null,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val title: String
)

private fun NavDestination?.isOnDestination(route: String): Boolean =
    this?.hierarchy?.any { it.route == route } == true