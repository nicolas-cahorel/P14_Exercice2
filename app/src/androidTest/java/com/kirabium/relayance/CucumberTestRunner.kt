package com.kirabium.relayance

import io.cucumber.android.runner.CucumberAndroidJUnitRunner
import io.cucumber.junit.CucumberOptions

/**
 * Custom test runner to execute Cucumber tests on Android.
 *
 * This class extends [CucumberAndroidJUnitRunner] to integrate Cucumber BDD tests
 * with Android instrumentation tests.
 *
 * The [CucumberOptions] annotation configures:
 * - `features`: the folder containing the Gherkin feature files.
 * - `glue`: the package containing the step definitions.
 * - `plugin`: output format for test results (e.g., "pretty" for readable console output).
 */
@CucumberOptions(
    features = ["features"],
    glue = ["com.kirabium.relayance.steps"],
    plugin = ["pretty"]
)
class CucumberTestRunner : CucumberAndroidJUnitRunner()
