package nomic.data.models


// This is mostly the same as the DTO but contains some additional data specific to the recycler view
data class RuleRecyclerModel(
    val ruleId: Int? = null,
    val index: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val mutable: Boolean = true,
    var amendments: MutableList<AmendmentModel> = mutableListOf(),
    var isExpanded: Boolean = false
)