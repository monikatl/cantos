package com.example.singandsongs.ui.calendar.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.singandsongs.R
import com.example.singandsongs.databinding.FragmentCalendarBinding
import com.example.singandsongs.databinding.FragmentPlayingBinding
import com.example.singandsongs.model.playing.Playing
import com.example.singandsongs.ui.calendar.PlayingViewModel
import com.example.singandsongs.ui.calendar.dialogs.AddPlayingDialogFragment

class CalendarFragment : Fragment() {

  private lateinit var binding: FragmentCalendarBinding
  private val viewModel: CalendarViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    binding = FragmentCalendarBinding.inflate(inflater, container, false)
    binding.viewModel = viewModel

    return binding.root
  }

}
