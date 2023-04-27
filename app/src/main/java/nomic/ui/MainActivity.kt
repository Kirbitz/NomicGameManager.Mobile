package nomic.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nomic.data.models.AmendmentModel
import nomic.data.models.RuleRecyclerModel
import nomic.mobile.R
import nomic.ui.utils.RuleRecyclerAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var ruleRecycler: RecyclerView
    private lateinit var ruleList: MutableList<RuleRecyclerModel>

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

    }

    private fun prepareData() {
        val amends = mutableListOf(
            AmendmentModel(1, 1, ":)", "Hello Amendment!"),
            AmendmentModel(1, 1, ":(", "Hello Amendtwo!"),
            AmendmentModel(1, 1, ":|", "Hello Amendthree!")
        )
        val amendsTwo = amends.toMutableList()
        val amendsThree = amends.toMutableList()

        ruleList = mutableListOf(
            RuleRecyclerModel(1, 1, "What happens if I give the title of the amendment some ridiculously long name",":)", true, amends),
            RuleRecyclerModel(2, 2, "Hello Two!",":(", false, amendsTwo),
            RuleRecyclerModel(3, 3, "Hello Tres!",":|", false, amendsThree),
        )

    }
}