package nomic.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import nomic.mobile.R

class ConfirmationDialogFragment: BottomSheetDialogFragment() {

    private var callback : suspend ()->Unit = {}

    private val confirmationText: TextView by lazy {
        requireView().findViewById(R.id.confirmationText)
    }

    private val confirmationButton: Button by lazy {
        requireView().findViewById(R.id.confirmationButton)
    }

    private val cancelButton: Button by lazy {
        requireView().findViewById(R.id.cancelButton)
    }

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
