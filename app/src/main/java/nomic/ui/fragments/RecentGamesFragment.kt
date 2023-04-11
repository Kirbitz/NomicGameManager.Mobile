package nomic.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import mobile.game.manager.nomic.databinding.RecentGamesFragmentBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import mobile.game.manager.nomic.R
import nomic.data.models.GameDTO
import nomic.ui.viewmodels.MainMenuViewModel
import nomic.ui.viewmodels.MainMenuViewModelFactory

class RecentGamesFragment : Fragment() {

    companion object {
        fun newInstance() = RecentGamesFragment()
    }

    private lateinit var viewModel: MainMenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainMenuViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: RecentGamesFragmentBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_recentgames, container, false)
        binding.viewModel = vm//attach your viewModel to xml
        return binding.root
    }    }

}