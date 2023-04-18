package nomic.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mobile.game.manager.nomic.R
import mobile.game.manager.nomic.databinding.FragmentRecentgamesBinding
import mobile.game.manager.nomic.databinding.MainMenuPageBinding
import nomic.data.models.GameDTO
import nomic.ui.viewmodels.MainMenuViewModel

class RecentGamesFragment : Fragment() {

    private lateinit var viewModel: MainMenuViewModel
    private lateinit var _binding: MainMenuPageBinding
    private val binding get() = _binding
    private lateinit var recentGamesAdapter: RecentGameAdapter

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = this.context?.let { MainMenuViewModel(1, it) }!!
        recentGamesAdapter = RecentGameAdapter(viewModel.getGames())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainMenuPageBinding.inflate(inflater, container, false)
        return binding.root
    }

}

class RecentGameAdapter(
    private val games: MutableList<GameDTO>
) : RecyclerView.Adapter<RecentGameAdapter.RecentGameViewHolder>() {

    private lateinit var _binding: FragmentRecentgamesBinding
    private val binding get() = _binding

    class RecentGameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentGameViewHolder {
        return RecentGameViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.fragment_recentgames,
                parent,
                false,
            )
        )
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: RecentGameViewHolder, position: Int) {

        val curGame = games[position]
        holder.itemView.apply {
            binding.tvGameTitle.text = curGame.title
            binding.tvCreatedDate.text = curGame.createDate.toString()
        }
    }

}
