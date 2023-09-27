package com.asama.remindly.presentation.features.reminders.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asama.remindly.domain.model.Reminder
import com.asama.remindly.presentation.features.reminders.ReminderEvent
import com.asama.remindly.presentation.features.reminders.UiState
import com.asama.remindly.presentation.ui.theme.Orange
import com.asama.remindly.presentation.ui.theme.SecondaryDark
import com.asama.remindly.presentation.ui.theme.Teal
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random

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

		CompletedAndScheduled(completedCount = uiState.completedCount, scheduledCount = uiState.reminders.size - uiState.completedCount)

		Button(
			modifier = Modifier
				.fillMaxWidth()
				.padding(top = 8.dp),
			onClick = { /*TODO*/ },
			colors = ButtonDefaults.buttonColors(
				containerColor = SecondaryDark,
				contentColor = Color.White
			),
			contentPadding = PaddingValues(14.dp),
			shape = RoundedCornerShape(15.dp),
		) {
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Text(
					text = "All Reminders",
					style = LocalTextStyle.current.copy(fontSize = 16.sp)
				)
				Image(
					imageVector = Icons.Default.ArrowOutward,
					colorFilter = ColorFilter.tint(Color.White),
					contentDescription = "Button to view all reminders"
				)
			}
		}

		TodayRemindersColumn(
			modifier = Modifier
				.padding(top = 8.dp),
		) {
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.height(100.dp)
					.weight(1f)
					.clip(RoundedCornerShape(15.dp))
					.background(color = Color.White)
					.clickable { /*TODO*/ }
					.padding(start = 14.dp, end = 14.dp, bottom = 20.dp),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically
			) {
				Column {
					Text(
						text = "About Today",
						style = LocalTextStyle.current.copy(fontSize = 18.sp, color = Color.Black)
					)
					Text(
						text = "Your daily reminders are here",
						style = LocalTextStyle.current.copy(fontSize = 14.sp, color = Color.Gray)
					)
				}
			}

			uiState.reminders.filter { reminder ->
				Calendar.getInstance().apply {
					timeInMillis = reminder.forTimestamp
				}.get(Calendar.DATE) == uiState.currentDate.get(Calendar.DATE) && !reminder.completed
			}.sortedByDescending { it.forTimestamp }.forEachIndexed { index, reminder ->
				TodayReminderCard(index, reminder)
			}
		}
	}

}

@Composable
private fun TodayReminderCard(index: Int, reminder: Reminder) {
	val color = remember {
		getBackgroundColor(index)
	}

	Row(
		modifier = Modifier
			.fillMaxWidth()
			.height(110.dp)
			.clip(RoundedCornerShape(15.dp))
			.background(color = color)
			.clickable { /*TODO*/ }
			.padding(14.dp),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically
	) {
		Text(
			text = (index + 1).toString().clampLength(),
			style = LocalTextStyle.current.copy(
				fontSize = 50.sp,
				color = if (color == Color.Black) Color.White else Color.Black
			),
			modifier = Modifier
				.width(70.dp)
				.offset(y = (-5).dp)
		)
		Text(
			modifier = Modifier
				.padding(start = 5.dp, end = 12.dp)
				.weight(1f),
			text = reminder.title,
			style = LocalTextStyle.current.copy(
				fontSize = 18.sp,
				color = if (color == Color.Black) Color.White else Color.Black,
			),
			maxLines = 2,
			overflow = TextOverflow.Ellipsis
		)
		Image(
			modifier = Modifier.size(32.dp),
			imageVector = Icons.Default.ArrowOutward,
			colorFilter = ColorFilter.tint(if (color == Color.Black) Color.White else Color.Black),
			contentDescription = "Button to view all reminders"
		)
	}
}

private fun getBackgroundColor(index: Int): Color {
	val colors = listOf(Teal, Color.Black, Orange)
	return colors[index % colors.size]
}

@Composable
fun TodayRemindersColumn(
	modifier: Modifier = Modifier,
	content: @Composable () -> Unit
) {
	Layout(
		modifier = modifier,
		content = content
	) { measurables, constraints ->
		val placeables = measurables.map { measurable ->
			measurable.measure(constraints)
		}

		layout(constraints.maxWidth, constraints.maxHeight) {
			var yPosition = 0f

			placeables.forEach { placeable ->
				if (yPosition + placeable.height > constraints.maxHeight) {
					return@layout
				}
				placeable.placeRelative(x = 0, y = yPosition.toInt())

				yPosition += placeable.height * 0.75f
			}
		}
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
		modifier = Modifier
			.size(52.dp)
			.aspectRatio(1f),
		colors = ButtonDefaults.buttonColors(
			containerColor = SecondaryDark,
			contentColor = Color.White
		),
		contentPadding = PaddingValues(0.dp),
		shape = CircleShape,
		onClick = { event(ReminderEvent.NavigateToCreateReminder) }
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
			style = LocalTextStyle.current.copy(
				fontSize = 60.sp,
				baselineShift = BaselineShift(0.3f)
			)
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

@Composable
private fun CompletedAndScheduled(completedCount: Int, scheduledCount: Int) {
	Row(
		modifier = Modifier
			.padding(top = 16.dp)
			.fillMaxWidth(),
		horizontalArrangement = Arrangement.spacedBy(8.dp),
	) {
		Column(
			modifier = Modifier
				.clip(
					RoundedCornerShape(15.dp)
				)
				.background(SecondaryDark)
				.fillMaxWidth(0.5f)
				.padding(14.dp),
		) {
			Text(text = "Completed")
			Text(
				modifier = Modifier.fillMaxWidth(),
				text = completedCount.toString().clampLength(),
				style = LocalTextStyle.current.copy(
					fontSize = 60.sp,
					textAlign = TextAlign.Center,
					baselineShift = BaselineShift(0.2f),
					lineHeight = 50.sp,
					lineHeightStyle = LineHeightStyle(
						alignment = LineHeightStyle.Alignment.Top,
						trim = LineHeightStyle.Trim.None
					)
				)
			)
		}
		Column(
			modifier = Modifier
				.clip(
					RoundedCornerShape(15.dp)
				)
				.background(SecondaryDark)
				.fillMaxWidth()
				.padding(14.dp),
		) {
			Text(text = "Scheduled")

			Text(
				modifier = Modifier.fillMaxWidth(),
				text = scheduledCount.toString()
					.clampLength(),
				style = LocalTextStyle.current.copy(
					fontSize = 60.sp,
					textAlign = TextAlign.Center,
					baselineShift = BaselineShift(0.2f),
					lineHeight = 50.sp,
					lineHeightStyle = LineHeightStyle(
						alignment = LineHeightStyle.Alignment.Top,
						trim = LineHeightStyle.Trim.None
					)
				)
			)
		}
	}
}

private fun String.clampLength() = if (length < 2) "0$this" else if (length > 2) "99+" else this

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