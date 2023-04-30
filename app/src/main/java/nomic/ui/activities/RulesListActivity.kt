package nomic.ui.activities

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
import nomic.mobile.databinding.RulesListBinding
import nomic.ui.fragments.CreateAmendmentFragment
import nomic.ui.fragments.CreateRuleFragment
import nomic.ui.utils.AmendmentRecyclerAdapter
import nomic.ui.utils.RuleRecyclerAdapter
import nomic.ui.viewmodels.RulesListViewModel
import nomic.ui.viewmodels.RulesListViewModelFactory

class RulesListActivity : AppCompatActivity(),
    RuleRecyclerAdapter.RuleClickListener,
    AmendmentRecyclerAdapter.AmendClickListener,
    CreateRuleFragment.SaveRuleListener,
    CreateAmendmentFragment.SaveAmendRuleListener {
    private lateinit var ruleRecycler: RecyclerView
    private lateinit var binding: RulesListBinding
    private lateinit var addRule: ImageButton
    private val rulesListViewModel: RulesListViewModel by viewModels { RulesListViewModelFactory(intent.getIntExtra("GameId", 2), this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rules_list)

        binding = RulesListBinding.inflate(layoutInflater)

        // Grab the recyclerview by ID
        ruleRecycler = findViewById(R.id.rule_recycler)
        ruleRecycler.setHasFixedSize(true)
        ruleRecycler.layoutManager = LinearLayoutManager(this)

        val clickContext = this

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                rulesListViewModel.uiState.collect { uiState ->
                    if (uiState.rulesList.size > 0) {
                        val ruleRecyclerModelList = uiState.rulesList.map { rule -> RuleRecyclerModel(rule) }
                        val ruleAdapter = RuleRecyclerAdapter(ruleRecyclerModelList)
                        ruleAdapter.ruleClickListener = clickContext
                        ruleAdapter.amendClickListener = clickContext
                        ruleRecycler.adapter = ruleAdapter
                    }
                }
            }
        }

        // Add the "add a rule" functionality
        addRule = findViewById(R.id.addRule)
        addRule.setOnClickListener {
            val createRuleFragment = CreateRuleFragment()
            createRuleFragment.saveRuleClickListener = this
            createRuleFragment.show(supportFragmentManager, "newRuleTag")
        }

    }

    override fun amendRule(ruleId: Int) {
        val createAmendmentFragment = CreateAmendmentFragment(ruleId)
        createAmendmentFragment.saveAmendRuleClickListener = this
        createAmendmentFragment.show(supportFragmentManager, "newAmendmentTag")
    }

    override fun deleteRule(ruleId: Int) {
        lifecycleScope.launch {
            rulesListViewModel.repealRule(ruleId)
        }
        Toast.makeText(this, "Rule Deleted", Toast.LENGTH_SHORT).show()
    }

    override fun createNewRule(index: Int, title: String, description: String) {
        lifecycleScope.launch {
            rulesListViewModel.createRule(index, title, description)
        }
        Toast.makeText(this, "Rule Created", Toast.LENGTH_SHORT).show()
    }

    override fun createNewAmendment(ruleId: Int, index: Int, title: String, description: String) {
        lifecycleScope.launch {
            rulesListViewModel.amendRule(ruleId, index, title, description)
        }
        Toast.makeText(this, "Amendment Created", Toast.LENGTH_SHORT).show()
    }

    override fun deleteAmendment(amendId: Int) {
        lifecycleScope.launch {
            rulesListViewModel.repealAmendment(amendId)
        }
        Toast.makeText(this, "Amendment Repealed", Toast.LENGTH_SHORT).show()
    }
}