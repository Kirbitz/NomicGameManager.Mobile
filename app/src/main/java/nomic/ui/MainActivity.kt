package nomic.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.coroutines.*
import mobile.game.manager.nomic.databinding.ActivityMainBinding
import nomic.data.repositories.NomicApiRepository

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val nomicApiRepository by lazy {
        NomicApiRepository(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val data = nomicApiRepository.getRulesAmendmentsList(2, "Collection").toString()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(applicationContext, data, Toast.LENGTH_LONG).show()
                    }
                } catch (exception: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(applicationContext, "Error Happened", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}