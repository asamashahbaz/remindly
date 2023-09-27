package com.asama.remindly.presentation.features.reminders.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.asama.remindly.presentation.features.reminders.RemindersViewModel

@Composable
fun RemindersHomeScreen(
	viewModel: RemindersViewModel
) {
	val uiState by viewModel.uiState.collectAsState()

	RemindersHomeContent(
		uiState = uiState,
		event = viewModel::onEvent
	)
}