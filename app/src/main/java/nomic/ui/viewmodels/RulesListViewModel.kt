package nomic.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nomic.data.models.RulesAmendmentsDTO
import nomic.data.repositories.NomicApiRepository
import nomic.data.repositories.VolleyRequester

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
    val gameId: Int,
    context: Context
) : ViewModel() {
    // The private mutable state and the public immutable state
    private val _uiState = MutableStateFlow(RulesListUiState(mutableListOf(), gameId))
    val uiState: StateFlow<RulesListUiState> = _uiState.asStateFlow()

    private val volleyRequester by lazy {
        VolleyRequester(context)
    }

    private val nomicApiRepository by lazy {
         NomicApiRepository(volleyRequester, context)
    }

    // initialization
    init {
        viewModelScope.launch {
            loadRulesAmendments()
        }
    }

    // FUNCTIONALITY

    // Load all of the rules and amendments on initialization
    private suspend fun loadRulesAmendments() {
        _uiState.update { currentState ->
            currentState.copy(
                // This is fine for now, but eventually it needs to call the repo
                // The repo should return a mutable list of some agreed upon model
                 rulesList = nomicApiRepository.getRulesAmendmentsList(gameId, "loadRulesAmendments") as MutableList<RulesAmendmentsDTO>
            )
        }
    }

    fun getRulesAmendments(): MutableList<RulesAmendmentsDTO> {
        return _uiState.value.rulesList
    }

    // Update the rules and amendments list (not clear what that means yet)
    fun updateRulesAmendments() {

    }

    // This is the minimum required data for making a new rule. Other data either defaults or has to come from environment somehow
    fun createRule(index: Int, title: String, description: String) {
        // Call the repo to create the amendment

        // Reload the rules and amendments
    }

    // Amend a rule in the local list
    fun amendRule(ruleId: Int, index: Int, description: String) {

    }

    fun repealRule(ruleId: Int) {

    }

    fun repealAmendment(ruleId: Int, amendId: Int) {

    }

    fun transmuteRule(ruleId: Int) {

    }

    // This gets called when the ViewModel is destroyed/cleared
    override fun onCleared() {
        super.onCleared()
    }
}

data class RulesListUiState(
    // This is where any top-level state info needs to go
    val rulesList: MutableList<RulesAmendmentsDTO> = mutableListOf(),
    val gameId: Int
)

class RulesListViewModelFactory(val gameId: Int, val context: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RulesListViewModel(gameId, context) as T
    }
}
