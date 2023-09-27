package com.asama.remindly.presentation.features.reminders.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.ResourceFont
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.asama.remindly.R
import com.asama.remindly.domain.model.Reminder
import com.asama.remindly.presentation.features.reminders.ReminderEvent
import com.asama.remindly.presentation.features.reminders.UiState
import com.asama.remindly.presentation.ui.theme.SecondaryDark
import java.util.Calendar
import java.util.Locale

@Composable
fun RemindersHomeContent(
	uiState: UiState,
	event: (ReminderEvent) -> Unit
) {

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(start = 16.dp, end = 16.dp)
	) {

		HeaderArea(uiState.currentDate, event)

	}

}

@Composable
private fun HeaderArea(calendar: Calendar, event: (ReminderEvent) -> Unit) {
	Row(
		modifier = Modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically
	) {
		CurrentDate(calendar = calendar)
		CreateNewReminderButton(event)
	}
}

@Composable
private fun CreateNewReminderButton(event: (ReminderEvent) -> Unit) {
	Button(
		modifier = Modifier.size(52.dp).aspectRatio(1f),
		colors = ButtonDefaults.buttonColors(
			containerColor = SecondaryDark,
			contentColor = Color.White
		),
		contentPadding = PaddingValues(0.dp),
		shape = CircleShape,
		onClick = { /*TODO*/ }
	) {
		Image(
			imageVector = Icons.Default.Add,
			colorFilter = ColorFilter.tint(Color.White),
			contentDescription = "Button to create new reminder"
		)
	}
}

@Composable
private fun CurrentDate(calendar: Calendar) {
	Row(
		horizontalArrangement = Arrangement.spacedBy(8.dp)
	) {
		Text(
			text = calendar.get(Calendar.DATE).toString(),
			style = LocalTextStyle.current.copy(fontSize = 60.sp, baselineShift = BaselineShift(0.3f))
		)
		Column(
			modifier = Modifier.align(Alignment.CenterVertically),
			verticalArrangement = Arrangement.spacedBy(3.dp)
		) {
			Text(
				text = (calendar.getDisplayName(
					Calendar.MONTH,
					Calendar.LONG,
					Locale.getDefault()
				) ?: "").plus(", ${calendar.get(Calendar.YEAR)}")
			)
			Text(
				text = (calendar.getDisplayName(
					Calendar.DAY_OF_WEEK,
					Calendar.LONG,
					Locale.getDefault()
				) ?: "")
			)
		}
	}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RemindersHomeContentPreview() {
	RemindersHomeContent(
		uiState = UiState(
			reminders = listOf(
				Reminder(
					title = "Title",
					forTimestamp = Calendar.getInstance().timeInMillis,
					completed = false
				)
			)
		),
		event = {}
	)
}