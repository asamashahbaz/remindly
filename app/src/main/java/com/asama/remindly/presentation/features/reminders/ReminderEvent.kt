package com.asama.remindly.presentation.features.reminders

sealed class ReminderEvent {
	data class CreateNewReminder(
		val reminderTitle : String,
		val reminderDate: Long
	) : ReminderEvent()
}