package nomic.ui.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mobile.game.manager.nomic.R
import nomic.data.models.GameDTO

class RecentGamesAdapter(
    private val games: MutableList<GameDTO>
) : RecyclerView.Adapter<RecentGamesAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var gameTitle: TextView
        var createDate: TextView
        var playBtn: Button

        init {
            gameTitle = itemView.findViewById(R.id.tvGameTitle)
            createDate = itemView.findViewById(R.id.tvCreatedDate)
            playBtn = itemView.findViewById(R.id.btnPlay)
        }
    }

    // Called to create the above view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recentgames, parent, false)
        return ViewHolder(v)
    }

    // Populate the views in the viewholder with data
    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.gameTitle.text = games[i].title
        holder.createDate.text = games[i].createDate.toString()
        holder.playBtn.setOnClickListener {
            Log.d("Game " + games[i].gameId.toString(), "Hello")
        }
    }

    // Required to extend this adapter
    override fun getItemCount(): Int {
        return games.size
    }
}
