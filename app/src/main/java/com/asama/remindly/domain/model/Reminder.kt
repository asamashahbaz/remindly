package com.asama.remindly.domain.model

data class Reminder(
	val title: String,
	val forTimestamp: Long,
	val completed: Boolean
)
