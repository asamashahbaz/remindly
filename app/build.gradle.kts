plugins {
	alias(libs.plugins.com.android.application)
	alias(libs.plugins.org.jetbrains.kotlin.android)
	alias(libs.plugins.ksp)
	alias(libs.plugins.dagger.hilt)
}

android {
	namespace = "com.asama.remindly"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.asama.remindly"
		minSdk = 24
		//noinspection EditedTargetSdkVersion
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = true
		}
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	kotlinOptions {
		jvmTarget = "17"
	}
	buildFeatures {
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.5.3"
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
}

dependencies {
	// Defaults
	implementation(libs.core.ktx)
	implementation(libs.lifecycle.runtime.ktx)
	implementation(libs.activity.compose)
	implementation(platform(libs.compose.bom))
	implementation(libs.ui)
	implementation(libs.ui.graphics)
	implementation(libs.ui.tooling.preview)
	implementation(libs.material3)
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.test.ext.junit)
	androidTestImplementation(libs.espresso.core)
	androidTestImplementation(platform(libs.compose.bom))
	androidTestImplementation(libs.ui.test.junit4)
	debugImplementation(libs.ui.tooling)
	debugImplementation(libs.ui.test.manifest)

	// Compose Navigation
	implementation(libs.androidx.navigation.compose)

	// Room Database
	implementation(libs.androidx.room.runtime)
	annotationProcessor(libs.androidx.room.compiler)
	implementation(libs.androidx.room.ktx)
	ksp(libs.androidx.room.compiler)

	// Dagger Hilt
	implementation(libs.hilt.android)
	ksp(libs.hilt.compiler)

	// Material Icons
	implementation(libs.androidx.material.icons.extended)

}