package com.example.singandsongs.ui.calendar.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.singandsongs.R
import com.example.singandsongs.databinding.DialogAddPlaceBinding

class AddPlaceDialogFragment(
  private val addPlace: (String, String, List<String>) -> Unit
) : DialogFragment() {

  private lateinit var binding: DialogAddPlaceBinding
  private val hourList = mutableListOf("18:00", "17:00")
  private lateinit var adapter: ArrayAdapter<String>

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    binding = DataBindingUtil.inflate(
      LayoutInflater.from(requireContext()),
      R.layout.dialog_add_place,
      null,
      false
    )

    resolveHourListAdapter()

    binding.addHourButton.setOnClickListener { addHour() }
    binding.mapButton.setOnClickListener { showMapDialog() }

    return AlertDialog.Builder(requireContext())
      .setView(binding.root)
      .setPositiveButton("Dodaj") { _, _ -> handlePositiveClick() }
      .setNegativeButton("Anuluj") { dialog, _ -> dialog.dismiss() }
      .create()
      .apply {
        setOnShowListener { initializeDialog() }
      }
  }

  private fun resolveHourListAdapter() {
    adapter = ArrayAdapter(
      requireContext(),
      android.R.layout.simple_list_item_1,
      hourList
    )
    binding.hourList.adapter = adapter
  }
  private fun handlePositiveClick() {
    val name = binding.inputName.text.toString()
    val address = binding.inputAddress.text.toString()
    addPlace.invoke(name, address, hourList)
  }
  private fun initializeDialog() {}

  private fun addHour() {
    hourList.add("18:00")
    adapter.notifyDataSetChanged()
  }

  private fun showMapDialog() {
    val mapDialog = MapDialogFragment()
    mapDialog.show(activity?.supportFragmentManager!!, "MapDialog")
  }
}
