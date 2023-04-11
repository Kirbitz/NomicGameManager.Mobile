package nomic.data.repositories

import nomic.data.models.*

/**
 * Repository for API calls to the Nomic API
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

    /**
     * Passes new rule data to the Nomic API
     *
     * @param newRule the rule data to be sent to the Nomic API
     * @param tag Identifier for a request made through the Volley Queue
     * @return String the signifies the rule was created
     */
    suspend fun enactRule(newRule: RuleDTO, tag: String): String

    /**
     * Sends rule data to be repealed to the Nomic API
     *
     * @param ruleId the rule Id to repeal from the database
     * @param tag Identifier for a request made through the Volley Queue
     * @return String that signifies the rule was repealed
     */
    suspend fun repealRule(ruleId: Int, tag: String): String

    /**
     * Sends rule data to be transmuted to the Nomic API
     *
     * @param ruleId the rule Id to repeal from the database
     * @param mutable the flag object of [MutableRuleDTO] to update the rules mutability to
     * @param tag Identifier for a request made through the Volley Queue
     * @return String that signifies the rule was repealed
     */
    suspend fun transmuteRule(ruleId: Int, mutable: MutableRuleDTO, tag: String): String

    /**
     * Sends amendment data to be enacted to the Nomic API
     *
     * @param newAmendment the amendment data of [AmendmentDTO] to be sent to the Nomic API
     * @param tag Identifier for a request made through the Volley Queue
     * @return String that signifies the rule was repealed
     */
    suspend fun amendRule(newAmendment: AmendmentDTO, tag: String): String

    /**
     * Sends amendment id to be repealed to the Nomic API
     *
     * @param amendId the rule Id to repeal from the database
     * @param tag Identifier for a request made through the Volley Queue
     * @return String that signifies the rule was repealed
     */
    suspend fun repealAmendment(amendId: Int, tag: String): String

    /**
     * Sends game data to be created to the Nomic API
     *
     * @param newGame the game data of [GameDTO] to be sent to the Nomic API
     * @param tag Identifier for a request made through the Volley Queue
     * @return String that signifies the rule was repealed
     */
    suspend fun createGame(newGame: GameDTO, tag: String): String
}
