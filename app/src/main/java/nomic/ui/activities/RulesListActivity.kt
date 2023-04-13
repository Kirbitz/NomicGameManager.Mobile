package nomic.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nomic.mobile.databinding.RulesListBinding
import nomic.ui.viewmodels.RulesListViewModel
import nomic.ui.viewmodels.RulesListViewModelFactory

class RulesListActivity : AppCompatActivity() {
    private var _binding: RulesListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = RulesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: RulesListViewModel by viewModels { RulesListViewModelFactory(2, this) }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect()
            }
        }
    }
}