package nomic.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// This viewmodel needs to offer the following functionality to the UI
// 1) Retrieve/refresh the list of rules (On page load or when list is updated) - achieved simply by storing the required data
// 2) Retrieve a list of amendments for a rule (IE when the rule is tapped, expand the list) - same thing
// 3) Create a rule
// 4) Amend a rule
// 5) Repeal a rule
// 6) Repeal an amendment
// 7) Transmute a rule
// 8) Retrieve full details for a rule (IE when the rule is held/long tapped, show details)
// 9) Load the data on creation (however it's coming from the repo)
class RulesListViewModel(
    val gameId: Int
    // This is where the repo will go
) : ViewModel() {
    // The private mutable state and the public immutable state
    private val _uiState = MutableStateFlow(RulesListUiState(mutableListOf(), gameId))
    val uiState: StateFlow<RulesListUiState> = _uiState.asStateFlow()

    // FUNCTIONALITY

    //
    fun loadRulesAmendments() {
        _uiState.update { currentState ->
            currentState.copy(
                // This is fine for now, but eventually it needs to call the repo
                // The repo should return a mutable list of some agreed upon model
                rulesList = mutableListOf()
            )
        }
    }

    fun updateRulesAmendments() {

    }

    // This is the minimum required data for making a new rule. Other data either defaults or has to come from environment somehow
    fun createRule(index: Int, title: String, description: String) {
        // Call the repo to create the amendment

        // Reload the rules and amendments
        loadRulesAmendments()
    }

    fun amendRule(ruleId: Int, index: Int, description: String) {

    }

    fun repealRule(ruleId: Int) {

    }

    fun repealAmendment(ruleId: Int, amendId: Int) {

    }

    fun transmuteRule(ruleId: Int) {

    }
}

data class RulesListUiState(
    // This is where any top-level state info needs to go
    val rulesList: MutableList<RuleUiState> = mutableListOf(),
    val gameId: Int
)

data class RuleUiState(
    // This is used to store the state of an individual rule
    val ruleId: Int,
    val index: Int,
    val title: String,
    val description: String,
    val ruleAmendments: MutableList<AmendmentUiState> = mutableListOf()
)

data class AmendmentUiState(
    // This is used to store the state of an individual amendment
    val amendId: Int,
    val index: Int,
    val description: String
)