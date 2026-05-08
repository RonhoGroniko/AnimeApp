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
import com.sharapov.feature_details_screen.ui.DetailsScreen
import com.sharapov.feature_favorites_screen.ui.FavoritesScreen
import com.sharapov.feature_main_screen.ui.MainScreen
import com.sharapov.feature_recommendations_screen.ui.RecommendScreen
import com.sharapov.feature_search_screen.domain.entity.AnimeFilter
import com.sharapov.feature_search_screen.domain.entity.genre.Genre
import com.sharapov.feature_search_screen.ui.SearchScreen
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
                                    animeId = key.id,
                                    onBackClick = { topLevelBackStack.removeLast() },
                                    onCardClick = { animeId ->
                                        topLevelBackStack.add(Screen.Details(animeId))
                                    },
                                    onGenreClick = { genre ->
                                        topLevelBackStack.switchTopLevel(Screen.Search)
                                        topLevelBackStack.add(Screen.SearchWithGenre(genre))
                                    }
                                )
                            }

                            entry<Screen.Recommendations> { key ->
                                RecommendScreen(
                                    onCardClick = { animeId ->
                                        topLevelBackStack.add(Screen.Details(animeId))
                                    }
                                )
                            }

                            entry<Screen.Favorites> { key ->
                                FavoritesScreen(
                                    onCardClick = { animeId ->
                                        topLevelBackStack.add(Screen.Details(animeId))
                                    }
                                )
                            }

                            entry<Screen.Search> { key ->
                                SearchScreen(
                                    onCardClick = { animeId ->
                                        topLevelBackStack.add(Screen.Details(animeId))
                                    }
                                )
                            }

                            entry<Screen.SearchWithGenre> { key ->
                                SearchScreen(
                                    filter = AnimeFilter(
                                        genre = Genre.getIdByName(key.genre).toString()
                                    ),
                                    onCardClick = { animeId ->
                                        topLevelBackStack.add(Screen.Details(animeId))
                                    }
                                )
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

// TODO : РЕШИТЬ ВОПРОС С SECTION STATE
// TODO : CHARACTER DETAILS
// TODO : ПОИСК - ДЕТАЛИ - ОБРАТНО - КРЕСТ ПО QUERY - ИКОНКА УЕХАЛА ВАТАХЕЛИ