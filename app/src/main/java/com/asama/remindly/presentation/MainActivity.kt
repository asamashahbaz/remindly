package com.asama.remindly.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.asama.remindly.presentation.features.reminders.RemindersViewModel
import com.asama.remindly.presentation.features.reminders.all_reminders.RemindersListScreen
import com.asama.remindly.presentation.features.reminders.create.CreateReminderScreen
import com.asama.remindly.presentation.features.reminders.home.RemindersHomeScreen
import com.asama.remindly.presentation.navigation.Destination
import com.asama.remindly.presentation.navigation.NavigationEffects
import com.asama.remindly.presentation.navigation.animatedPresenceComposable
import com.asama.remindly.presentation.ui.theme.RemindlyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	@OptIn(ExperimentalAnimationApi::class)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			val navController = rememberNavController()

			val remindersViewModel: RemindersViewModel = viewModel()

			NavigationEffects(
				navigationChannel = remindersViewModel.navigationChannel,
				navHostController = navController
			)

			RemindlyTheme(
				darkTheme = true,
				dynamicColor = false
			) {
				// A surface container using the 'background' color from the theme
				Surface(
					modifier = Modifier.fillMaxSize(),
				) {
					NavHost(
						navController = navController,
						startDestination = Destination.RemindersHomeScreen.fullRoute
					) {
						animatedPresenceComposable(
							route = Destination.RemindersHomeScreen.fullRoute,
						) {
							RemindersHomeScreen(remindersViewModel)
						}
						animatedPresenceComposable(route = Destination.RemindersListScreen.fullRoute) {
							RemindersListScreen(remindersViewModel)
						}
						animatedPresenceComposable(
							route = Destination.CreateReminderScreen.fullRoute,
						) {
							CreateReminderScreen(remindersViewModel)
						}
					}
				}
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
	RemindlyTheme {

	}
}