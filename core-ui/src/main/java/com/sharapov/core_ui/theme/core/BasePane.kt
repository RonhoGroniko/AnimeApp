package com.sharapov.core_ui.theme.core

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@Composable
fun <S> BasePane(
    modifier: Modifier = Modifier,
    lceState: LceState<S>,
    includeBottomBarInset: Boolean = true,
    topBar: @Composable (() -> Unit)? = null,
    floatingActionButton: @Composable (() -> Unit)? = null,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable (PaddingValues, LceState<S>) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { topBar?.invoke() },
        floatingActionButton = { floatingActionButton?.invoke() },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (includeBottomBarInset) {
                Spacer(
                    modifier = Modifier.height(BasePaneDefaults.shortNavigationBarInset)
                )
            }
        }
    ) { innerPadding ->

        when (lceState) {
            is LceState.Content<S> -> {
                content(innerPadding, lceState)
            }

            is LceState.Error -> {
                LaunchedEffect(Unit) {
                    snackbarHostState.showSnackbar(lceState.message)
                }
                content(innerPadding, lceState)
            }

            LceState.Initial -> {}

            LceState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    var showProgressIndicator by remember { mutableStateOf(false) }
                    LaunchedEffect(Unit) {
                        delay(1000)
                        showProgressIndicator = true
                    }
                    AnimatedVisibility(visible = showProgressIndicator) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
