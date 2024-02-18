package com.example.singandsongs.ui.notifications

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.singandsongs.R
import com.google.android.material.textfield.TextInputLayout


class AddPlayListDialogFragment(private val action: (String, Boolean) -> Unit)  : DialogFragment() {

    var name: String = "Nowa lista"
    private var isDefault: Boolean = true

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            builder.setView(inflater.inflate(R.layout.dialog_add_playlist, null))
                .setPositiveButton("dodaj"){ _, _ -> clickPositiveButton() }
                .setNegativeButton("Anuluj") { dialog, _ -> dialog.dismiss() }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun clickPositiveButton() {
        dialog?.let{
            val nameText: TextInputLayout = it.findViewById(R.id.name)
            val isDefaultCheck: CheckBox = it.findViewById(R.id.isDefault)
            name = nameText.editText?.text.toString()
            isDefault = isDefaultCheck.isChecked
            action.invoke(name, isDefault)
        }
    }
}