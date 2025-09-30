package com.sharapov.feature_main_screen

sealed interface MainScreenCommand {

    data object RefreshData: MainScreenCommand
}