package nomic.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import nomic.data.models.RuleDTO
import nomic.mobile.databinding.FragmentCreateAmendmentBinding


class CreateAmendmentFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentCreateAmendmentBinding
    private lateinit var mainViewModel: MainViewModel

    /*
        Creates the view of bottom sheet
    */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        mainViewModel = ViewModelProvider(activity).get(MainViewModel::class.java)
        binding.submitAmendButton.setOnClickListener {
            saveAction()
        }

    }

    /*
        Function that is executed after the save button of the fragment is pushed
    */
    private fun saveAction() {
        //Regex for checking title and description
        var pattern = Regex("^[A-Za-z0-9 ,.!?]*\$")
        val duration = Toast.LENGTH_SHORT

        if(!pattern.matches(binding.title.text.toString()))
        {
            //Toast pop up notification for regex checking
            val toast = Toast.makeText(context, "Invalid Title Characters", duration)
            toast.show()
        }
        else if(!pattern.matches(binding.description.text.toString()))
        {
            //Toast pop up notification for regex checking
            val toast = Toast.makeText(context, "Invalid Description Characters", duration)
            toast.show()
        }
        else{

            dismiss()
        }

    }

    /*
        Inflates the bottom dialog sheet fragment
    */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateAmendmentBinding.inflate(inflater,container,false)
        return binding.root
    }
}