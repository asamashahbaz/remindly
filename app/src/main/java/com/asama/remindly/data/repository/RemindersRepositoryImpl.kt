package com.asama.remindly.data.repository

import com.asama.remindly.common.Resource
import com.asama.remindly.data.local.ReminderDAO
import com.asama.remindly.data.local.ReminderEntity
import com.asama.remindly.domain.repository.RemindersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemindersRepositoryImpl @Inject constructor(
	private val reminderDAO: ReminderDAO
) : RemindersRepository {

	override fun getAll(): Flow<Resource<List<ReminderEntity>>> = flow {
		emit(Resource.Loading)
		reminderDAO.getAll().apply {
			collectLatest {
				if (it.isEmpty()) {
					emit(Resource.Error("No reminders found"))
				} else {
					emit(Resource.Success(it))
				}
			}
			catch {
				emit(Resource.Error("Error getting reminders"))
			}
		}
	}

	override suspend fun getById(id: Int): Resource<ReminderEntity?> {
		val reminder = reminderDAO.getById(id) ?: return Resource.Error("Reminder not found")
		return Resource.Success(reminder)
	}

	override fun getByTitle(title: String): Flow<Resource<List<ReminderEntity>>> = flow {
		emit(Resource.Loading)
		reminderDAO.getByTitle(title).apply {
			collectLatest {
				if (it.isEmpty()) {
					emit(Resource.Error("No reminders found"))
				} else {
					emit(Resource.Success(it))
				}
			}
			catch {
				emit(Resource.Error("Error getting reminders with title $title"))
			}
		}
	}

	override fun getByDateISO(dateISO: String): Flow<Resource<List<ReminderEntity>>> = flow {
		emit(Resource.Loading)
		reminderDAO.getByDateISO(dateISO).apply {
			collectLatest {
				if (it.isEmpty()) {
					emit(Resource.Error("No reminders found"))
				} else {
					emit(Resource.Success(it))
				}
			}
			catch {
				emit(Resource.Error("Error getting reminders with dateISO $dateISO"))
			}
		}
	}

	override fun getByDateISO(
		dateISOStart: String,
		dateISOEnd: String
	): Flow<Resource<List<ReminderEntity>>> = flow {
		emit(Resource.Loading)
		reminderDAO.getByDateISO(dateISOStart, dateISOEnd).apply {
			collectLatest {
				if (it.isEmpty()) {
					emit(Resource.Error("No reminders found between $dateISOStart and $dateISOEnd"))
				} else {
					emit(Resource.Success(it))
				}
			}
			catch {
				emit(Resource.Error("Error getting reminders with dateISO $dateISOStart to $dateISOEnd"))
			}
		}
	}

	override suspend fun store(reminder: ReminderEntity) {
		reminderDAO.store(reminder)
	}

	override suspend fun delete(reminder: ReminderEntity) {
		reminderDAO.delete(reminder)
	}

}