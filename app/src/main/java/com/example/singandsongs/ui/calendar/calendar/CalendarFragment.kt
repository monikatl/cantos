package com.example.singandsongs.ui.calendar.calendar

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.singandsongs.R
import com.example.singandsongs.databinding.FragmentCalendarBinding
import com.example.singandsongs.model.playing.Day
import com.example.singandsongs.model.playing.FullDay
import com.example.singandsongs.model.playing.Playing
import com.example.singandsongs.ui.calendar.dialogs.AddPlayingDialogFragment
import com.example.singandsongs.ui.calendar.list.PlayingListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalendarFragment : Fragment() {

  private lateinit var binding: FragmentCalendarBinding
  private val viewModel: CalendarViewModel by viewModels()

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    binding = FragmentCalendarBinding.inflate(inflater, container, false)
    binding.viewModel = viewModel

    setupDayAdapter()
    setupPlayingListFragment()
    setupSelectedDayObserver()

    return binding.root
  }

  @RequiresApi(Build.VERSION_CODES.O)
  private fun setupDayAdapter() {
    val adapter = DayAdapter(onLongClickAction, onItemClickAction)
    binding.days.adapter = adapter
    val layoutManager = GridLayoutManager(requireContext(), 7)
    binding.days.layoutManager = layoutManager

    viewModel.days.observe(viewLifecycleOwner) {
      val fullDays = it.map { day -> FullDay.convertToFullDay(day, emptyList()) }
      adapter.setList(fullDays)
    }
  }

  private fun setupPlayingListFragment() {

    val playingListFragment = PlayingListFragment()

    childFragmentManager.beginTransaction()
      .replace(R.id.playing_list_fragment_container, playingListFragment)
      .commit()
  }

  private fun setupSelectedDayObserver() {
    viewModel.selectedDay.observe(viewLifecycleOwner) {
      val day = viewModel.selectedDay.value
      val label = day?.let {
          StringBuilder()
            .append(it.number)
            .append(" ")
            .append(it.month)
            .append(" ")
            .append(it.year)
            .toString()
      } ?: "NADCHODZĄCE GRANIA"


      binding.sectionText = label
    }
  }

  private val onLongClickAction: (Day?) -> Unit = { showAddPlayingDialog() }
  private val onItemClickAction: (FullDay) -> Unit = { viewModel.selectDay(it) }

  private fun showAddPlayingDialog() {
    val newFragment = AddPlayingDialogFragment { addNewPlaying(it) }
    newFragment.show(activity?.supportFragmentManager!!,  tag)
  }

  private fun addNewPlaying(it: Playing) {

  }

}
