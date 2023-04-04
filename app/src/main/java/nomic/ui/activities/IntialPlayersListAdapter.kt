package nomic.ui.activities
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import mobile.game.manager.nomic.R


class IntialPlayersListAdapter(
    val IntialPlayers: Array<String>):
    RecyclerView.Adapter<IntialPlayersListAdapter.ViewHolder>(){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val textView: TextView
            init {
                textView = view.findViewById(R.id.rvIntialPlayers)
            }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.intial_players_list,viewGroup,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return IntialPlayers.size
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int){
        viewHolder.textView.text = IntialPlayers [position]

    }
}