package nomic.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import mobile.game.manager.nomic.R
import mobile.game.manager.nomic.databinding.FragmentCreateRuleBinding


class CreateRuleFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentCreateRuleBinding
    private lateinit var mainViewModel: MainViewModel

    /*
    creates the view of bottom sheet
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        mainViewModel = ViewModelProvider(activity).get(MainViewModel::class.java)
        binding.submitRuleButton.setOnClickListener {
            saveAction()
        }

    }

    /*
        Function that is executed after the save button of the fragment is pushed

     */
    private fun saveAction() {
        dismiss()
    }

    /*
        Inflates the bottom dialog sheet fragment
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateRuleBinding.inflate(inflater,container,false)
        return binding.root
    }


}