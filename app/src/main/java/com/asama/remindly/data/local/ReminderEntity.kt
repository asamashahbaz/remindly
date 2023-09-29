package com.asama.remindly.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import com.asama.remindly.domain.model.Reminder

@Fts4
@Entity(tableName = "reminders")
data class ReminderEntity(
	@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "rowid") val id: Int? = null,
	val title: String,
	val description: String? = null,
	val forTimestamp: Long,
	val completed: Boolean = false,
) {

	fun toReminder(): Reminder {
		return Reminder(
			title = title,
			forTimestamp = forTimestamp,
			completed = completed
		)
	}
}
