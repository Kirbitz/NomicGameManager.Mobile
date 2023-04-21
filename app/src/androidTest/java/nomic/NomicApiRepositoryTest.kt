package nomic

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import nomic.data.models.AmendmentDTO
import nomic.data.models.AmendmentModel
import nomic.data.models.GameDTO
import nomic.data.models.ModifyRuleMutabilityDTO
import nomic.data.models.RuleDTO
import nomic.data.models.RulesAmendmentsDTO
import nomic.data.repositories.NomicApiRepository
import nomic.data.repositories.VolleyRequester
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class NomicApiRepositoryTest {
    private val nomicApiRepository: NomicApiRepository
    private val rulesAmendmentsList = listOf(
        RulesAmendmentsDTO(
            ruleId = 1234,
            index = 12,
            title = "My Awesome Title",
            description = "My Even Better Description",
            mutable = false,
            mutableListOf(
                AmendmentModel(
                    amendId = 321,
                    index = 21,
                    description = "Wow this rule sucks",
                    title = "Amendment Title"
                )
            )
        )
    )

    private val gameList = listOf(
        GameDTO(
            gameId = 2,
            title = "Wow",
            createDate = LocalDate.now(),
            currentPlayer = 2,
            userId = 3
        )
    )

    init {
        val mockVolleyRequester: VolleyRequester = mockk()

        coEvery { mockVolleyRequester.stringRequest<String>(any(), any()) } returns "You Got A String"
        coEvery { mockVolleyRequester.stringRequest<List<RulesAmendmentsDTO>>("http://10.0.2.2:8080/api/rules_amendments/collect/2", any()) } returns rulesAmendmentsList
        coEvery { mockVolleyRequester.stringRequest<List<GameDTO>>("http://10.0.2.2:8080/api/game/list?size=100&offset=0", any()) } returns gameList
        coEvery { mockVolleyRequester.jsonObjectRequest<Any, String>(any(), any(), any()) } returns "You Got A String"

        nomicApiRepository = NomicApiRepository(mockVolleyRequester)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun collectRulesAmendmentsData_DataReceived_VolleyRequesterHit() = runTest {
        var data: List<RulesAmendmentsDTO> = emptyList()
        launch { data = nomicApiRepository.getRulesAmendmentsList(2, "tag") }
        advanceUntilIdle()

        assertEquals(rulesAmendmentsList.size, data.size)
        assertEquals(rulesAmendmentsList[0].ruleId, data[0].ruleId)
        assertEquals(rulesAmendmentsList[0].amendments.size, data[0].amendments.size)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun sendNewRuleData_SuccessReceived_VolleyRequesterHit() = runTest {
        var successString = ""
        val newRule = RuleDTO(
            ruleId = 21,
            index = 2,
            description = "Hello there",
            title = "Wow awesome title",
            gameID = 22,
            active = false,
            mutable = true
        )
        launch { successString = nomicApiRepository.enactRule(newRule, "tag") }
        advanceUntilIdle()

        assertEquals("You Got A String", successString)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun repealRuleData_SuccessReceived_VolleyRequesterHit() = runTest {
        var successString = ""
        launch { successString = nomicApiRepository.repealRule(2, "tag") }
        advanceUntilIdle()

        assertEquals("You Got A String", successString)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun transmuteRuleData_SuccessReceived_VolleyRequesterHit() = runTest {
        var successString = ""
        val mutableInput = ModifyRuleMutabilityDTO(mutableInput = false)
        launch { successString = nomicApiRepository.transmuteRule(2, mutableInput, "tag") }
        advanceUntilIdle()

        assertEquals("You Got A String", successString)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun sendNewAmendmentData_SuccessReceived_VolleyRequesterHit() = runTest {
        var successString = ""
        val newAmendment = AmendmentDTO(
            ruleId = 123,
            id = 21,
            index = 22,
            title = "This amendment is bad",
            description = "Be More Chill"
        )
        launch { successString = nomicApiRepository.amendRule(newAmendment, "tag") }
        advanceUntilIdle()

        assertEquals("You Got A String", successString)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun repealAmendmentData_SuccessReceived_VolleyRequesterHit() = runTest {
        var successString = ""
        launch { successString = nomicApiRepository.repealAmendment(32, "tag") }
        advanceUntilIdle()

        assertEquals("You Got A String", successString)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun sendNewGameData_SuccessReceived_VolleyRequesterHit() = runTest {
        var successString = ""
        val newGame = GameDTO(
            gameId = 404,
            title = "Apocalypse of the Damned",
            createDate = LocalDate.now(),
            userId = 42
        )
        launch { successString = nomicApiRepository.createGame(newGame, "tag") }
        advanceUntilIdle()

        assertEquals("You Got A String", successString)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun collectGamesData_DataReceived_VolleyRequesterHit() = runTest {
        var data: List<GameDTO> = emptyList()
        launch { data = nomicApiRepository.getGamesList(100, 0, "tag") }
        advanceUntilIdle()

        assertEquals(gameList.size, data.size)
        assertEquals(gameList[0].gameId, data[0].gameId)
    }
}