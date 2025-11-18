pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AnimeApp"
include(":app")
include(":core-ui")
include(":network-anime")
include(":core-di")
include(":core-navigation")
include(":domain-anime")
include(":feature-main_screen")
include(":feature-details_screen")
include(":feature-search_screen")
include(":database-anime")
include(":data-anime")
