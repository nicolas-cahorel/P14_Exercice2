import com.android.build.gradle.BaseExtension
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension

plugins {
    // Android-related plugins
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    // Dependency injection
    alias(libs.plugins.dagger.hilt)

    // Kotlin and Compose
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.ksp)

    // Code Coverage
    alias(libs.plugins.jacoco)
}

tasks.withType<Test> {
    extensions.configure(JacocoTaskExtension::class) {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

val androidExtension = extensions.getByType<BaseExtension>()

val jacocoTestReport by tasks.registering(JacocoReport::class) {
    dependsOn("testDebugUnitTest", "connectedDebugAndroidTest")

    group = "Reporting"
    description = "Génère un rapport de couverture Jacoco"

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val debugTree = fileTree(layout.buildDirectory.dir("tmp/kotlin-classes/debug"))
    val mainSrc = androidExtension.sourceSets.getByName("main").java.srcDirs

    classDirectories.setFrom(debugTree)
    sourceDirectories.setFrom(files(mainSrc))

    executionData.setFrom(
        files(
            layout.buildDirectory.file("jacoco/testDebugUnitTest.exec"),
            layout.buildDirectory.file("outputs/code-coverage/connected/coverage.ec")
        ).filter { it.exists() }
    )

}

android {
    namespace = "com.kirabium.relayance"
    compileSdk = 35

    testCoverage {
        version = "0.8.8"
    }

    defaultConfig {
        applicationId = "com.kirabium.relayance"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        // For instrumentation test with Cucumber
        testInstrumentationRunner = "com.kirabium.relayance.CucumberTestRunner"

        // For instrumentation test with JUnit
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        testInstrumentationRunnerArguments["optionsAnnotationPackage"] = "com.kirabium.relayance"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
//            isTestCoverageEnabled = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    sourceSets["androidTest"].assets.srcDirs("src/androidTest/assets")

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.espresso.intents)
    implementation(libs.androidx.espresso.contrib)
    implementation(libs.androidx.ui.test.junit4.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(kotlin("test"))
    androidTestImplementation(libs.androidx.rules)

    androidTestImplementation(libs.cucumber.java)
    androidTestImplementation(libs.cucumber.junit)

    androidTestImplementation(libs.cucumber.android)

    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.turbine)

    testImplementation(libs.junit.api)
    testRuntimeOnly(libs.junit.engine)
    testImplementation(libs.junit.params)



    // -------------------------- Dependency Injection (Hilt) --------------------------
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

}