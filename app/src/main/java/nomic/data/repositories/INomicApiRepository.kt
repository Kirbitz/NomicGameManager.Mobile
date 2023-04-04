package nomic.data.repositories

import nomic.data.models.RulesAmendmentsDTO

/**
 * Service for api calls to the Nomic API
 */
interface INomicApiRepository {

    /**
     * Collects the rules and amendments data from the Nomic Api
     *
     * @param gameId the game Id to collect rules and amendments data on
     * @param tag Identifier for a request made through the Volley Queue
     * @return The list of [RulesAmendmentsModel][nomic.data.models.RulesAmendmentsDTO] objects
     */
    suspend fun getRulesAmendmentsList(gameId: Int, tag: String): List<RulesAmendmentsDTO>
}