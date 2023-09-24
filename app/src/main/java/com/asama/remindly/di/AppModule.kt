package com.asama.remindly.di

import android.content.Context
import androidx.room.Room
import com.asama.remindly.data.local.ReminderDAO
import com.asama.remindly.data.local.RemindersDatabase
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
	fun providesRemindersDatabase(@ApplicationContext applicationContext: Context): RemindersDatabase {
		return Room.databaseBuilder(
			applicationContext,
			RemindersDatabase::class.java, "reminders_database"
		).build()
	}

	@Provides
	fun providesReminderDAO(database: RemindersDatabase): ReminderDAO {
		return database.reminderDao()
	}

}