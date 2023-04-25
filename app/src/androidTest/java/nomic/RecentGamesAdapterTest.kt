package nomic

import android.view.View
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import nomic.data.models.GameDTO
import org.junit.runner.RunWith
import javax.inject.Inject
import nomic.ui.utils.RecentGamesAdapter
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class RecentGamesAdapterTest {
    private val gamesList = mutableListOf(
        GameDTO(1, "Hello", LocalDate.of(1234,1,2), 1, 3),
        GameDTO(2, "World", LocalDate.of(5678,9,10), 5, 3),
        GameDTO(3, "I am Tiernan", LocalDate.of(1337, 4, 20), 8, 3)
    )

    private var adapter = RecentGamesAdapter(gamesList)

    @Test
    fun getItemCount() {
        assertEquals(adapter.itemCount,3)
    }
}