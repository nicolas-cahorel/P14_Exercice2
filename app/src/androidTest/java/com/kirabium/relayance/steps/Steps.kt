package com.kirabium.relayance.steps

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.kirabium.relayance.R
import com.kirabium.relayance.ui.activity.main.MainActivity
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

/**
 * Step definitions for Cucumber UI tests.
 *
 * Contains steps to navigate and interact with the app's main screen,
 * add customer screen, and verify UI elements during testing.
 */
class Steps {

    private var decorView: View? = null

    /**
     * Launches the MainActivity and stores its decorView.
     */
    @Given("Je suis sur l'écran d'accueil")
    fun jeSuisSurLEcranDAccueil() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.onActivity {
            decorView = it.window.decorView
        }
    }

    /**
     * Clicks the floating action button to navigate to the Add Customer screen
     * and verifies the name input layout is displayed.
     */
    @When("Je navigue vers l'écran d'ajout")
    fun jeNavigueVersLEcranDAjout() {
        onView(withId(R.id.addCustomerFab)).perform(click())
        onView(withId(R.id.nameTextInputLayout)).check(matches(isDisplayed()))
    }

    /**
     * Types the customer's name into the name EditText field.
     */
    @And("Je saisis le nom du client")
    fun jeSaisisLeNomDuClient() {
        onView(withId(R.id.nameEditText)).perform(typeText("Jean Dupont"), closeSoftKeyboard())
    }

    /**
     * Types the customer's email into the email EditText field.
     */
    @And("Je saisis l'adresse mail du client")
    fun jeSaisisAdresseMailClient() {
        onView(withId(R.id.emailEditText)).perform(
            typeText("jean@example.com"),
            closeSoftKeyboard()
        )
    }

    /**
     * Clicks the save floating action button to validate adding the customer.
     */
    @And("Je clique sur le bouton pour valider l'ajout")
    fun jeCliqueSurBoutonValiderAjout() {
        onView(withId(R.id.saveFab)).perform(click())
    }

    /**
     * Checks that the user is redirected to the main screen by verifying
     * the RecyclerView displaying customers is visible.
     */
    @Then("Je suis redirigé vers l'écran d'accueil")
    fun jeSuisRedirigeVersEcranAccueil() {
        onView(withId(R.id.customerRecyclerView)).check(matches(isDisplayed()))
    }

    /**
     * Verifies that the newly added customer appears in the customers list.
     */
    @And("Le nouveau client apparait dans la liste")
    fun leNouveauClientApparaitDansLaListe() {
        onView(withText("Jean Dupont")).check(matches(isDisplayed()))
    }

}
