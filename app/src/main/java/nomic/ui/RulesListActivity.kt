package nomic.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nomic.data.models.AmendmentModel
import nomic.data.models.RuleRecyclerModel
import nomic.mobile.R
import nomic.ui.utils.RuleRecyclerAdapter

class RulesListActivity : AppCompatActivity() {
    private lateinit var ruleRecycler: RecyclerView
    private lateinit var ruleList: MutableList<RuleRecyclerModel>
    private lateinit var addRule: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rules_list)

        // Grab the recyclerview by ID
        ruleRecycler = findViewById(R.id.rule_recycler)
        ruleRecycler.setHasFixedSize(true)
        ruleRecycler.layoutManager = LinearLayoutManager(this)
        prepareData()
        val ruleAdapter = RuleRecyclerAdapter(ruleList)
        ruleRecycler.adapter = ruleAdapter

        // Add the "add a rule" functionality
        addRule = findViewById(R.id.addrule_floatingbutton)
        addRule.setOnClickListener {
            Toast.makeText(this, "Pressed add RULE", Toast.LENGTH_SHORT).show()
        }

    }

    private fun prepareData() {
        val amends = mutableListOf(
            AmendmentModel(1, 123456789, ":)", "Hello Amendment!"),
            AmendmentModel(2, 2, ":(", "Some ridiculously long amendment title that won't fit in the textview!"),
            AmendmentModel(3, 3, ":|", "Hello Amendthree!")
        )
        val amendsTwo = amends.toMutableList()
        val amendsThree = amends.toMutableList()

        ruleList = mutableListOf(
            RuleRecyclerModel(1, 1, "What happens if I give the title of the amendment some ridiculously long name",":)", true, amends),
            RuleRecyclerModel(2, 123456789, "Hello Two!",":(", false, amendsTwo),
            RuleRecyclerModel(3, 3, "Hello Tres!",":|", false, amendsThree),
        )

    }
}