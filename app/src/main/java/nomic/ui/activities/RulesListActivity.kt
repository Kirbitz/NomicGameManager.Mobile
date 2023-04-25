package nomic.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mobile.game.manager.nomic.databinding.ActivityTempBinding
import nomic.ui.activities.MainMenuActivity

// Dummy file for testing changing of activity intent in the MainMenuActivity
class RulesListActivity : AppCompatActivity() {
    private var _binding: ActivityTempBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTempBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView.text = "Configure Game Page"

        binding.button.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }
    }
}