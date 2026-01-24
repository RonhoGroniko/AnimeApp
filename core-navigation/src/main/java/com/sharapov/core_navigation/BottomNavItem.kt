package com.sharapov.core_navigation

import androidx.compose.ui.graphics.vector.ImageVector

interface BottomNavItem {
    val selectedIcon: ImageVector
    val unselectedIcon: ImageVector
    val title: String
}