package nomic.data.models

/**
 * The rules and amendments object to be passed back to the caller of the repository
 *
 * @property ruleId the unique if for the rule
 * @property index the position of the rule within the context of a game
 * @property title the name of this rule
 * @property description the clarifying text for the rule
 * @property mutable flag for mutable/immutable rules
 * @property amendments the list of amendments that are attached to a rule
 * @see[nomic.data.models.AmendmentModel]
 */
data class RulesAmendmentsDTO(
    val ruleId: Int,
    val index: Int,
    val title: String,
    val description: String,
    val mutable: Boolean,
    val amendments: List<AmendmentModel> = listOf()
)

/**
 * The amendments object to be passed back to the caller of the repository
 *
 * @property amendId the primary key for an amendment
 * @property index the position of an amendment under a rule
 * @property description the context of an amendment
 * @property title what an amendment is called
 */
data class AmendmentModel(
    val amendId: Int,
    val index: Int,
    val description: String,
    val title: String
)
