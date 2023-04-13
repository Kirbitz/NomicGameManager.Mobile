package nomic.ui.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nomic.data.models.RulesAmendmentsDTO
import nomic.mobile.R

class RulesListRecyclerAdapter : RecyclerView.Adapter<RulesListRecyclerAdapter.ViewHolder>() {
    private val data : MutableList<RulesAmendmentsDTO> = mutableListOf()

    // holds views?
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemIndex: TextView
        var itemTitle: TextView

        init {
            itemIndex = itemView.findViewById(R.id.card_index)
            itemTitle = itemView.findViewById(R.id.card_title)
        }
    }

    // Called to create the above view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.rule_card, parent, false)
        return ViewHolder(v)
    }

    // Populate the views in the viewholder with data
    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.itemIndex.text = data[i].index.toString()
        holder.itemTitle.text = data[i].title
    }

    // Required to extend this adapter
    override fun getItemCount(): Int {
        return data.size
    }
}