package com.example.singandsongs.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.singandsongs.R
import com.example.singandsongs.model.Canto
import com.example.singandsongs.model.Kind
import com.google.android.material.textfield.TextInputLayout


class AddCantoDialogFragment(private val action: (Int, String, Kind) -> Unit,
                             val canto: Canto? = null, val draftName: String? = null)  : DialogFragment() {

    var name: String = "Nowa pieśń"
    var number: Int = 315
    var kind: Kind = Kind.ACCIDENTAL

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            builder.setView(inflater.inflate(R.layout.dialog_add_canto, null))
                .setPositiveButton(if(canto == null)"dodaj" else "zapisz"){ _, _ -> clickPositiveButton() }
                .setNegativeButton("Anuluj") { dialog, _ -> dialog.dismiss() }
            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
        dialog.setOnShowListener {
            if(this.tag == "add_draft") setDraftImageAndName()
            if(canto != null) bindProperties()
            setKindList()
        }
        return dialog
    }

    private fun bindProperties() {
        dialog?.let{
            val nameText: TextInputLayout = it.findViewById(R.id.name)
            val numberText: TextInputLayout = it.findViewById(R.id.number)
            val kindEdit: AutoCompleteTextView = it.findViewById(R.id.kindText)
            nameText.editText?.setText(canto?.name)
            numberText.editText?.setText(canto?.number.toString())
            kindEdit.setText(canto?.kind?.text)
        }
    }

    private fun setDraftImageAndName() {
        dialog?.let{
            val image: ImageView = it.findViewById(R.id.dialogImage)
            image.setImageResource(R.drawable.rule_draft_svgrepo_com)
            val nameText: TextInputLayout = it.findViewById(R.id.name)
            nameText.editText?.setText(draftName)
        }
    }

    private fun clickPositiveButton() {
        dialog?.let{
            val nameText: TextInputLayout = it.findViewById(R.id.name)
            val numberText: TextInputLayout = it.findViewById(R.id.number)
            name = nameText.editText?.text.toString()
            number = try {
                numberText.editText?.text.toString().toInt()
            } catch (e: java.lang.NumberFormatException) {
                0
            }
            action.invoke( number, name, kind)
        }
    }

    private fun setKindList() {
        val items = Kind.values().map { it.text }
        items.forEach { println(it) }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
        dialog?.let{
            val kindEdit: AutoCompleteTextView = it.findViewById(R.id.kindText)
            kindEdit.setAdapter(adapter)
             kindEdit.setOnClickListener {
                 AdapterView.OnItemClickListener { _, _, position, _ ->
                     kind = Kind.values()[position]
                 }
             }
        }
    }
}