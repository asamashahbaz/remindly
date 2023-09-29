package com.asama.remindly.presentation.features.reminders

sealed class ReminderEvent {
	data class CreateNewReminder(
		val reminderTitle : String,
		val reminderDescription: String,
		val reminderDate: Long
	) : ReminderEvent()
	data object NavigateToCreateReminder : ReminderEvent()
	data object NavigateBack : ReminderEvent()
}
