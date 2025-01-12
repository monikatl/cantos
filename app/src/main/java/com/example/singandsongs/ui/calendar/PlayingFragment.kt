package com.example.singandsongs.ui.calendar

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
import androidx.viewpager2.widget.ViewPager2
import com.example.singandsongs.R
import com.example.singandsongs.databinding.FragmentPlayingBinding
import com.example.singandsongs.model.playing.Playing
import com.example.singandsongs.ui.calendar.dialogs.AddPlayingDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayingFragment : Fragment() {

  private lateinit var binding: FragmentPlayingBinding
  private val viewModel: PlayingViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    binding = FragmentPlayingBinding.inflate(inflater, container, false)
    binding.viewModel = viewModel

    binding.addPlaying.setOnClickListener { showAddPlayingDialog() }

    return binding.root
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val viewPager: ViewPager2 = view.findViewById(R.id.viewPager)
    val adapter = PageAdapter(this)
    viewPager.adapter = adapter
  }

  private fun showAddPlayingDialog() {
    val newFragment = AddPlayingDialogFragment { addNewPlaying(it) }
    newFragment.show(activity?.supportFragmentManager!!,  tag)
  }
  private fun addNewPlaying(playing: Playing) {
    viewModel.addPlaying(playing)
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.calendar_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.add_place -> {
        navigateToPlacesFragment()
        true
      }

      else -> { false }
    }
  }

  private fun navigateToPlacesFragment() {
    findNavController().navigate(R.id.action_navigation_playing_to_placesFragment)
  }


}
