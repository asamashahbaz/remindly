package com.asama.remindly.presentation.features.reminders.all_reminders

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.asama.remindly.presentation.features.reminders.RemindersViewModel

@Composable
fun RemindersListScreen(
	viewModel: RemindersViewModel
) {
	val uiSate by viewModel.uiState.collectAsState()

	RemindersListContent(
		uiState = uiSate,
		event = viewModel::onEvent
	)
}