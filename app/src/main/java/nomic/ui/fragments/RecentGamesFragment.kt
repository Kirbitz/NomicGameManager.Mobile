package nomic.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import mobile.game.manager.nomic.databinding.FragmentRecentgamesBinding
import nomic.data.models.GameDTO
import nomic.ui.viewmodels.MainMenuViewModel

class RecentGamesFragment : Fragment() {

    private lateinit var viewModel: MainMenuViewModel
    private lateinit var _binding: FragmentRecentgamesBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProvider(this)[MainMenuViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecentgamesBinding.inflate(inflater, container, false)
        return binding.root
    }
}

class RecentGameAdapter(
    private val games: MutableList<GameDTO>
) : RecyclerView.Adapter<RecentGameAdapter.RecentGameViewHolder>() {

    private lateinit var _binding: FragmentRecentgamesBinding
    private val binding get() = _binding

    class RecentGameViewHolder(private val binding: FragmentRecentgamesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentGameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentRecentgamesBinding.inflate(layoutInflater, parent, false)
        return RecentGameViewHolder(binding)
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
