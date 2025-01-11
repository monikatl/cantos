package com.example.singandsongs.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.singandsongs.databinding.FragmentCalendarBinding
import com.example.singandsongs.model.playing.Playing
import com.example.singandsongs.ui.calendar.dialogs.AddPlayingDialogFragment
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

    binding.addPlaying.setOnClickListener { showAddPlayingDialog() }


    return binding.root
  }

  private fun showAddPlayingDialog() {
    val newFragment = AddPlayingDialogFragment { addNewPlaying(it) }
    newFragment.show(activity?.supportFragmentManager!!,  tag)
  }

  private fun addNewPlaying(playing: Playing) {}
}
