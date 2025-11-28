package com.sharapov.core_ui.theme.core

import com.sharapov.core_domain.Result

sealed interface LceState<out T> {
    data object Initial: LceState<Nothing>
    data object Loading : LceState<Nothing>
    data class Error(val type: UiError, val message: String) : LceState<Nothing>
    data class Content<T>(val data: T) : LceState<T>
}


fun <T> stateWithSections(sections: List<SectionState<*>>, content: () -> T): LceState<T> {
    val hasContent = sections.any { it is SectionState.Content<*> }
    val allInitialOrLoading = sections.all { it is SectionState.Initial || it is SectionState.Loading }
    val allErrorOrInitial = sections.all { it is SectionState.Initial || it is SectionState.Error }

    return when {
        !hasContent && allInitialOrLoading -> LceState.Loading
        !hasContent && allErrorOrInitial -> {
            val firstError = sections.firstOrNull { it is SectionState.Error } as? SectionState.Error
            LceState.Error(
                type = firstError?.type ?: UiError.Unknown,
                message = firstError?.message.orEmpty()
            )
        }
        else -> LceState.Content(content())
    }
}


fun <T, R> Result<T>.toLceState(mapper: (T) -> R): LceState<R> {
    return when(this) {
        Result.Loading -> {
            LceState.Loading
        }
        is Result.Success<T> -> {
            LceState.Content(mapper(data))
        }

        is Result.Error -> {
            val type = exception.toErrorType()
            LceState.Error(type, type.toUiMessage(message))
        }
    }
}