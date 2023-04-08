package nomic.data.models

data class RuleDTO(
    val ruleId: Int,
    val index: Int,
    val description: String,
    val title: String,
    val gameID: Int,
    val active: Boolean,
    val mutable: Boolean
)