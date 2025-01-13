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
import androidx.lifecycle.lifecycleScope
import com.example.singandsongs.R
import com.example.singandsongs.databinding.DialogAddPlayingBinding
import com.example.singandsongs.model.playing.Day
import com.example.singandsongs.model.playing.Place
import com.example.singandsongs.model.playing.Playing
import com.example.singandsongs.model.playlist.PlayList
import com.example.singandsongs.ui.calendar.PlayingViewModel
import com.example.singandsongs.ui.calendar.calendar.CalendarViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class AddPlayingDialogFragment(
  val day: Day? = null,
  private val action: (Playing) -> CompletableDeferred<Long>
) : DialogFragment() {

  private lateinit var binding: DialogAddPlayingBinding
  private val viewModel: PlayingViewModel by viewModels()
  private val calendarViewModel: CalendarViewModel by viewModels()

  @RequiresApi(Build.VERSION_CODES.O)
  var date: LocalDate = LocalDate.now()
  private var name: String = ""
  private var place: Place? = null
  private var time: String = ""
  private var playList: PlayList? = null

  init {
      day?.let { date = day.date }
  }

  @RequiresApi(Build.VERSION_CODES.O)
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
        setOnShowListener {  }
      }
  }

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    setListsObservers()
    binding.inputDate.setOnClickListener { showDatePickerDialog() }
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

  @RequiresApi(Build.VERSION_CODES.O)
  private fun handlePositiveClick() {
    lifecycleScope.launch {
      val playingId = resolveNewPlaying()
      editDay(playingId)
    }
  }

  @RequiresApi(Build.VERSION_CODES.O)
  private suspend fun resolveNewPlaying(): Long {
    val playListId = playList?.playListId ?: 0
    val placeId = place?.placeId ?: 0
    val newPlaying = Playing(date, name, placeId, time, playListId)
    val id = action.invoke(newPlaying)
    return id.await()
  }

  @RequiresApi(Build.VERSION_CODES.O)
  private fun editDay(playingId: Long) {
    lifecycleScope.launch {
      calendarViewModel.editDay(date, playingId)
    }
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
      date = LocalDate.of(year, month + 1, day)
    }, year, month, day)

    datePickerDialog.show()
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
