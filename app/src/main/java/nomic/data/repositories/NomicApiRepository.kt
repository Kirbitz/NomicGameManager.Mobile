package nomic.data.repositories

import nomic.data.models.AmendmentDTO
import nomic.data.models.GameDTO
import nomic.data.models.ModifyRuleMutabilityDTO
import nomic.data.models.RuleDTO
import nomic.data.models.RulesAmendmentsDTO
import nomic.mobile.BuildConfig

/**
 * Implementation of the [INomicApiRepository][nomic.data.repositories.INomicApiRepository]
 *
 * @see [nomic.data.repositories.INomicApiRepository]
 */
class NomicApiRepository(private val volleyRequester: VolleyRequester) : INomicApiRepository {
    private val baseUrl: String = BuildConfig.SERVER_ROOT_URI

    override suspend fun getRulesAmendmentsList(gameId: Int, tag: String): List<RulesAmendmentsDTO> {
        val endpointUrl = "$baseUrl/rules_amendments/collect/$gameId"
        return volleyRequester.stringRequest(endpointUrl, tag)
    }

    override suspend fun enactRule(newRule: RuleDTO, tag: String): String {
        val endpointUrl = "$baseUrl/rules_amendments/enactRule"
        return volleyRequester.jsonObjectRequest(endpointUrl, newRule, tag)
    }

    override suspend fun repealRule(ruleId: Int, tag: String): String {
        val endpointUrl = "$baseUrl/rules_amendments/repeal_rule/$ruleId"
        return volleyRequester.stringRequest(endpointUrl, tag)
    }

    override suspend fun transmuteRule(ruleId: Int, mutable: ModifyRuleMutabilityDTO, tag: String): String {
        val endpointUrl = "$baseUrl/rules_amendments/transmute_rule/$ruleId"
        return volleyRequester.jsonObjectRequest(endpointUrl, mutable, tag)
    }

    override suspend fun amendRule(newAmendment: AmendmentDTO, tag: String): String {
        val endpointUrl = "$baseUrl/rules_amendments/enactAmendment"
        return volleyRequester.jsonObjectRequest(endpointUrl, newAmendment, tag)
    }

    override suspend fun repealAmendment(amendId: Int, tag: String): String {
        val endpointUrl = "$baseUrl/rules_amendments/repeal_amendment/$amendId"
        return volleyRequester.stringRequest(endpointUrl, tag)
    }

    override suspend fun createGame(newGame: GameDTO, tag: String): String {
        val endpointUrl = "$baseUrl/game/create"
        return volleyRequester.jsonObjectRequest(endpointUrl, newGame, tag)
    }

    override suspend fun getGamesList(size: Int, offset: Int, tag: String): List<GameDTO> {
        val endpointUrl = "$baseUrl/game/list?size=$size&offset=$offset"
        return volleyRequester.stringRequest(endpointUrl, tag)
    }

    /**
     * Cancels all requests that are identified by tag
     *
     * @param tag the identifier of requests in the queue
     */
    fun cancelRequests(tag: String) {
        volleyRequester.cancelRequests(tag)
    }
}
