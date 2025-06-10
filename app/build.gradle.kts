import com.android.build.gradle.BaseExtension

plugins {
    // ================================= Android Gradle Plugin ====================================
    alias(libs.plugins.android.gradle.plugin)
    // ===================================== Kotlin & KSP =========================================
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.ksp)
    // =========================================== Hilt ===========================================
    alias(libs.plugins.dagger.hilt)
    // ========================================== Jacoco ==========================================
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
//    dependsOn("testDebugUnitTest", "connectedDebugAndroidTest")

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
            layout.buildDirectory.file("outputs/all_code_coverage/CucumberCoverage.ec"),
            layout.buildDirectory.file("outputs/all_code_coverage/JUnitCoverage.ec"),
            layout.buildDirectory.file("outputs/all_code_coverage/UnitTestCoverage.exec")
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
//        testInstrumentationRunner = "com.kirabium.relayance.CucumberTestRunner"

        // For instrumentation test with JUnit
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

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

    // ================================= AndroidX Core & Lifecycle ================================
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    // ========================================= Compose ==========================================
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // ===================================== Material Design ======================================
    implementation(libs.material)
    // ======================================== Unit Tests ========================================
    testImplementation(kotlin("test"))
    // ================================= Android Instrumented Tests ===============================
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    implementation(libs.androidx.ui.test.junit4.android)
    // =========================================== Hilt ===========================================
    implementation(libs.hilt)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)
    // ========================================= Cucumber =========================================
    androidTestImplementation(libs.cucumber.java)
    androidTestImplementation(libs.cucumber.junit)
    androidTestImplementation(libs.cucumber.android)
    // =========================================== JUnit ==========================================
    androidTestImplementation(libs.androidx.junit)
    testImplementation(libs.junit)
    testImplementation(libs.junit.api)
    testImplementation(libs.junit.params)
    testRuntimeOnly(libs.junit.engine)
    // ========================================= Espresso =========================================
    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.espresso.intents)
    implementation(libs.androidx.espresso.contrib)
    // ========================================== Mockito =========================================
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    // ========================================== Turbine =========================================
    testImplementation(libs.turbine)


}