package nomic.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import kotlinx.coroutines.*
import mobile.game.manager.nomic.databinding.ActivityMainBinding
import nomic.data.repositories.NomicApiRepository
import nomic.ui.viewmodels.RulesListViewModel
import nomic.ui.viewmodels.RulesListViewModelFactory

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: RulesListViewModel by viewModels {RulesListViewModelFactory(1, this)}

        binding.button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
//                try {
//                    val data = nomicApiRepository.getRulesAmendmentsList(2, "Collection").toString()
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(applicationContext, data, Toast.LENGTH_LONG).show()
//                    }
//                } catch (exception: Exception) {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(applicationContext, "Error Happened", Toast.LENGTH_LONG).show()
//                    }
//                }
            }
        }
    }
}