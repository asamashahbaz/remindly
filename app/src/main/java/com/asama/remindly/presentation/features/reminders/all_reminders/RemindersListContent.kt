package com.asama.remindly.presentation.features.reminders.all_reminders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.asama.remindly.domain.model.Reminder
import com.asama.remindly.presentation.features.reminders.ReminderEvent
import com.asama.remindly.presentation.features.reminders.UiState
import java.util.Calendar

@Composable
fun RemindersListContent(
	uiState: UiState,
	event: (ReminderEvent) -> Unit
) {
	var title by rememberSaveable { mutableStateOf("") }
	var date by rememberSaveable { mutableLongStateOf(Calendar.getInstance().timeInMillis) }

	Column(
		modifier = Modifier.fillMaxSize().imePadding(),
		verticalArrangement = Arrangement.SpaceBetween
	) {
		LazyColumn {
			if (uiState.reminders.isNotEmpty())
				items(uiState.reminders) { reminder ->
					ReminderItem(reminder)
				}
			else
				item {
					Text(text = "No reminders")
				}
		}

		Row {
			TextField(value = title, onValueChange = { title = it })
			Button(onClick = {
				event(ReminderEvent.CreateNewReminder(title, date))
			}) {
				Text(text = "Create")
			}
		}
	}

}

@Composable
fun ReminderItem(
	reminder: Reminder
) {
	Text(text = reminder.title)
}
