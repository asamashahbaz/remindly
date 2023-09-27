package com.asama.remindly.di

import android.content.Context
import androidx.room.Room
import com.asama.remindly.data.local.ReminderDAO
import com.asama.remindly.data.local.RemindersDatabase
import com.asama.remindly.data.repository.RemindersRepositoryImpl
import com.asama.remindly.domain.repository.RemindersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Provides
	@Singleton
	fun provideRemindersDatabase(@ApplicationContext applicationContext: Context): RemindersDatabase {
		return Room.databaseBuilder(
			applicationContext,
			RemindersDatabase::class.java, "reminders_database"
		).fallbackToDestructiveMigration().build()
	}

	@Provides
	fun provideReminderDAO(database: RemindersDatabase): ReminderDAO {
		return database.reminderDao()
	}

	@Provides
	@Singleton
	fun provideRepository(reminderDAO: ReminderDAO): RemindersRepository {
		return RemindersRepositoryImpl(reminderDAO)
	}

}