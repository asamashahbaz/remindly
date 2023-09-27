package com.asama.remindly.data.repository

import com.asama.remindly.common.Resource
import com.asama.remindly.data.local.ReminderDAO
import com.asama.remindly.data.local.ReminderEntity
import com.asama.remindly.domain.model.Reminder
import com.asama.remindly.domain.repository.RemindersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemindersRepositoryImpl @Inject constructor(
	private val reminderDAO: ReminderDAO
) : RemindersRepository {

	override fun getAll(): Flow<Resource<List<Reminder>>> = reminderDAO.getAll().map {
		Resource.Loading
		if (it.isEmpty()) {
			Resource.Error("No reminders found")
		} else {
			Resource.Success(it.map { reminderEntity ->
				reminderEntity.toReminder()
			})
		}
	}

	override suspend fun getById(id: Int): Resource<Reminder?> {
		val reminderEntity = reminderDAO.getById(id) ?: return Resource.Error("Reminder not found")
		return Resource.Success(reminderEntity.toReminder())
	}

	override fun getByTitle(title: String): Flow<Resource<List<Reminder>>> = flow {
		emit(Resource.Loading)
		reminderDAO.getByTitle(title).apply {
			collectLatest {
				if (it.isEmpty()) {
					emit(Resource.Error("No reminders found"))
				} else {
					emit(Resource.Success(it.map { reminderEntity ->
						reminderEntity.toReminder()
					}))
				}
			}
			catch {
				emit(Resource.Error("Error getting reminders with title $title"))
			}
		}
	}

	override fun getByDate(date: Long): Flow<Resource<List<Reminder>>> = flow {
		emit(Resource.Loading)
		reminderDAO.getByDate(date).apply {
			collectLatest {
				if (it.isEmpty()) {
					emit(Resource.Error("No reminders found"))
				} else {
					emit(Resource.Success(it.map { reminderEntity ->
						reminderEntity.toReminder()
					}))
				}
			}
			catch {
				emit(Resource.Error("Error getting reminders with date $date"))
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