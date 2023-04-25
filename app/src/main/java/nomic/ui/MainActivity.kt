package nomic.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nomic.mobile.R
import nomic.ui.activities.RulesListActivity
import nomic.ui.utils.RulesListRecyclerAdapter

class MainActivity : AppCompatActivity() {
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rules_list)
        layoutManager = LinearLayoutManager(this)

        // Grab the recyclerview by ID
        var ruleRecycler: RecyclerView = findViewById(R.id.rule_recycler)
        ruleRecycler.layoutManager = layoutManager
        ruleRecycler.adapter = RulesListRecyclerAdapter(mutableListOf())

    }
}