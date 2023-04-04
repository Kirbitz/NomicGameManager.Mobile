package nomic.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mobile.game.manager.nomic.R
import nomic.ui.viewmodels.GameUiState
import nomic.ui.viewmodels.MainMenuViewModel

class RecentGamesFragment : Fragment() {

    companion object {
        fun newInstance() = RecentGamesFragment()
    }

    private lateinit var viewModel: MainMenuViewModel
    var gamesList : MutableList<GameUiState> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainMenuViewModel::class.java]
        gamesList = viewModel.loadPreviousGames()
        val listIterator = gamesList.listIterator()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.recent_games_main_menu, container, false)
    }

}