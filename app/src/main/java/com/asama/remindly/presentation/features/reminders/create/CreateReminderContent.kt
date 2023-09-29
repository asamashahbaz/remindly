package com.asama.remindly.presentation.features.reminders.create

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asama.remindly.presentation.features.reminders.ReminderEvent
import com.asama.remindly.presentation.features.reminders.UiState
import com.asama.remindly.presentation.ui.theme.Orange
import com.asama.remindly.presentation.ui.theme.SecondaryDark
import com.asama.remindly.presentation.ui.theme.Teal
import java.util.Calendar
import java.util.Locale

@Composable
fun CreateReminderContent(
	uiState: UiState,
	event: (ReminderEvent) -> Unit
) {

	val reminderDate by rememberSaveable {
		mutableStateOf(Calendar.getInstance().apply {
			timeInMillis = this.timeInMillis
		})
	}
	var title by rememberSaveable { mutableStateOf("") }
	var description by rememberSaveable { mutableStateOf("") }

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp)
	) {

		HeaderArea(
			event = event,
			saveReminder = {
				event(ReminderEvent.CreateNewReminder(
					reminderTitle = title,
					reminderDescription = description,
					reminderDate = reminderDate.timeInMillis
				))
			}
		)

		ReminderDateAndTime(reminderDate = reminderDate)

		ReminderTitleAndDescription(
			title = title,
			setTitle = { title = it },
			description = description,
			setDescription = { description = it }
		)
	}
}

@Composable
private fun HeaderArea(event: (ReminderEvent) -> Unit, saveReminder: () -> Unit) {
	Row(
		modifier = Modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically
	) {
		Button(
			modifier = Modifier
				.size(52.dp)
				.aspectRatio(1f),
			colors = ButtonDefaults.buttonColors(
				containerColor = SecondaryDark,
				contentColor = Color.White
			),
			contentPadding = PaddingValues(0.dp),
			shape = CircleShape,
			onClick = { event(ReminderEvent.NavigateBack) }
		) {
			Image(
				imageVector = Icons.AutoMirrored.Filled.ArrowBack,
				colorFilter = ColorFilter.tint(Color.White),
				contentDescription = "Button to create new reminder"
			)
		}
		Text(
			text = "Add New Reminder",
			style = LocalTextStyle.current.copy(
				fontSize = 18.sp
			)
		)
		Button(
			modifier = Modifier
				.size(52.dp)
				.aspectRatio(1f),
			colors = ButtonDefaults.buttonColors(
				containerColor = Teal,
				contentColor = Color.White
			),
			contentPadding = PaddingValues(0.dp),
			shape = CircleShape,
			onClick = saveReminder
		) {
			Image(
				imageVector = Icons.Default.Check,
				contentDescription = "Button to create new reminder"
			)
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReminderDateAndTime(
	reminderDate: Calendar
) {
	var pickDate by rememberSaveable { mutableStateOf(false) }
	val datePickerState = rememberDatePickerState(
		initialSelectedDateMillis = reminderDate.timeInMillis,
		initialDisplayMode = DisplayMode.Picker
	)

	var pickTime by rememberSaveable { mutableStateOf(false) }
	val timePickerState = rememberTimePickerState(
		initialHour = reminderDate.get(Calendar.HOUR_OF_DAY),
		initialMinute = reminderDate.get(Calendar.MINUTE),
		is24Hour = true
	)

	if (pickDate) {
		DatePickerDialog(
			onDismissRequest = {
				pickDate = false
			},
			confirmButton = {
				Button(onClick = {
					reminderDate.timeInMillis =
						datePickerState.selectedDateMillis ?: reminderDate.timeInMillis
					pickDate = false
				}) {
					Text(text = "Select")
				}
			},
			tonalElevation = 5.dp
		) {
			DatePicker(
				state = datePickerState,
				showModeToggle = false,
				colors = DatePickerDefaults.colors(
					dayContentColor = Orange,
					selectedDayContentColor = Orange,
					selectedDayContainerColor = Orange,
					currentYearContentColor = Color.White,
					selectedYearContainerColor = Orange,
					todayContentColor = Color.White,
					todayDateBorderColor = Color.White,
				),
			)
		}
	}

	if (pickTime) {
		DatePickerDialog(
			onDismissRequest = {
				pickTime = false
			},
			confirmButton = {
				Button(onClick = {
					val updatedTime = reminderDate.apply {
						set(Calendar.HOUR_OF_DAY, timePickerState.hour)
						set(Calendar.MINUTE, timePickerState.minute)
					}
					reminderDate.timeInMillis = updatedTime.timeInMillis
					pickTime = false
				}) {
					Text(text = "Select")
				}
			},
		) {
			TimePicker(
				modifier = Modifier
					.fillMaxWidth()
					.padding(16.dp),
				state = timePickerState
			)
		}
	}

	Button(
		modifier = Modifier
			.fillMaxWidth()
			.padding(top = 32.dp),
		onClick = { pickDate = true },
		colors = ButtonDefaults.buttonColors(
			containerColor = Orange,
			contentColor = Color.Black
		),
		contentPadding = PaddingValues(horizontal = 14.dp, vertical = 18.dp),
		shape = RoundedCornerShape(15.dp),
	) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Image(
				imageVector = Icons.Default.CalendarMonth,
				colorFilter = ColorFilter.tint(Color.Black),
				contentDescription = null
			)
			Text(
				text = (reminderDate.getDisplayName(
					Calendar.MONTH,
					Calendar.LONG,
					Locale.getDefault()
				) ?: "").plus(
					" ${reminderDate.get(Calendar.DATE)}, ${
						reminderDate.get(
							Calendar.YEAR
						)
					}"
				),
				modifier = Modifier
					.padding(start = 8.dp, end = 8.dp)
					.weight(1f),
				style = LocalTextStyle.current.copy(fontSize = 16.sp, color = Color.Black)
			)
			Image(
				imageVector = Icons.Default.ArrowOutward,
				colorFilter = ColorFilter.tint(Color.Black),
				contentDescription = "Button to select date for the reminder"
			)
		}
	}

	Button(
		modifier = Modifier
			.fillMaxWidth()
			.padding(top = 8.dp),
		onClick = { pickTime = true },
		colors = ButtonDefaults.buttonColors(
			containerColor = Teal,
			contentColor = Color.Black
		),
		contentPadding = PaddingValues(horizontal = 14.dp, vertical = 18.dp),
		shape = RoundedCornerShape(15.dp),
	) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Image(
				imageVector = Icons.Default.AccessTimeFilled,
				colorFilter = ColorFilter.tint(Color.Black),
				contentDescription = null
			)
			Text(
				modifier = Modifier
					.padding(start = 8.dp, end = 8.dp)
					.weight(1f),
				text = "${reminderDate.get(Calendar.HOUR_OF_DAY)}:${reminderDate.get(Calendar.MINUTE)}",
				style = LocalTextStyle.current.copy(fontSize = 16.sp, color = Color.Black)
			)
			Image(
				imageVector = Icons.Default.ArrowOutward,
				colorFilter = ColorFilter.tint(Color.Black),
				contentDescription = "Button to select time for the reminder"
			)
		}
	}
}

@Composable
fun ReminderTitleAndDescription(
	title: String,
	setTitle: (String) -> Unit,
	description: String,
	setDescription: (String) -> Unit
) {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(top = 16.dp)
			.clip(RoundedCornerShape(15.dp))
			.background(SecondaryDark),
		verticalArrangement = Arrangement.spacedBy(0.dp)
	) {
		TextField(
			modifier = Modifier.fillMaxWidth(),
			value = title,
			onValueChange = setTitle,
			label = {
				Text(text = "Title")
			},
			colors = TextFieldDefaults.colors(
				disabledTextColor = Color.Transparent,
				focusedIndicatorColor = Color.Transparent,
				unfocusedIndicatorColor = Color.Transparent,
				disabledIndicatorColor = Color.Transparent,
				unfocusedContainerColor = Color.Transparent,
				focusedContainerColor = Color.Transparent,
				cursorColor = Color.White
			),
		)
		Divider(
			modifier = Modifier
				.fillMaxWidth()
				.height(1.dp),
			color = Color.DarkGray
		)
		TextField(
			modifier = Modifier.fillMaxWidth(),
			value = description,
			minLines = 4,
			onValueChange = setDescription,
			label = {
				Text(text = "Description")
			},
			colors = TextFieldDefaults.colors(
				disabledTextColor = Color.Transparent,
				focusedIndicatorColor = Color.Transparent,
				unfocusedIndicatorColor = Color.Transparent,
				disabledIndicatorColor = Color.Transparent,
				unfocusedContainerColor = Color.Transparent,
				focusedContainerColor = Color.Transparent,
				cursorColor = Color.White
			)
		)
	}
}