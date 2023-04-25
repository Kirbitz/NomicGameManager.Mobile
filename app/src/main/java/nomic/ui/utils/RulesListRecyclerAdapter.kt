package nomic.ui.utils

import android.provider.SyncStateContract.Constants
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import nomic.data.models.AmendmentModel
import nomic.data.models.RecyclerConstants
import nomic.data.models.RuleRecyclerModel
import nomic.data.models.RulesAmendmentsDTO
import nomic.mobile.R

class RulesListRecyclerAdapter(val list: MutableList<RuleRecyclerModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var amends = mutableListOf<AmendmentModel>(
        AmendmentModel(1, 1, ":)", "Hello Amendment!"),
        AmendmentModel(1, 1, ":(", "Hello Amendtwo!"),
        AmendmentModel(1, 1, ":|", "Hello Amendthree!")
    )
    private var amendsTwo = amends.toMutableList()
    private var amendsThree = amends.toMutableList()

    private val data = mutableListOf<RuleRecyclerModel>(
        RuleRecyclerModel(1, 1, "Hello RecyclerView!",":)", true, amends),
        RuleRecyclerModel(2, 2, "Hello Two!",":(", false, amendsTwo),
        RuleRecyclerModel(3, 3, "Hello Tres!",":|", false, amendsThree),
    )


    // Tells you if an item is a RULE (0) or AMEND (1)
    override fun getItemViewType(position: Int): Int {
        return list[position].type
    }

    // Called to create the above view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == RecyclerConstants.RULE) {
            val ruleView: View = LayoutInflater.from(parent.context).inflate(R.layout.rule_card, parent, false)
            RuleViewHolder(ruleView)
        } else {
            val amendView: View = LayoutInflater.from(parent.context).inflate(R.layout.amend_card, parent, false)
            AmendViewHolder(amendView)
        }
    }

    // Populate the views in the viewholder with data
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, pos: Int) {
        val dataPoint = data[pos]
        if (dataPoint.type == RecyclerConstants.RULE) {
            holder as RuleViewHolder
            holder.apply {
                ruleIndex?.text = dataPoint.index.toString()
                ruleIndex?.setOnClickListener {
                    expandOrCollapseRule(dataPoint, pos)
                }
                ruleTitle?.text = dataPoint.title
            }
        } else {
            holder as AmendViewHolder
            holder.apply {
                val singleAmend = dataPoint.amendments.first()
                amendIndex?.text = singleAmend.index.toString()
                amendTitle?.text = singleAmend.title
            }
        }
    }

    private fun expandOrCollapseRule(rule: RuleRecyclerModel, pos: Int) {
        if (rule.isExpanded) {
            collapseRule(pos)
        } else {
            expandRule(pos)
        }
    }

    private fun expandRule(pos: Int) {
        val currRule = data[pos]
        val currAmends = currRule.amendments
        currRule.isExpanded = true
        var nextPos = pos
        if (currRule.type == RecyclerConstants.RULE) {
            currAmends.forEach { amend ->
                var parentRule = RuleRecyclerModel()
                parentRule.type = RecyclerConstants.AMENDMENT
                val subList: MutableList<AmendmentModel> = mutableListOf()
                subList.add(amend)
                parentRule.amendments = subList
                data.add(++nextPos,parentRule)
            }
            notifyDataSetChanged()
        }
    }

    private fun collapseRule(pos: Int) {
        val currRule = data[pos]
        val currAmends = currRule.amendments
        data[pos].isExpanded = false
        if (data[pos].type == RecyclerConstants.RULE) {
            currAmends.forEach { _ ->
                data.removeAt(pos)
            }
            notifyDataSetChanged()
        }
    }

    // Required to extend this adapter
    override fun getItemCount(): Int {
        return data.size
    }

    class RuleViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        val ruleIndex = row.findViewById(R.id.rule_index) as TextView?
        val ruleTitle  = row.findViewById(R.id.rule_title) as TextView?
    }
    class AmendViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        val amendIndex = row.findViewById(R.id.amend_index) as TextView?
        val amendTitle  = row.findViewById(R.id.amend_title) as TextView?
    }
}
