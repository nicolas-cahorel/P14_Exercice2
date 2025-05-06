package com.kirabium.relayance.steps

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.kirabium.relayance.R
import com.kirabium.relayance.ui.activity.main.MainActivity
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.hamcrest.Matchers.*
import org.junit.Rule

class Steps {

    private var decorView: View? = null

    @Rule @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Given("Je suis sur l'écran d'accueil")
    fun jeSuisSurLEcranAccueil() {
        decorView = activityRule.activity.window.decorView
        onView(withId(R.id.customerRecyclerView)).check(matches(isDisplayed()))
    }

    @When("Je clique sur le bouton d'ajout")
    fun jeCliqueSurLeBoutonAjout() {
        onView(withId(R.id.addCustomerFab)).perform(click())
    }

    @When("Je suis redirigé vers l'écran d'ajout")
    fun jeSuisRedirigeVersEcranAjout() {
        onView(withId(R.id.nameTextInputLayout)).check(matches(isDisplayed()))
    }

    @When("Je saisis les informations du client valides")
    fun jeSaisisInfosClientValides() {
        onView(withId(R.id.nameEditText)).perform(typeText("Jean Dupont"), closeSoftKeyboard())
        onView(withId(R.id.emailEditText)).perform(typeText("jean@example.com"), closeSoftKeyboard())
    }

    @When("Je clique sur le bouton pour valider l'ajout")
    fun jeCliqueSurBoutonValiderAjout() {
        onView(withId(R.id.saveFab)).perform(click())
    }

    @Then("Un message de confirmation s'affiche")
    fun unMessageDeConfirmationSAffiche() {
        onView(withText("Client ajouté avec succès"))
            .inRoot(withDecorView(not(`is`(decorView))))
            .check(matches(isDisplayed()))
    }

    @Then("Je suis redirigé vers l'écran d'accueil")
    fun jeSuisRedirigeVersEcranAccueil() {
        onView(withId(R.id.customerRecyclerView)).check(matches(isDisplayed()))
    }

    @Then("Le nouveau client est visible dans la liste des clients")
    fun clientAjoute() {
        onView(withText("Jean Dupont")).check(matches(isDisplayed()))
    }
}
