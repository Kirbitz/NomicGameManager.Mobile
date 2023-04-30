package nomic.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import nomic.mobile.databinding.FragmentCreateAmendmentBinding


class CreateAmendmentFragment(private val ruleId: Int) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentCreateAmendmentBinding

    interface SaveAmendRuleListener {
        fun createNewAmendment(ruleId: Int, index: Int, title: String, description: String)
    }

    lateinit var saveAmendRuleClickListener: SaveAmendRuleListener

    /*
        Creates the view of bottom sheet
    */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submitAmendButton.setOnClickListener {
            saveAction()
        }

    }

    /*
        Function that is executed after the save button of the fragment is pushed
    */
    private fun saveAction() {
        //Regex for checking title and description
        val pattern = Regex("^[A-Za-z0-9 ,.!?]*\$")
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
            saveAmendRuleClickListener.createNewAmendment(ruleId, binding.index.text.toString().toInt(), binding.title.text.toString(), binding.description.text.toString())
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