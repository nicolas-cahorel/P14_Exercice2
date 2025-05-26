package com.kirabium.relayance

import io.cucumber.android.runner.CucumberAndroidJUnitRunner
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["features"],
    glue = ["com.kirabium.relayance.steps"],
    plugin = ["pretty"]
)
class CucumberTestRunner : CucumberAndroidJUnitRunner()
