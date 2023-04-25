package nomic.ui.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nomic.data.models.AmendmentModel
import nomic.data.models.RulesAmendmentsDTO
import nomic.mobile.R

class AmendListRecyclerAdapter (var amendList: List<AmendmentModel>): RecyclerView.Adapter<AmendListRecyclerAdapter.ViewHolder>() {


    // Called to create the above view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.rule_card, parent, false)
        return ViewHolder(v)
    }

    // Populate the views in the viewholder with data
    override fun onBindViewHolder(holder: ViewHolder, i: Int) {

    }

    // Required to extend this adapter
    override fun getItemCount(): Int {
        return amendList.size
    }

    // holds views?
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // val binding = SubListItemBinding.bind(itemView)
    }
}