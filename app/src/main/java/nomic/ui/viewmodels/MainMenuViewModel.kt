package nomic.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nomic.data.models.GameDTO
import java.time.LocalDate

// This viewmodel needs to offer the following functionality to the UI
// 1) Retrieve/refresh the list of previous games (On page load or when list is updated) - achieved simply by storing the required data
class MainMenuViewModel(
    val userId: Int    // This is where the repo will go
) : ViewModel() {
    // The private mutable state and the public immutable state
    private val _uiState = MutableStateFlow(MainMenuUiState(mutableListOf(), userId))
    val uiState: StateFlow<MainMenuUiState> = _uiState.asStateFlow()

    // initialization
    init {
        viewModelScope.launch {
            loadPreviousGames()
        }
    }

    // FUNCTIONALITY

    // Load all of the rules and amendments on initialization
    fun loadPreviousGames() {
        _uiState.update { currentState ->
            currentState.copy(
                // This is fine for now, but eventually it needs to call the repo
                // The repo should return a mutable list of some agreed upon model
                gamesList = mutableListOf()
            )
        }
    }

    // This gets called when the ViewModel is destroyed/cleared
    override fun onCleared() {
        super.onCleared()
    }
}

data class MainMenuUiState(
    // This is where any top-level state info needs to go
    val gamesList: MutableList<GameDTO> = mutableListOf(),
    val userId: Int
)

class MainMenuViewModelFactory(val userId: Int, val context: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RulesListViewModel(userId, context) as T
    }
}
