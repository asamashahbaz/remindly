package com.asama.remindly.presentation.navigation

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

	@Provides
	@Singleton
	fun provideAppNavigator(): AppNavigator = AppNavigatorImpl()

}