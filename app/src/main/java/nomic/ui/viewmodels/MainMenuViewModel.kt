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
    val userId: Int,
    context: Context
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

    // Load all of the previous games for the userId
    private fun loadPreviousGames() {
        _uiState.update { currentState ->
            currentState.copy(
                // This is fine for now, but eventually it needs to call the repo
                // The repo should return a mutable list of some agreed upon model
                // Currently using a list of dummy data
                gamesList = mutableListOf(
                    GameDTO(1, "Hello", LocalDate.of(1234,1,2), 1, 3),
                    GameDTO(2, "World", LocalDate.of(5678,9,10), 5, 3),
                    GameDTO(3, "I am Tiernan", LocalDate.of(1337, 4, 20), 8, 3)
                )
            )
        }
    }

    // Returns gamesList as a mutable list of type GameDTO
    fun getGames(): MutableList<GameDTO> {
        return _uiState.value.gamesList
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
        return MainMenuViewModel(userId, context) as T
    }
}
