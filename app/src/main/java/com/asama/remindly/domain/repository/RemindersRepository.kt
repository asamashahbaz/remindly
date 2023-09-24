package com.asama.remindly.domain.repository

import com.asama.remindly.common.Resource
import com.asama.remindly.data.local.ReminderEntity
import kotlinx.coroutines.flow.Flow

interface RemindersRepository {

	fun getAll(): Flow<Resource<List<ReminderEntity>>>

	suspend fun getById(id: Int): Resource<ReminderEntity?>

	fun getByTitle(title: String): Flow<Resource<List<ReminderEntity>>>

	fun getByDateISO(dateISO: String): Flow<Resource<List<ReminderEntity>>>

	fun getByDateISO(dateISOStart: String, dateISOEnd: String): Flow<Resource<List<ReminderEntity>>>

	suspend fun store(reminder: ReminderEntity)

	suspend fun delete(reminder: ReminderEntity)

}