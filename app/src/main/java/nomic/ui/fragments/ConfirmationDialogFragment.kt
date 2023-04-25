package nomic.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.confirmation_dialog_fragment.cancelButton
import kotlinx.android.synthetic.main.confirmation_dialog_fragment.confirmationButton
import kotlinx.android.synthetic.main.confirmation_dialog_fragment.confirmationText
import mobile.game.manager.nomic.R

class ConfirmationDialogFragment private constructor() : BottomSheetDialogFragment() {

    private var callback : suspend ()->Unit = {}

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

        confirmationButton.setOnClickListener {
            suspend {
                this.callback()
                this.dismiss()
            }
        }

        cancelButton.setOnClickListener {
            // Hides the dialog, but is it the right way to do it?
            this.dismiss()
        }
    }

    fun setOnConfirmationListener(callback: suspend ()->Unit) {
        this.callback = callback
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
