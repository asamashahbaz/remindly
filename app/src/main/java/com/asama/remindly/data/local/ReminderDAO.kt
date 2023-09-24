package com.asama.remindly.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDAO {

	@Query("SELECT * FROM reminders")
	fun getAll(): Flow<List<ReminderEntity>>

	@Query("SELECT * FROM reminders WHERE rowid = :id")
	suspend fun getById(id: Int): ReminderEntity?

	@Query("SELECT * FROM reminders WHERE title LIKE :title")
	fun getByTitle(title: String): Flow<List<ReminderEntity?>>

	@Query("SELECT * FROM reminders WHERE dateISO = :dateISO")
	fun getByDateISO(dateISO: String): Flow<List<ReminderEntity?>>

	@Query("SELECT * FROM reminders WHERE dateISO BETWEEN :dateISOStart AND :dateISOEnd")
	fun getByDateISO(dateISOStart: String, dateISOEnd: String): Flow<List<ReminderEntity?>>

	@Insert
	suspend fun store(reminder: ReminderEntity)

	@Delete
	suspend fun delete(reminder: ReminderEntity)
}