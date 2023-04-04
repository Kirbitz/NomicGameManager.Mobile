package nomic.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import mobile.game.manager.nomic.databinding.MainMenuPageBinding

class MainMenuActivity : AppCompatActivity() {

    private var _binding: MainMenuPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = MainMenuPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreateGame.setOnClickListener {
            Log.d("Create Game", "Hello")
        }

        binding.btnJoinGame.setOnClickListener {
            Log.d("Join Game", "Hello")
        }

        binding.btnLoadGame.setOnClickListener {
            Log.d("Load Game", "Hello")
        }
    }
}