package nomic.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nomic.data.models.AmendmentModel
import nomic.data.models.RuleRecyclerModel
import nomic.data.models.RulesAmendmentsDTO
import nomic.mobile.R
import nomic.ui.utils.RuleRecyclerAdapter
import nomic.ui.viewmodels.RulesListViewModel
import nomic.ui.viewmodels.RulesListViewModelFactory

class RulesListActivity : AppCompatActivity(), RuleRecyclerAdapter.RuleClickListener {
    private lateinit var ruleRecycler: RecyclerView
    private lateinit var ruleList: MutableList<RuleRecyclerModel>
    private lateinit var addRule: ImageButton
    private val rulesListViewModel: RulesListViewModel by viewModels { RulesListViewModelFactory(2, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rules_list)

        // Grab the recyclerview by ID
        ruleRecycler = findViewById(R.id.rule_recycler)
        ruleRecycler.setHasFixedSize(true)
        ruleRecycler.layoutManager = LinearLayoutManager(this)

        val ruleRecyclerModelList = rulesListViewModel.getRulesAmendments().map { rule -> RuleRecyclerModel(rule, false) }
        val ruleAdapter = RuleRecyclerAdapter(ruleRecyclerModelList)
        ruleAdapter.ruleClickListener = this
        ruleRecycler.adapter = ruleAdapter


        /*lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                rulesListViewModel.uiState.collect { uiState ->
                    if (uiState.rulesList.size > 0) {
                        val ruleAdapter = RuleRecyclerAdapter(rulesListViewModel)
                        ruleRecycler.adapter = ruleAdapter
                    }
                }
            }
        }*/

        // Add the "add a rule" functionality
        addRule = findViewById(R.id.addrule_floatingbutton)
        addRule.setOnClickListener {
            Toast.makeText(this, "Pressed add RULE", Toast.LENGTH_SHORT).show()
        }

    }

    override fun amendRule() {
        Toast.makeText(this, "Pressed add AMENDMENT", Toast.LENGTH_SHORT).show()
    }

    override fun deleteRule() {
        Toast.makeText(this, "Pressed delete RULE", Toast.LENGTH_SHORT).show()
    }
}