package nomic.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import nomic.mobile.databinding.ConfirmationDialogFragmentBinding

class ConfirmationDialogFragment(
    private val deletingRule: Boolean,
    private val ruleAmendId: Int
): BottomSheetDialogFragment() {
    private lateinit var binding: ConfirmationDialogFragmentBinding

    interface ConfirmClickListener {
        fun confirmClick(deletingRule: Boolean, ruleAmendId: Int)
    }

    lateinit var confirmClickListener: ConfirmClickListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ConfirmationDialogFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.confirmationText.text = if (deletingRule) "Confirm Delete Rule $ruleAmendId" else "Confirm Delete Amendment $ruleAmendId"

        binding.confirmationButton.setOnClickListener {
            this.dismiss()
            confirmClickListener.confirmClick(deletingRule, ruleAmendId)
        }

        binding.cancelButton.setOnClickListener {
            // Hides the dialog, but is it the right way to do it?
            this.dismiss()
        }
    }

//    companion object {
//        fun newInstance(confirmationText : String) : ConfirmationDialogFragment {
//            val bundle = Bundle()
//            bundle.putString("confirmationText", confirmationText)
//            val fragment = ConfirmationDialogFragment(deletingRule:)
//            fragment.arguments = bundle
//            return fragment
//        }
//    }
}
