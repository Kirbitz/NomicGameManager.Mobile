package nomic.data.models

/**
 * Data class for rule to be packaged and sent out
 *
 * @property ruleId the unique id for a rule
 * @property index the position of a rule
 * @property description the details of what a rule is supposed to do
 * @property title the display name of a rule
 * @property gameID the game that the rule is attached to
 * @property active flag to indicated if a rule is visible or not
 * @property mutable flag to indicated if a rule can be modified/deleted
 */
data class RuleDTO(
    val ruleId: Int,
    val index: Int,
    val description: String,
    val title: String,
    val gameID: Int,
    val active: Boolean,
    val mutable: Boolean
)
