package nomic.ui.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nomic.data.models.RuleRecyclerModel
import nomic.mobile.R

class RuleRecyclerAdapter(val list: MutableList<RuleRecyclerModel>)
    : RecyclerView.Adapter<RuleRecyclerAdapter.RuleViewHolder>() {

    inner class RuleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ruleListConstraintLayout : ConstraintLayout? = view.findViewById(R.id.rulelist_constraint_layout)
        val ruleIndex : TextView? = view.findViewById(R.id.rule_index)
        val ruleTitle : TextView? = view.findViewById(R.id.rule_title)
        val amendButton : ImageButton? = view.findViewById(R.id.amend_button)
        val deleteButton : ImageButton? = view.findViewById(R.id.deleterule_button)
        val amendRecycler : RecyclerView? = view.findViewById(R.id.amend_rv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RuleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rule_card, parent, false)
        return RuleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RuleViewHolder, position: Int) {
        val rule = list[position]

        holder.ruleIndex?.text = rule.rulesAmendmentsDTO.index.toString()
        holder.ruleTitle?.text = rule.rulesAmendmentsDTO.title
        holder.amendRecycler?.setHasFixedSize(true)
        holder.amendRecycler?.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)

        val amendmentAdapter = AmendmentRecyclerAdapter(rule.rulesAmendmentsDTO.amendments)
        holder.amendRecycler?.adapter = amendmentAdapter

        // Make the list actually expand
        val isExpanded = rule.isExpanded
        holder.amendRecycler?.visibility = if (isExpanded) View.VISIBLE else View.GONE

        holder.ruleListConstraintLayout?.setOnClickListener {
            rule.isExpanded = !rule.isExpanded
            notifyItemChanged(position)
        }

        holder.amendButton?.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Pressed add AMENDMENT", Toast.LENGTH_SHORT).show()
        }

        holder.deleteButton?.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Pressed delete RULE", Toast.LENGTH_SHORT).show()
        }
    }
}