package com.kirabium.relayance

import io.cucumber.android.runner.CucumberAndroidJUnitRunner
import io.cucumber.junit.CucumberOptions

@CucumberOptions(
    features = ["features"], // Le dossier assets/features est utilisé par défaut dans Cucumber Android
    glue = ["com.kirabium.relayance.steps"], // Le package où se trouvent vos étapes
    plugin = ["pretty", "json:target/cucumber-report.json"]
)
class CucumberTestRunner : CucumberAndroidJUnitRunner()
