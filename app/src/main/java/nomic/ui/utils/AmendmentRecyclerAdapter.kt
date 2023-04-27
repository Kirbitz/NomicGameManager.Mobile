package nomic.ui.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nomic.data.models.AmendmentModel
import nomic.mobile.R

class AmendmentRecyclerAdapter (val amendList: List<AmendmentModel>): RecyclerView.Adapter<AmendmentRecyclerAdapter.AmendmentViewHolder>() {

    // holds views?
    inner class AmendmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val amendIndex : TextView = itemView.findViewById(R.id.amend_index)
        val amendTitle : TextView = itemView.findViewById(R.id.amend_title)
    }

    // Called to create the above view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmendmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.amend_card, parent, false)
        return AmendmentViewHolder(view)
    }

    // Populate the views in the viewholder with data
    override fun onBindViewHolder(holder: AmendmentViewHolder, position: Int) {
        holder.amendIndex.text = amendList[position].index.toString()
        holder.amendTitle.text = amendList[position].title
    }

    // Required to extend this adapter
    override fun getItemCount(): Int {
        return amendList.size
    }


}