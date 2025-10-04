package com.sharapov.feature_details_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun DetailsScreen(
    id: Int,
    viewModel: DetailsViewModel = hiltViewModel { factory: DetailsViewModel.Factory ->
        factory.create(id)
    },
) {
    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            viewModel.toString()
        }
    }
}