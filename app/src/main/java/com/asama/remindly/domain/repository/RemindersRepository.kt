package com.asama.remindly.domain.repository

import com.asama.remindly.common.Resource
import com.asama.remindly.data.local.ReminderEntity
import com.asama.remindly.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

interface RemindersRepository {

	fun getAll(): Flow<Resource<List<Reminder>>>

	suspend fun getById(id: Int): Resource<Reminder?>

	fun getByTitle(title: String): Flow<Resource<List<Reminder>>>

	fun getByDate(date: Long): Flow<Resource<List<Reminder>>>

	suspend fun store(reminder: ReminderEntity)

	suspend fun delete(reminder: ReminderEntity)

}