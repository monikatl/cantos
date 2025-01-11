package com.example.singandsongs.ui.calendar.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.singandsongs.R
import com.example.singandsongs.databinding.DialogAddPlaceBinding

class AddPlaceDialogFragment : DialogFragment() {

  private lateinit var binding: DialogAddPlaceBinding

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    binding = DataBindingUtil.inflate(
      LayoutInflater.from(requireContext()),
      R.layout.dialog_add_place,
      null,
      false
    )

    return AlertDialog.Builder(requireContext())
      .setView(binding.root)
      .setPositiveButton("Dodaj") { _, _ -> handlePositiveClick() }
      .setNegativeButton("Anuluj") { dialog, _ -> dialog.dismiss() }
      .create()
      .apply {
        setOnShowListener { initializeDialog() }
      }
  }

  private fun handlePositiveClick() {}
  private fun initializeDialog() {}
}
