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

    private var _binding: MainMenuPageBinding? = null
    private val binding get() = _binding!!
    private lateinit var recentGamesAdapter: RecentGamesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = MainMenuPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: MainMenuViewModel by viewModels { MainMenuViewModelFactory(2, this) }

        recentGamesAdapter = RecentGamesAdapter(viewModel.getGames())

        binding.rvRecentGames.adapter = recentGamesAdapter
        binding.rvRecentGames.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect()
            }
        }

        binding.btnCreateGame.setOnClickListener {
            val intent = Intent(this, ConfigureGameActivity::class.java)
            startActivity(intent)
        }

        binding.btnJoinGame.setOnClickListener {
            Log.d("Join Game", "Hello")
        }

        binding.btnLoadGame.setOnClickListener {
            val intent = Intent(this, RulesListActivity::class.java)
            startActivity(intent)
        }
    }
}