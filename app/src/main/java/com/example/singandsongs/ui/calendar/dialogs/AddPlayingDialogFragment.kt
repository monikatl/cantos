package com.example.singandsongs.ui.calendar.dialogs

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.singandsongs.R
import com.example.singandsongs.databinding.DialogAddPlayingBinding
import com.example.singandsongs.model.playing.Event
import com.example.singandsongs.model.playing.Place
import com.example.singandsongs.model.playing.Playing
import com.example.singandsongs.model.playlist.PlayList
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

class AddPlayingDialogFragment(
  private val action: (Playing) -> Unit
) : DialogFragment() {

  private lateinit var binding: DialogAddPlayingBinding

  @RequiresApi(Build.VERSION_CODES.O)
  var date: LocalDate = LocalDate.now()
  private var place: Place? = null
  private var time: Event? = null
  private var playList: PlayList? = null

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    binding = DataBindingUtil.inflate(
      LayoutInflater.from(requireContext()),
      R.layout.dialog_add_playing,
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

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    setupClickListeners()
    return binding.root
  }

  private fun setupClickListeners() {

  }

  private fun handlePositiveClick() {
//    val newPlaying = Playing()
//    action.invoke(newPlaying)
  }

  private fun resolveDialogFieldsValues() {

  }

  private fun initializeDialog() {

  }

  @RequiresApi(Build.VERSION_CODES.O)
  private fun showDatePickerDialog() {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, day ->

      val selectedDate = Calendar.getInstance()
      selectedDate.set(year, month, day)

      val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate.time)
      binding.inputDate.setText(formattedDate)
      date = LocalDate.of(year, month, day)
    }, year, month, day)

    datePickerDialog.show()
  }

}
