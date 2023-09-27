package com.asama.remindly.domain.usecase

import javax.inject.Inject

data class ReminderUseCases @Inject constructor(
	val getReminders: GetRemindersUseCase,
	val createReminder: CreateReminderUseCase
)