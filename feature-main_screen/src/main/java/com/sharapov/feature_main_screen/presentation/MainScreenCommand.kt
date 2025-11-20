package com.sharapov.feature_main_screen.presentation

sealed interface MainScreenCommand {

    data object RefreshData: MainScreenCommand
}