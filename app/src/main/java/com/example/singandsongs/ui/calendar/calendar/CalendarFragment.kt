package com.example.singandsongs.ui.calendar.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.singandsongs.databinding.FragmentCalendarBinding
import com.example.singandsongs.model.playing.Day
import com.example.singandsongs.model.playing.FullDay
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalendarFragment : Fragment() {

  private lateinit var binding: FragmentCalendarBinding
  private val viewModel: CalendarViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    binding = FragmentCalendarBinding.inflate(inflater, container, false)
    binding.viewModel = viewModel

    val adapter = DayAdapter(onLongClickAction, onItemClickAction)
    binding.days.adapter = adapter
    val layoutManager = GridLayoutManager(requireContext(), 7)
    binding.days.layoutManager = layoutManager

    viewModel.days.observe(viewLifecycleOwner) {
      val fullDays = it.map { day -> FullDay.convertToFullDay(day, emptyList()) }
      adapter.setList(fullDays)
    }

    return binding.root
  }

  private val onLongClickAction: (Day) -> Unit = { viewModel.deletePlaying(it) }
  private val onItemClickAction: (Long) -> Unit = {}

}
