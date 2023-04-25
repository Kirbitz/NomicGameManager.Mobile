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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mobile.game.manager.nomic.databinding.MainMenuPageBinding
import nomic.ui.ConfigureGameActivity
import nomic.ui.RulesListActivity
import nomic.ui.utils.RecentGamesAdapter
import nomic.ui.viewmodels.MainMenuViewModel
import nomic.ui.viewmodels.MainMenuViewModelFactory

class MainMenuActivity : AppCompatActivity() {

    // Initializes the binding to the main_menu_page.xml layout
    private var _binding: MainMenuPageBinding? = null
    private val binding get() = _binding!!
    // Initializes the adapter to the recycler view for recent games
    private lateinit var recentGamesAdapter: RecentGamesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflates and sets the view to previously initialized layout
        _binding = MainMenuPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Creates the viewModel that will collect information relevant to the MainMenuPage
        val viewModel: MainMenuViewModel by viewModels { MainMenuViewModelFactory(2, this) }

        // Creates an adapter that will use the gamesList from the viewModel
        recentGamesAdapter = RecentGamesAdapter(viewModel.getGames())

        // Changes the binding for the RecyclerView to use the adapter
        binding.rvRecentGames.adapter = recentGamesAdapter
        binding.rvRecentGames.layoutManager = LinearLayoutManager(this)

        // When activity is started collect the uiState
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect()
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
            val intent = Intent(this, RulesListActivity::class.java)
            startActivity(intent)
        }
    }
}