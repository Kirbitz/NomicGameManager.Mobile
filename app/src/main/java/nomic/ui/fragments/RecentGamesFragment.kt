package nomic.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import mobile.game.manager.nomic.R
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

    class RecentGamesAdapter(
        private val games: MutableList<GameDTO>
    ) : RecyclerView.Adapter<RecentGamesAdapter.RecentGamesViewHolder>() {


        class RecentGamesViewHolder(gameView: View) : RecyclerView.ViewHolder(gameView)

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecentGamesViewHolder {
            return RecentGamesViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.fragment_recentgames,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(
            holder: RecentGamesAdapter.RecentGamesViewHolder,
            position: Int
        ) {
            TODO("Not yet implemented")
        }

        override fun getItemCount(): Int {
            TODO("Not yet implemented")
        }
    }
}