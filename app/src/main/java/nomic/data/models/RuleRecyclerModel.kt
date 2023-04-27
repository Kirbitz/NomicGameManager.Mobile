package nomic.data.models


// This is mostly the same as the DTO but contains some additional data specific to the recycler view
data class RuleRecyclerModel(
    var rulesAmendmentsDTO: RulesAmendmentsDTO,
    var isExpanded: Boolean = false
)