package nomic.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mobile.game.manager.nomic.R
import nomic.data.models.GameDTO
import nomic.ui.viewmodels.MainMenuViewModel
import mobile.game.manager.nomic.databinding.FragmentRecentgamesBinding

class RecentGamesFragment : Fragment() {

    companion object {
        fun newInstance() = RecentGamesFragment()
    }

    private lateinit var vm: MainMenuViewModel
    private lateinit var _binding: FragmentRecentgamesBinding
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = activity?.run {
            ViewModelProvider(this)[MainMenuViewModel::class.java]
        } ?: throw Exception("Invalid Activity")     }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecentgamesBinding.inflate(inflater,container,false)
        return binding.root
    }
}