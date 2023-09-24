package com.asama.remindly.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Fts4
@Entity(tableName = "reminders")
data class ReminderEntity(
	@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "rowid") val id: Int,
	val title: String,
	val dateISO: String,
)
