package com.kirabium.relayance

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider

/**
 * Launches an Activity of type [A] within a [ComposeTestRule] environment,
 * allowing to provide a custom [Intent] and execute code before and after the Activity is started.
 *
 * @param A The Activity class to launch.
 * @param onBefore A lambda executed before launching the Activity. Default is an empty block.
 * @param intentFactory A lambda to create the [Intent] used to launch the Activity,
 *                     receiving the application [Context] as parameter.
 *                     Default creates a simple Intent for Activity [A].
 * @param onAfterLaunched A lambda with receiver [ComposeTestRule] that is executed
 *                        after the Activity has been launched and is ready.
 *
 * This function launches the Activity inside an [ActivityScenario], ensuring proper lifecycle management.
 * It is designed for Compose UI tests to start Activities with custom setup and then perform UI assertions or interactions.
 */
inline fun <reified A : Activity> ComposeTestRule.launch(
    noinline onBefore: () -> Unit = {},
    noinline intentFactory: (Context) -> Intent = {
        Intent(ApplicationProvider.getApplicationContext(), A::class.java)
    },
    noinline onAfterLaunched: ComposeTestRule.() -> Unit
) {
    onBefore()
    // Launch the Activity with the created Intent
    ActivityScenario.launch<A>(intentFactory(ApplicationProvider.getApplicationContext()))
        .use {
            // Once the Activity is ready, execute the Compose test block
            onAfterLaunched()
        }
}
