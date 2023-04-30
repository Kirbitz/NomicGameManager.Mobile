package nomic.ui.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import nomic.data.models.AmendmentModel
import nomic.mobile.R

class AmendmentRecyclerAdapter (val amendList: List<AmendmentModel>): RecyclerView.Adapter<AmendmentRecyclerAdapter.AmendmentViewHolder>() {

    public interface AmendClickListener {
        fun deleteAmendment()
    }

    var amendClickListener: AmendClickListener ?= null

    // holds views?
    inner class AmendmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val amendIndex : TextView = itemView.findViewById(R.id.amend_index)
        val amendTitle : TextView = itemView.findViewById(R.id.amend_title)
        val deleteAmendment : ImageButton = itemView.findViewById(R.id.deleteamendment_button)
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

        holder.deleteAmendment.setOnClickListener {
            amendClickListener?.deleteAmendment()
            // Toast.makeText(holder.itemView.context, "Pressed delete AMENDMENT", Toast.LENGTH_SHORT).show()
        }
    }

    // Required to extend this adapter
    override fun getItemCount(): Int {
        return amendList.size
    }


}