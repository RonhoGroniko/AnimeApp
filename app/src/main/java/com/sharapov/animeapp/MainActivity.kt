package com.sharapov.animeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.navigation.compose.rememberNavController
import com.sharapov.core_navigation.NavGraph
import com.sharapov.core_navigation.Screen
import com.sharapov.core_navigation.rememberNavigationState
import com.sharapov.core_ui.theme.AnimeAppTheme
import com.sharapov.feature_details_screen.DetailsScreen
import com.sharapov.feature_main_screen.MainScreen
import com.sharapov.feature_search_screen.SearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navigationState = rememberNavigationState(navController)
            AnimeAppTheme {
                NavGraph(
                    navigationState = navigationState,
                    mainScreenContent = {
                        MainScreen(
                            onCardClick = {
                                navigationState.navigateToDetails(it)
                            },
                            onSettingsClick = {
                                navigationState.navigateTo(Screen.Settings.route)
                            }
                        )
                    },
                    detailsScreenContent = { id ->
                        DetailsScreen(
                            id = id,
                            onBackClick = {
                                navigationState.navController.popBackStack()
                            },
                            onCardClick = {
                                navigationState.navigateToDetails(it)
                            }
                        )
                    },
                    profileScreenContent = { Text("Placeholder profile") },
                    searchScreenContent = {
                        SearchScreen()
                    },
                    favoritesScreenContent = { Text("Placeholder favorites") },
                    settingsScreenContent = { Text("Placeholder settings") }
                )
            }
        }
    }
}
