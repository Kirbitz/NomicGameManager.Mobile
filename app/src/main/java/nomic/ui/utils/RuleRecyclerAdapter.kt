package nomic.ui.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nomic.data.models.RuleRecyclerModel
import nomic.mobile.R

class RuleRecyclerAdapter(val list: MutableList<RuleRecyclerModel>) : RecyclerView.Adapter<RuleRecyclerAdapter.RuleViewHolder>() {


    inner class RuleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val relativeLayout : RelativeLayout? = view.findViewById(R.id.rule_relative_layout)
        // val relativeLayout  : RelativeLayout? = view.findViewById(R.id.amend_layout)
        val ruleIndex : TextView? = view.findViewById(R.id.rule_index)
        val ruleTitle : TextView? = view.findViewById(R.id.rule_title)
        // val downArrow : ImageView? = view.findViewById(R.id.arrow_iv)
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

        holder.ruleIndex?.text = rule.index.toString()
        holder.ruleTitle?.text = rule.title
        holder.amendRecycler?.setHasFixedSize(true)
        holder.amendRecycler?.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)

        val amendmentAdapter = AmendmentRecyclerAdapter(rule.amendments)
        holder.amendRecycler?.adapter = amendmentAdapter

        // Make the list actually expand
        val isExpanded = rule.isExpanded
        holder.amendRecycler?.visibility = if (isExpanded) View.VISIBLE else View.GONE

        holder.relativeLayout?.setOnClickListener {
            rule.isExpanded = !rule.isExpanded
            notifyItemChanged(position)
        }
    }
}