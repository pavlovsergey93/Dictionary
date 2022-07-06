import android.annotation.SuppressLint

plugins {
	id("com.android.library")
	id("org.jetbrains.kotlin.android")
	id("kotlin-kapt")
}

android {
	compileSdk = 32

	defaultConfig {
		minSdk = 23
		targetSdk = 32

		testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
		consumerProguardFiles("consumer-rules.pro")
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
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}
}

dependencies {

	implementation("com.android.support:appcompat-v7:28.0.0")
	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("com.android.support.test:runner:1.0.2")
	androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")
	implementation(Deps.KOTLINX_COROUTINES_ANDROID)
	implementation(Deps.OKHTTP)
	implementation(Deps.RETROFIT)
	implementation(Deps.RETROFIT_CONVERTOR_GSON)
	implementation(Deps.RETROFIT_ADAPTER_RX_JAVA3)
	kapt(Deps.ROOM_COMPILER)
	implementation(Deps.ROOM_QUAVA)
	implementation(Deps.ROOM_KTX)
	implementation(project(":app_entities"))
}