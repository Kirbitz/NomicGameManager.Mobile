package nomic.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nomic.data.models.GameDTO
import nomic.mobile.R
import nomic.mobile.databinding.MainMenuPageBinding
import nomic.ui.ConfigureGameActivity
import nomic.ui.RulesListActivity
import nomic.ui.utils.RecentGamesAdapter
import nomic.ui.viewmodels.MainMenuViewModel
import nomic.ui.viewmodels.MainMenuViewModelFactory

class MainMenuActivity : AppCompatActivity(),
    RecentGamesAdapter.PlayClickListener {

    private var _binding: MainMenuPageBinding? = null
    private val binding get() = _binding!!
    private val mainMenuViewModel: MainMenuViewModel by viewModels { MainMenuViewModelFactory(3, this) }
    private lateinit var rvRecentGames: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflates and sets the view to previously initialized layout
        _binding = MainMenuPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvRecentGames = findViewById(R.id.rvRecentGames)
        rvRecentGames.layoutManager = LinearLayoutManager(this)

        val clickContext = this

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainMenuViewModel.uiState.collect { uiState ->
                    if (uiState.gamesList.size > 0) {
                        val gameList = uiState.gamesList
                        val recentGamesAdapter = RecentGamesAdapter(gameList)
                        recentGamesAdapter.playClickListener = clickContext
                        rvRecentGames.adapter = recentGamesAdapter

                    }
                }
            }
        }
        // Set the Create Game button to change intent to the Game Configuration activity
        binding.btnCreateGame.setOnClickListener {
            val intent = Intent(this, ConfigureGameActivity::class.java)
            startActivity(intent)
        }

        binding.btnJoinGame.setOnClickListener {
            Log.d("Join Game", "Hello")
        }

        // Set the Load Game button to change intent to the Rules List (game page) activity
        binding.btnLoadGame.setOnClickListener {
            Log.d("Load Game", "Hello")
        }
    }

    override fun playGame(gameId: Int?) {
        val intent = Intent(this, RulesListActivity::class.java)
        intent.putExtra("GameId", gameId)
        startActivity(intent)
    }
}