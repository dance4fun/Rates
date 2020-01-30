@file:Suppress("MayBeConstant")

import org.gradle.api.JavaVersion

object Versions {
  val compileSdk = 29
  val targetSdk = 29
  val minSdk = 21

  val gradlePlugin = "3.3.2"
  val kotlin = "1.3.50"

  // Android
  val appCompat = "1.1.0"
  val recyclerView = "1.1.0"
  val supportConstraint = "1.1.3"
  val archLifecycle = "2.0.0"

  // DI
  val dagger = "2.14.1"

  // Reactive
  val rxJava2 = "2.2.6"
  val rxAndroid2 = "2.1.1"

  // Networking
  val okHttp = "3.14.3"
  val retrofit = "2.6.1"

  // Images
  val glide = "4.7.1"

  // Inspections
  val stetho = "1.5.0"

  // Testing
  val junit = "4.12"
  val mockito = "2.28.2"
  val mockitoKotlin = "1.5.0"
  val hamcrest = "1.3"

  val androidSourceCompatibility = JavaVersion.VERSION_1_8
  val androidTargetCompatibility = JavaVersion.VERSION_1_8
}

object Libs {
  // Android
  val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
  val supportConstraint = "androidx.constraintlayout:constraintlayout:${Versions.supportConstraint}"
  val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
  val archComponentsExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.archLifecycle}"

  // Reactive
  val rxJava2 = "io.reactivex.rxjava2:rxjava:${Versions.rxJava2}"
  val rxAndroid2 = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid2}"

  // Images
  val glide = "com.github.bumptech.glide:glide:${Versions.glide}"

  // Dagger
  val dagger = "com.google.dagger:dagger:${Versions.dagger}"
  val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"

  // Kotlin
  val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
  val kotlinTest = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
  val kotlinTestJUnit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"

  // Networking
  val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
  val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
  val retrofitRx2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
  val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
  val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

  // Gradle
  val gradlePlugin = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
  val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

  // Inspections
  val stetho = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"

  // Testing
  val junit = "junit:junit:${Versions.junit}"
  val mockito = "org.mockito:mockito-core:${Versions.mockito}"
  val mockitoKotlin = "com.nhaarman:mockito-kotlin:${Versions.mockitoKotlin}"
  val archComponentsTesting = "androidx.arch.core:core-testing:${Versions.archLifecycle}"

  val hamcrest = "org.hamcrest:hamcrest-core:${Versions.hamcrest}"
  val hamcrestLib = "org.hamcrest:hamcrest-library:${Versions.hamcrest}"
}
