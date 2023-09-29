package com.asama.remindly.presentation.features.reminders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asama.remindly.common.Resource
import com.asama.remindly.domain.usecase.ReminderUseCases
import com.asama.remindly.presentation.navigation.AppNavigator
import com.asama.remindly.presentation.navigation.Destination
import com.asama.remindly.presentation.navigation.NavigationIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemindersViewModel @Inject constructor(
	appNavigator: AppNavigator,
	remindersUseCases: ReminderUseCases
) : ViewModel() {

	val navigationChannel = appNavigator.navigationChannel
	private val getReminders = remindersUseCases.getReminders
	private val createReminder = remindersUseCases.createReminder

	private val _uiState = MutableStateFlow(UiState())
	val uiState = _uiState.asStateFlow()

	init {
		getAllReminders()
	}

	private fun getAllReminders() {
		viewModelScope.launch(Dispatchers.IO) {
			getReminders.getAll().collectLatest { response ->
				when (response) {
					is Resource.Success -> {
						val completedCount = response.data.filter { it.completed }.size
						println("getAllReminders: $completedCount")
						_uiState.update {
							UiState(
								reminders = response.data,
							)
						}
						launch {
							delay(2000)
							println("getAllReminders - AFTER: ${_uiState.value.completedCount}")
						}
					}

					is Resource.Error -> {
						_uiState.value = UiState(error = response.message)
					}

					else -> {
						_uiState.value = UiState(isLoading = true)
					}
				}
			}
		}
	}

	fun onEvent(event: ReminderEvent) {
		when (event) {
			is ReminderEvent.CreateNewReminder -> {
				if (event.reminderTitle.isEmpty()) {
					return
				}
				viewModelScope.launch(Dispatchers.IO) {
					createReminder(
						event.reminderTitle,
						event.reminderDescription,
						event.reminderDate
					)
					navigationChannel.send(NavigationIntent.NavigateBack())
				}
			}

			is ReminderEvent.NavigateToCreateReminder -> viewModelScope.launch {
				navigationChannel.send(
					NavigationIntent.NavigateTo(
						route = Destination.CreateReminderScreen.fullRoute,
						isSingleTop = true
					)
				)
			}

			is ReminderEvent.NavigateBack -> viewModelScope.launch {
				navigationChannel.send(NavigationIntent.NavigateBack())
			}
		}
	}
}
