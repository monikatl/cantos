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

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return super.onCreateView(inflater, container, savedInstanceState)
    //binding.addPlaying.setOnClickListener { showAddPlayingDialog() }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val viewPager: ViewPager2 = view.findViewById(R.id.viewPager)
    val adapter = PageAdapter(this)
    viewPager.adapter = adapter
  }


}
