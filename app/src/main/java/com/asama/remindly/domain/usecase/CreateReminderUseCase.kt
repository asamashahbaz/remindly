package com.asama.remindly.domain.usecase

import com.asama.remindly.data.local.ReminderEntity
import com.asama.remindly.domain.repository.RemindersRepository
import javax.inject.Inject

class CreateReminderUseCase @Inject constructor(
	private val repository: RemindersRepository
) {
	suspend operator fun invoke(
		reminderTitle: String,
		reminderDescription: String? = null,
		reminderDate: Long
	) {
		val reminderEntity = ReminderEntity(
			title = reminderTitle,
			description = reminderDescription,
			forTimestamp = reminderDate
		)
		repository.store(reminderEntity)
	}
}