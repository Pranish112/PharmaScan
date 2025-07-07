package edu.vt.mobiledev.pharmascan.data

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

/**
 * DialogFragment that displays the result of a drug scan,
 * indicating whether the drug is authentic or counterfeit.
 */
class ResultDialogFragment : DialogFragment() {
    // Creates and returns the dialog with scan result details
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val isAuthentic = requireArguments().getBoolean("authentic")
        val name = requireArguments().getString("name")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(if (isAuthentic) "Authentic Drug" else "Counterfeit Drug")
            .setMessage(name ?: "Not recognized")
            .setPositiveButton("OK", null)
        return builder.create()
    }

    companion object {

         // Factory method to create a new instance of this dialog
         // with scan result arguments.

        fun newInstance(isAuthentic: Boolean, name: String?): ResultDialogFragment {
            val args = Bundle().apply {
                putBoolean("authentic", isAuthentic)
                putString("name", name)
            }
            return ResultDialogFragment().apply { arguments = args }
        }
    }
}