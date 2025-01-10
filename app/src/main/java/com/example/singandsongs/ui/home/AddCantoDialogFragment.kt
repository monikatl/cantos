package com.example.singandsongs.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.singandsongs.R
import com.example.singandsongs.databinding.DialogAddCantoBinding
import com.example.singandsongs.model.Canto
import com.example.singandsongs.model.Kind
import java.net.URLEncoder

class AddCantoDialogFragment(
  private val onAction: (Int, String, Kind, Int, String, Canto?) -> Unit,
  private val canto: Canto? = null,
  private val draftName: String? = null
) : DialogFragment() {

  private lateinit var binding: DialogAddCantoBinding

  private var name: String = "Nowa pieśń"
  private var number: Int = 315
  private var kind: Kind = Kind.ACCIDENTAL
  private var text: String? = null
  private var pageCounter: Int = 1

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    binding = DataBindingUtil.inflate(
      LayoutInflater.from(requireContext()),
      R.layout.dialog_add_canto,
      null,
      false
    )

    return AlertDialog.Builder(requireContext())
      .setView(binding.root)
      .setPositiveButton(if (canto == null) "Dodaj" else "Zapisz") { _, _ -> handlePositiveClick() }
      .setNegativeButton("Anuluj") { dialog, _ -> dialog.dismiss() }
      .create()
      .apply {
        setOnShowListener { initializeDialog() }
      }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    setupClickListeners()
    return binding.root
  }

  private fun initializeDialog() {
    if (tag == "add_draft") setupDraftProperties()
    canto?.let { bindCantoProperties() }
    setupKindDropdown()
  }

  private fun setupClickListeners() {
    binding.addContentInput.setOnClickListener { showContentInput() }
    binding.searchButton.setOnClickListener { search(binding.inputName.text.toString()) }
  }

  private fun setupKindDropdown() {
    val kindNames = Kind.entries.map { it.text }
    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, kindNames)
    binding.kindText.setAdapter(adapter)

    binding.kindText.setOnItemClickListener { _, _, position, _ ->
      kind = Kind.entries[position]
    }
  }

  private fun showContentInput() {
    binding.addContentInput.visibility = View.GONE
    binding.cantoContent.visibility = View.VISIBLE
    text = ""
  }

  private fun bindCantoProperties() {
    binding.name.editText?.setText(canto?.name.orEmpty())
    binding.number.editText?.setText(canto?.number?.toString().orEmpty())
    binding.kindText.setText(canto?.kind?.text.orEmpty(), false)
    binding.pageCounter
  }

  private fun setupDraftProperties() {
    binding.dialogImage.setImageResource(R.drawable.rule_draft_svgrepo_com)
    binding.name.editText?.setText(draftName.orEmpty())
  }

  private fun handlePositiveClick() {
    name = binding.name.editText?.text.toString().trim()
    number = binding.number.editText?.text.toString().toIntOrNull() ?: 0
    kind = Kind.entries.find { it.text == binding.kindText.text.toString() } ?: Kind.ACCIDENTAL
    text = binding.cantoContentInput.text.toString().trim()
    pageCounter = binding.pageCounter.editText?.text.toString().toIntOrNull() ?: 0

    onAction(number, name, kind, pageCounter, text.orEmpty(), canto)
  }

  private fun search(query: String) {
    val searchUri = Uri.parse("https://www.google.com/search?q=${URLEncoder.encode(query, "UTF-8")}")
    val searchIntent = Intent(Intent.ACTION_VIEW, searchUri).apply {
      addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    try {
      requireActivity().startActivity(searchIntent)
    } catch (e: Exception) {
      e.printStackTrace()
      Toast.makeText(requireContext(), "Brak aplikacji do wyszukiwania", Toast.LENGTH_SHORT).show()
    }
  }
}
