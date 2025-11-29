package com.sharapov.animeapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.sharapov.core_navigation.BaseBottomBar
import com.sharapov.core_navigation.Screen
import com.sharapov.core_navigation.TopLevelBackStack
import com.sharapov.core_ui.theme.AnimeAppTheme
import com.sharapov.feature_details_screen.DetailsScreen
import com.sharapov.feature_main_screen.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimeAppTheme {
                val topLevelBackStack = rememberSaveable(saver = TopLevelBackStack.saver()) {
                    TopLevelBackStack(Screen.Main)
                }
                Scaffold(
                    bottomBar = { BaseBottomBar(topLevelBackStack) }
                ) {
                    NavDisplay(
                        entryDecorators = listOf(
                            rememberSaveableStateHolderNavEntryDecorator(),
                            rememberViewModelStoreNavEntryDecorator()
                        ),
                        backStack = topLevelBackStack.backStack,
                        onBack = { topLevelBackStack.removeLast() },
                        entryProvider = entryProvider {
                            entry<Screen.Main> { key ->
                                MainScreen(
                                    onCardClick = { animeId ->
                                        topLevelBackStack.add(Screen.Details(animeId))
                                    },
                                    onSettingsClick = {
                                        topLevelBackStack.add(Screen.Settings)
                                    }
                                )
                            }

                            entry<Screen.Details> { key ->
                                DetailsScreen(
                                    animeId = key.id
                                )
                            }

                            entry<Screen.Profile> { key ->

                            }

                            entry<Screen.Favorites> { key ->

                            }

                            entry<Screen.Settings> { key ->

                            }
                        }
                    )
                }
            }
        }
    }
}

//@Composable
//fun DetailsScreen() {
//    Box(modifier = Modifier.fillMaxSize().background(Color.Red))
//}
