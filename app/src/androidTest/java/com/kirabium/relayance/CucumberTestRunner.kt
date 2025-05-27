package com.kirabium.relayance

import io.cucumber.android.runner.CucumberAndroidJUnitRunner
import io.cucumber.junit.CucumberOptions

@CucumberOptions(
    features = ["features"],
    glue = ["com.kirabium.relayance.steps"],
    plugin = ["pretty"]
)
class CucumberTestRunner : CucumberAndroidJUnitRunner()
