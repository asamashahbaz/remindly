package com.asama.remindly.presentation.features.reminders.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.asama.remindly.presentation.features.reminders.RemindersViewModel

@Composable
fun CreateReminderScreen(
	remindersViewModel: RemindersViewModel
) {
	val uiState by remindersViewModel.uiState.collectAsState()

	CreateReminderContent(
		uiState = uiState,
		event = remindersViewModel::onEvent
	)
}