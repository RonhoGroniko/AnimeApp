package com.sharapov.core_ui.theme.core

import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.exception.ApolloNetworkException
import com.apollographql.apollo.exception.NoDataException
import java.io.IOException
import java.net.UnknownHostException

sealed interface UiError {
    data object Network : UiError
    data object Server : UiError
    data object Unauthorized : UiError
    data object Unknown : UiError
    data object NoData : UiError
    data object UnknownHostException : UiError
}

fun Throwable?.toErrorType(): UiError {
    return when (this) {
        is UnknownHostException -> UiError.UnknownHostException
        is IOException -> UiError.Network
        is ApolloHttpException -> UiError.Server
        is ApolloNetworkException -> UiError.Server
        is NoDataException -> UiError.NoData
        is ApolloException -> UiError.Server

        else -> UiError.Unknown
    }
}

fun UiError.toUiMessage(message: String?): String {
    return when (this) {
        UiError.Network -> "Network error: $message"
        UiError.Server -> "Server error: $message"
        UiError.Unauthorized -> "Unauthorized: $message"
        UiError.Unknown -> "Unknown error: $message"
        UiError.NoData, UiError.UnknownHostException -> "Check internet connection: $message"
    }
}