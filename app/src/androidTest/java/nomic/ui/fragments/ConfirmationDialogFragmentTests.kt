package nomic.ui.fragments

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import nomic.mobile.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConfirmationDialogFragmentTests {

    @Test
    fun test_dialog_containsTitle() {
        val titles = arrayOf("x", "Foo bar", "Hello world")
        for (title in titles) {
            launchFragment<ConfirmationDialogFragment>(bundleOf(Pair("confirmationText", title)))
            onView(withText(title))
                .check(matches(isDisplayed()))
        }
    }

    @Test
    fun test_dialog_hasConfirmation() {
        launchFragment<ConfirmationDialogFragment>(bundleOf(Pair("confirmationText", "Are you sure?")))
        onView(withText(R.string.confirm)).check(matches(isDisplayed()))
    }

    @Test
    fun test_dialog_hasCancellation() {
        launchFragment<ConfirmationDialogFragment>(bundleOf(Pair("confirmationText", "Are you sure?")))
        onView(withText(R.string.cancel)).check(matches(isDisplayed()))
    }

    @Test
    fun test_dialog_cancel_dismissesDialog() {
        val scenario = launchFragment<ConfirmationDialogFragment>(bundleOf(Pair("confirmationText", "Are you sure?")))
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withText(R.string.cancel))
            .perform(click())
            .check(doesNotExist())
    }

    @Test
    fun test_dialog_confirm_dismissesDialog() {
        val scenario = launchFragment<ConfirmationDialogFragment>(bundleOf(Pair("confirmationText", "Are you sure?")))
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withText(R.string.confirm))
            .perform(click())
            .check(doesNotExist())
    }
}
