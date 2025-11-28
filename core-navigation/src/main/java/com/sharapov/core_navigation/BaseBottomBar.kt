package com.sharapov.core_navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.ShortNavigationBar
import androidx.compose.material3.ShortNavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BaseBottomBar(
    topLevelBackStack: TopLevelBackStack<Screen>
) {
    val navItems = listOf(
        Screen.Main, Screen.Search, Screen.Favorites, Screen.Profile
    )

    ShortNavigationBar {
        navItems.forEachIndexed { index, item ->
            val selected = topLevelBackStack.topLevelKey == item
            ShortNavigationBarItem(
                selected = selected,
                onClick = {
                    if (selected) {
                        topLevelBackStack.dropChildStack()
                    } else {
                        topLevelBackStack.switchTopLevel(item)
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = "Navigate to ${item.title}"
                    )
                },
                label = { Text(item.title) }
            )
        }
    }
}
