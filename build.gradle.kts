plugins {
    alias(libs.plugins.android.gradle.plugin) apply false
    alias(libs.plugins.dagger.hilt) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.ksp) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral() // Needed to access cucumber-reporting plugin
    }

    dependencies {
        classpath(libs.cucumber.reporting) // Plugin dependency to generate Cucumber reports
    }
}

tasks.register("generateCucumberReport") {
    group = "verification"
    description = "Génère un rapport HTML Cucumber à partir du fichier JSON."

    doLast {
        val jsonReportPath = "app/build/reports/androidTests/connected/cucumber-report.json"
        val outputDir = file("app/build/reports/cucumber-html")

        val reportFile = file(jsonReportPath)
        if (!reportFile.exists()) {
            throw GradleException("Le fichier JSON n'existe pas : $jsonReportPath")
        }

        // Configuration for the report builder
        val config = net.masterthought.cucumber.Configuration(outputDir, "Rapport Cucumber Android")
        config.buildNumber = "1"
        config.addClassifications("Plateforme", "Android")
        config.addClassifications("Test Runner", "Cucumber")

        // Generate the report
        val reportBuilder = net.masterthought.cucumber.ReportBuilder(listOf(jsonReportPath), config)
        reportBuilder.generateReports()

        println("✅ Rapport généré : ${outputDir.absolutePath}/index.html")
    }
}
