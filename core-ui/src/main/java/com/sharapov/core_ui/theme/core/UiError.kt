package com.sharapov.core_ui.theme.core

import coil3.network.HttpException
import java.io.IOException

sealed interface UiError {
    data object Network : UiError
    data object Server : UiError
    data object Unauthorized : UiError
    data object Unknown : UiError
}

fun Throwable?.toErrorType(): UiError {
    return when (this) {
        is IOException -> UiError.Network
        is HttpException -> UiError.Server
        else -> UiError.Unknown
    }
}

fun UiError.toUiMessage(message: String?): String {
    return when (this) {
        UiError.Network -> "Network error: $message"
        UiError.Server -> "Server error: $message"
        UiError.Unauthorized -> "Unauthorized: $message"
        UiError.Unknown -> "Unknown error: $message"
    }
}