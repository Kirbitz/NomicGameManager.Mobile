package nomic.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.confirmation_dialog_fragment.confirmationText
import mobile.game.manager.nomic.R

class ConfirmationDialogFragment private constructor() : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.confirmation_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = savedInstanceState ?: requireArguments()
        confirmationText.text = bundle.getString("confirmationText")
    }

    companion object {
        fun newInstance(confirmationText : String) : ConfirmationDialogFragment {
            val bundle = Bundle()
            bundle.putString("confirmationText", confirmationText)
            val fragment = ConfirmationDialogFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
