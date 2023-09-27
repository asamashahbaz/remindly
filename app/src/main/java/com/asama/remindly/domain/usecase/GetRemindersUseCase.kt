package com.asama.remindly.domain.usecase

import com.asama.remindly.domain.repository.RemindersRepository
import javax.inject.Inject

class GetRemindersUseCase @Inject constructor(
	private val repository: RemindersRepository
) {

	fun getAll() = repository.getAll()

	fun getByTitle(title: String) = repository.getByTitle(title)

	fun getByDate(date: Long) = repository.getByDate(date)
}