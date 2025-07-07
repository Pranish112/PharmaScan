package edu.vt.mobiledev.pharmascan.data

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ResultDialogFragment : DialogFragment() {
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
        fun newInstance(isAuthentic: Boolean, name: String?): ResultDialogFragment {
            val args = Bundle().apply {
                putBoolean("authentic", isAuthentic)
                putString("name", name)
            }
            return ResultDialogFragment().apply { arguments = args }
        }
    }
}