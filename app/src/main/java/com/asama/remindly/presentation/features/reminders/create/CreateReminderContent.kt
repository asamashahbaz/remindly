package com.asama.remindly.presentation.features.reminders.create

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

	val title by rememberSaveable { mutableStateOf("") }
	val description by rememberSaveable { mutableStateOf("") }

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp)
	) {

		HeaderArea(event = event)

		ReminderDateAndTime(currentDate = uiState.currentDate)

	}
}

@Composable
private fun HeaderArea(event: (ReminderEvent) -> Unit) {
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
			onClick = { event(ReminderEvent.NavigateToCreateReminder) }
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
private fun ReminderDateAndTime(currentDate: Calendar) {
	var reminderDate by rememberSaveable {
		mutableLongStateOf(Calendar.getInstance().apply {
			timeInMillis = currentDate.timeInMillis
		}.timeInMillis)
	}
	val calendarDate by rememberSaveable(inputs = arrayOf(reminderDate)) {
		mutableStateOf(Calendar.getInstance().apply {
			timeInMillis = reminderDate
		})
	}
	var pickDate by rememberSaveable { mutableStateOf(false) }
	val datePickerState = rememberDatePickerState(
		initialSelectedDateMillis = reminderDate,
		initialDisplayMode = DisplayMode.Picker
	)

	if (pickDate) {
		DatePickerDialog(
			onDismissRequest = {
				pickDate = false
			},
			confirmButton = {
				Button(onClick = {
					println("Selected date: ${datePickerState.selectedDateMillis}")
					reminderDate = datePickerState.selectedDateMillis ?: reminderDate
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
				text = (calendarDate.getDisplayName(
					Calendar.MONTH,
					Calendar.LONG,
					Locale.getDefault()
				) ?: "").plus(
					" ${calendarDate.get(Calendar.DATE)}, ${
						calendarDate.get(
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
		onClick = { /*TODO*/ },
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
				text = "All Reminders",
				style = LocalTextStyle.current.copy(fontSize = 16.sp)
			)
			Image(
				imageVector = Icons.Default.ArrowOutward,
				colorFilter = ColorFilter.tint(Color.Black),
				contentDescription = "Button to select time for the reminder"
			)
		}
	}
}