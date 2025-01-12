package com.example.singandsongs.ui.calendar.dialogs

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.singandsongs.R
import com.example.singandsongs.databinding.DialogAddPlayingBinding
import com.example.singandsongs.model.playing.Event
import com.example.singandsongs.model.playing.Place
import com.example.singandsongs.model.playing.Playing
import com.example.singandsongs.model.playlist.Kind
import com.example.singandsongs.model.playlist.PlayList
import com.example.singandsongs.ui.calendar.CalendarViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AddPlayingDialogFragment(
  private val action: (Playing) -> Unit
) : DialogFragment() {

  private lateinit var binding: DialogAddPlayingBinding
  private val viewModel: CalendarViewModel by viewModels()

  @RequiresApi(Build.VERSION_CODES.O)
  var date: LocalDate = LocalDate.now()
  private var place: Place? = null
  private var time: String? = null
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
    setListsObservers()
    return binding.root
  }

  private fun setListsObservers() {
    viewModel.playLists.observe(viewLifecycleOwner) {
      setupPlayListDropdown()
    }
    viewModel.places.observe(viewLifecycleOwner) {
      setupPlaceDropdown()
    }
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

  private fun setupDropdowns() {
    setupPlaceDropdown()
    setupPlayListDropdown()
  }

  private fun setupPlaceDropdown() {
    val places = viewModel.places.value ?: emptyList()
    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, places.map { it.name })
    binding.placeText.setAdapter(adapter)

    binding.placeText.setOnItemClickListener { _, _, position, _ ->
      place = places[position]
      setupHoursDropdown(place!!.hours)
    }
  }

  private fun setupPlayListDropdown() {
    val playLists = viewModel.playLists.value ?: emptyList()
    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, playLists.map { it.name })
    binding.playListText.setAdapter(adapter)

    binding.playListText.setOnItemClickListener { _, _, position, _ ->
      playList = playLists[position]
    }
  }

  private fun setupHoursDropdown(hours: List<String>) {
    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, hours)
    binding.timeText.setAdapter(adapter)

    binding.timeText.setOnItemClickListener { _, _, position, _ ->
      time = hours[position]
    }
  }

}
