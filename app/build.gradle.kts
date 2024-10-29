import org.gradle.kotlin.dsl.add
import org.gradle.kotlin.dsl.android
import org.gradle.kotlin.dsl.configureEach
import org.gradle.kotlin.dsl.hilt
import org.gradle.kotlin.dsl.kotlinOptions
import org.gradle.kotlin.dsl.test

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.hajhosseinico.smartpipvideoplayer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hajhosseinico.smartpipvideoplayer"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }

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
        languageVersion = "1.9"
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }

    buildFeatures {
        compose = true
    }

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }

    // Or with Kotlin Gradle DSL:
    tasks.withType<JavaCompile>().configureEach {
        options.compilerArgs.add("-Xlint:deprecation")
    }

    packaging {
        resources {
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {
    // Core Libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.compose)

    // Unit Testing Dependencies
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.runner)

    // Instrumented Testing Dependencies (Android)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.mockk.android)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.junit)
    testImplementation(libs.kotlinx.coroutines.test)

    // UI Test
    androidTestImplementation(libs.androidx.ui.test.junit4.v150)
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test:rules:1.6.1")
    androidTestImplementation("androidx.test:core:1.6.1")


    // Hilt for Dependency Injection in both unit and instrumented tests
    testImplementation(libs.hilt.android.testing)
    kaptTest(libs.hilt.compiler)
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.compiler)

    // Compose and Material Design
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.constraintlayout.compose)
    api(libs.androidx.material.icons.extended)
    api(libs.androidx.ui.tooling)
    implementation(libs.splashscreen)

    // Networking Libraries
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)
    api(libs.kotlinx.serialization.json)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    // Hilt Dependencies
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Other Libraries
    api(libs.glide)
    implementation(libs.extendedspans)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.coil.compose)

    // ExoPlayer core library
    implementation ("androidx.media3:media3-exoplayer:1.4.1")

    // Optional UI component for ExoPlayer
    implementation ("androidx.media3:media3-ui:1.4.1")
    implementation ("com.google.android.material:material:1.12.0")

    implementation ("androidx.compose.material:material-icons-core:1.7.4")
    implementation ("androidx.compose.material:material-icons-extended:1.7.4")
}


kapt {
    javacOptions {
        option("-J--add-exports", "jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED")
    }
    arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
    }

    correctErrorTypes = true
}
