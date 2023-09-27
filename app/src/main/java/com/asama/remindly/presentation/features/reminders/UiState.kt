package com.asama.remindly.presentation.features.reminders

import com.asama.remindly.domain.model.Reminder
import java.util.Calendar

data class UiState(
	val reminders: List<Reminder> = emptyList(),
	val currentDate: Calendar = Calendar.getInstance(),
	val isLoading: Boolean = false,
	val error: String = ""
) {
	val completedCount = reminders.filter { it.completed }.size
}