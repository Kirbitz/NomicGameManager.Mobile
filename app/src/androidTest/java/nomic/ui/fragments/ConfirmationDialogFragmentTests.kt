package nomic.ui.fragments

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragment
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.android.synthetic.main.confirmation_dialog_fragment.confirmationText
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConfirmationDialogFragmentTests {

    @Test
    fun test_dialog_containsTitle() {
        val titles = arrayOf("x", "Foo bar", "Hello world")
        for (title in titles) {
            val scenario = launchFragment<ConfirmationDialogFragment>(bundleOf(Pair("confirmationText", title)))
            scenario.onFragment { fragment ->
                assertThat(fragment.confirmationText).isNotNull()
                assertThat(fragment.confirmationText.text).isEqualTo(title)
            }
        }
    }
}