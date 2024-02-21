package com.example.singandsongs.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.singandsongs.R
import com.example.singandsongs.databinding.DialogAddCantoBinding
import com.example.singandsongs.databinding.DialogAddPlaylistBinding
import com.example.singandsongs.model.Canto
import com.example.singandsongs.model.Kind
import com.google.android.material.textfield.TextInputLayout
import java.net.URLEncoder


class AddCantoDialogFragment(private val action: (Int, String, Kind, String) -> Unit,
                             val canto: Canto? = null, val draftName: String? = null)  : DialogFragment() {

    var name: String = "Nowa pieśń"
    var number: Int = 315
    var kind: Kind = Kind.ACCIDENTAL
    var text: String? = null

    private lateinit var binding: DialogAddCantoBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = activity?.let {
            binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.dialog_add_canto, null, false);
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.addContentInput.setOnClickListener { addCantoContentInput() }
        binding.searchButton.setOnClickListener {
            search(getCantoName())
        }
        return binding.root
    }

    private fun addCantoContentInput() {
        dialog?.let{
            binding.addContentInput.visibility = View.GONE
            text = ""
            val cantoContent: TextInputLayout = it.findViewById(R.id.cantoContent)
            cantoContent.visibility = View.VISIBLE
        }

    }

    private fun bindProperties() {
        dialog?.let{
            binding.name.editText?.setText(canto?.name)
            binding.number.editText?.setText(canto?.number.toString())
            binding.kindText.setText(canto?.kind?.text)
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
            text = binding.cantoContentInput.text.toString()
            action.invoke(number, name, kind, text ?: "")
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

    private fun getCantoName(): String {
        return binding.inputName.text.toString()
    }

    private fun search(query: String) {
        val searchUri = Uri.parse("https://www.google.com/search?q=${URLEncoder.encode(query, "UTF-8")}")
        val searchIntent = Intent(Intent.ACTION_VIEW, searchUri)
        searchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            requireActivity().startActivity(searchIntent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}