package com.kirabium.relayance.steps

import android.app.Activity
import android.view.View
import androidx.compose.runtime.currentComposer
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitor
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.kirabium.relayance.R
import com.kirabium.relayance.ui.activity.add.AddCustomerActivity
import com.kirabium.relayance.ui.activity.main.MainActivity
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not

class Steps {

    private var decorView: View? = null

    @Given("Je suis sur l'écran d'accueil")
    fun jeSuisSurLEcranDAccueil() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.onActivity {
            decorView = it.window.decorView
        }
    }

    @When("Je navigue vers l'écran d'ajout")
    fun jeNavigueVersLEcranDAjout() {
        onView(withId(R.id.addCustomerFab)).perform(click())
        onView(withId(R.id.nameTextInputLayout)).check(matches(isDisplayed()))
    }

    @And("Je saisis le nom du client")
    fun jeSaisisLeNomDuClient() {
        onView(withId(R.id.nameEditText)).perform(typeText("Jean Dupont"), closeSoftKeyboard())
    }

    @And("Je saisis l'adresse mail du client")
    fun jeSaisisAdresseMailClient() {
        onView(withId(R.id.emailEditText)).perform(typeText("jean@example.com"), closeSoftKeyboard())
    }

    @And("Je clique sur le bouton pour valider l'ajout")
    fun jeCliqueSurBoutonValiderAjout() {
        onView(withId(R.id.saveFab)).perform(click())
    }

    @Then("Je suis redirigé vers l'écran d'accueil")
    fun jeSuisRedirigeVersEcranAccueil() {
        onView(withId(R.id.customerRecyclerView)).check(matches(isDisplayed()))
    }

    @And("Le nouveau client apparait dans la liste")
    fun leNouveauClientApparaitDansLaListe() {
        onView(withText("Jean Dupont")).check(matches(isDisplayed()))
    }

}
