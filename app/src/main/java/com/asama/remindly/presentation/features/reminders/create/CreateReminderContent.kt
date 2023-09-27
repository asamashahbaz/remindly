package com.asama.remindly.presentation.features.reminders.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.asama.remindly.presentation.features.reminders.ReminderEvent
import com.asama.remindly.presentation.features.reminders.UiState

@Composable
fun CreateReminderContent(
	uiState: UiState,
	event: (ReminderEvent) -> Unit
) {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp)
	) {

	}
}