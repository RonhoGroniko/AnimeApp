package com.sharapov.core_ui.theme.core

import com.sharapov.core_domain.Result

sealed interface SectionState<out T> {

    data object Initial : SectionState<Nothing>
    data object Loading : SectionState<Nothing>
    data class Content<T>(val data: T) : SectionState<T>
    data class Error(val type: UiError, val message: String) : SectionState<Nothing>
}


fun <T> Result<T>.toSectionState(): SectionState<T> {
    return when(this) {
        is Result.Error -> {
            val type = exception.toErrorType()
            SectionState.Error(type, type.toUiMessage(message))
        }
        Result.Loading -> {
            SectionState.Loading
        }
        is Result.Success<T> -> {
            SectionState.Content(data)
        }
    }
}

