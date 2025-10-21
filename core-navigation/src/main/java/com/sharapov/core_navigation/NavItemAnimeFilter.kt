package com.sharapov.core_navigation

import android.net.Uri
import android.os.Parcelable
import androidx.navigation.NavType
import androidx.savedstate.SavedState
import com.sharapov.core_navigation.utils.parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
@Parcelize
sealed interface NavItemAnimeFilter: Parcelable {
    @Serializable
    @Parcelize
    data object All : NavItemAnimeFilter
    @Serializable
    @Parcelize
    data object Favorites : NavItemAnimeFilter
    @Serializable
    @Parcelize
    data class ByGenre(val genre: String) : NavItemAnimeFilter
    @Serializable
    @Parcelize
    data class ByRankingType(val rankingTypeName: String) : NavItemAnimeFilter

    companion object {

        private val json = Json {
            classDiscriminator = "kind"
            ignoreUnknownKeys = true
            encodeDefaults = true
        }

        val NavigationType: NavType<NavItemAnimeFilter> = object : NavType<NavItemAnimeFilter>(false) {
            override fun put(
                bundle: SavedState,
                key: String,
                value: NavItemAnimeFilter
            ) {
                bundle.putParcelable(key, value)
            }

            override fun get(
                bundle: SavedState,
                key: String
            ): NavItemAnimeFilter? {
                return bundle.parcelable(key)
            }

            override fun parseValue(value: String): NavItemAnimeFilter {
                val decoded = Uri.decode(value)
                return json.decodeFromString(NavItemAnimeFilter.serializer(), decoded)
            }
        }

        fun toQueryValue(filter: NavItemAnimeFilter): String =
            Uri.encode(json.encodeToString(NavItemAnimeFilter.serializer(), filter))

        fun fromQueryValue(encoded: String): NavItemAnimeFilter =
            json.decodeFromString(NavItemAnimeFilter.serializer(), Uri.decode(encoded))
    }
}