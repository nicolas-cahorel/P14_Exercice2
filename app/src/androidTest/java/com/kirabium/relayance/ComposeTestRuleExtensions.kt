package com.kirabium.relayance

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider

/**
 * Lance une Activity A dans un ComposeTestRule, avec un Intent custom
 * et exécute [onAfterLaunched] une fois l'Activity démarrée.
 */
inline fun <reified A : Activity> ComposeTestRule.launch(
    noinline onBefore: () -> Unit = {},
    noinline intentFactory: (Context) -> Intent = {
        Intent(ApplicationProvider.getApplicationContext(), A::class.java)
    },
    noinline onAfterLaunched: ComposeTestRule.() -> Unit
) {
    onBefore()
    // Lance l'Activity avec l'Intent fabriqué
    ActivityScenario.launch<A>(intentFactory(ApplicationProvider.getApplicationContext()))
        .use {
            // Dès que l'Activity est prête, on exécute le block Compose
            onAfterLaunched()
        }
}
