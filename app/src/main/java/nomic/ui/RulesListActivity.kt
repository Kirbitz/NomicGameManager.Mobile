package nomic.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import nomic.data.models.RuleRecyclerModel
import nomic.mobile.R
import nomic.mobile.databinding.ActivityMainBinding
import nomic.mobile.databinding.ActivityMainBinding
import nomic.ui.fragments.CreateRuleFragment
import nomic.ui.utils.RuleRecyclerAdapter
import nomic.ui.viewmodels.RulesListViewModel
import nomic.ui.viewmodels.RulesListViewModelFactory

class RulesListActivity : AppCompatActivity(), RuleRecyclerAdapter.RuleClickListener {
    private lateinit var ruleRecycler: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private lateinit var addRule: ImageButton
    private val rulesListViewModel: RulesListViewModel by viewModels { RulesListViewModelFactory(2, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rules_list)

        binding = ActivityMainBinding.inflate(layoutInflater)

        // Grab the recyclerview by ID
        ruleRecycler = findViewById(R.id.rule_recycler)
        ruleRecycler.setHasFixedSize(true)
        ruleRecycler.layoutManager = LinearLayoutManager(this)

        val ruleClickContext = this

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                rulesListViewModel.uiState.collect { uiState ->
                    if (uiState.rulesList.size > 0) {
                        val ruleRecyclerModelList = uiState.rulesList.map { rule -> RuleRecyclerModel(rule) }
                        val ruleAdapter = RuleRecyclerAdapter(ruleRecyclerModelList)
                        ruleAdapter.ruleClickListener = ruleClickContext
                        ruleRecycler.adapter = ruleAdapter
                    }
                }
            }
        }

        // Add the "add a rule" functionality
        addRule = findViewById(R.id.addRule)
        addRule.setOnClickListener {
            CreateRuleFragment().show(supportFragmentManager, "newRuleTag")
            lifecycleScope.launch {
                rulesListViewModel.createRule(1001, "Apple", "Banana")
            }
        }

    }

    override fun amendRule(ruleId: Int) {
        Toast.makeText(this, "Pressed add AMENDMENT", Toast.LENGTH_SHORT).show()
    }

    override fun deleteRule(ruleId: Int) {
        lifecycleScope.launch {
            rulesListViewModel.repealRule(ruleId)
        }
        Toast.makeText(this, "Rule Deleted", Toast.LENGTH_SHORT).show()
    }
}