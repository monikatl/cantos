package com.example.singandsongs.ui.calendar.calendar

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.singandsongs.R
import com.example.singandsongs.databinding.FragmentCalendarBinding
import com.example.singandsongs.model.playing.Day
import com.example.singandsongs.model.playing.FullDay
import com.example.singandsongs.model.playing.Playing
import com.example.singandsongs.ui.calendar.PlayingViewModel
import com.example.singandsongs.ui.calendar.dialogs.AddPlayingDialogFragment
import com.example.singandsongs.ui.calendar.list.PlayingListFragment
import com.example.singandsongs.ui.calendar.list.PlayingListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CalendarFragment : Fragment() {

  private lateinit var binding: FragmentCalendarBinding
  private val viewModel: CalendarViewModel by viewModels()
  private val playingViewModel: PlayingViewModel by viewModels()
  private val playingListViewModel: PlayingListViewModel by activityViewModels()

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

    viewModel.fullDays.observe(viewLifecycleOwner) {
      adapter.setList(it)
    }

    viewModel.selectedDay.observe(viewLifecycleOwner) {
      playingListViewModel.updatePlayingList(it?.playings)
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

  @RequiresApi(Build.VERSION_CODES.O)
  private val onLongClickAction: (Day?) -> Unit = { showAddPlayingDialog(it) }
  private val onItemClickAction: (FullDay) -> Unit = { viewModel.selectDay(it) }

  @RequiresApi(Build.VERSION_CODES.O)
  private fun showAddPlayingDialog(day: Day?) {
    val newFragment = AddPlayingDialogFragment(day) { addNewPlaying(it) }
    newFragment.show(activity?.supportFragmentManager!!,  tag)
  }

  private fun addNewPlaying(playing: Playing): CompletableDeferred<Long> {
    val deferred = CompletableDeferred<Long>()
    lifecycleScope.launch {
      val result = playingViewModel.addPlaying(playing)
      deferred.complete(result)
    }
    return deferred
  }

}
