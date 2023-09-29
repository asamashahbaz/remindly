package com.asama.remindly.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ReminderEntity::class], version = 2)
abstract class RemindersDatabase : RoomDatabase() {
	abstract fun reminderDao(): ReminderDAO
}