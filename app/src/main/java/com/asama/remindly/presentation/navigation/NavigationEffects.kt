package com.asama.remindly.presentation.navigation

import android.app.Activity
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

fun NavGraphBuilder.animatedPresenceComposable(
	route: String,
	arguments: List<NamedNavArgument> = emptyList(),
	deepLinks: List<NavDeepLink> = emptyList(),
	content: @Composable() (AnimatedContentScope.(NavBackStackEntry) -> Unit)
) {
	val duration = 500
	composable(
		route = route,
		arguments = arguments,
		deepLinks = deepLinks,
		enterTransition = {
			/*slideIntoContainer(
				towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
				animationSpec = tween(duration + 100),
				initialOffset = {
					it / 4
				}
			) +*/ scaleIn(
				animationSpec = tween(duration, 150),
				initialScale = 0.9f
			) + fadeIn(animationSpec = tween(duration, 150))
		},
		exitTransition = {
			/*slideOutOfContainer(
				towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
				animationSpec = tween(duration + 100),
				targetOffset = {
					it / 4
				}
			) +*/ scaleOut(
				animationSpec = tween(duration),
				targetScale = 1.1f
			) + fadeOut(animationSpec = tween(duration - 100))
		},
		popEnterTransition = {
			/*slideIntoContainer(
				towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
				animationSpec = tween(duration + 100),
				initialOffset = {
					it / 4
				}
			) +*/ scaleIn(
				animationSpec = tween(duration, 150),
				initialScale = 1.1f
			) + fadeIn(animationSpec = tween(duration, 150))
		},
		popExitTransition = {
			/*slideOutOfContainer(
				towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
				animationSpec = tween(duration + 100),
				targetOffset = {
					it / 4
				}
			) +*/ scaleOut(
				animationSpec = tween(duration),
				targetScale = 0.9f
			) + fadeOut(animationSpec = tween(duration - 100))
		},
		content = content
	)
}

@Composable
fun NavigationEffects(
	navigationChannel: Channel<NavigationIntent>,
	navHostController: NavHostController
) {
	val activity = (LocalContext.current as? Activity)
	LaunchedEffect(activity, navHostController, navigationChannel) {
		navigationChannel.receiveAsFlow().collect { intent ->
			if (activity?.isFinishing == true) {
				return@collect
			}
			when (intent) {
				is NavigationIntent.NavigateBack -> {
					if (intent.route != null) {
						navHostController.popBackStack(intent.route, intent.inclusive)
					} else {
						navHostController.popBackStack()
					}
				}

				is NavigationIntent.NavigateTo -> {
					navHostController.navigate(intent.route) {
						launchSingleTop = intent.isSingleTop
						intent.popUpToRoute?.let { popUpToRoute ->
							popUpTo(popUpToRoute) { inclusive = intent.inclusive }
						}
					}
				}
			}
		}
	}
}