package com.example.singandsongs.ui.calendar.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.singandsongs.databinding.FragmentPlayingListBinding
import com.example.singandsongs.model.playing.Playing
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayingListFragment : Fragment() {

  private lateinit var binding: FragmentPlayingListBinding

    companion object {
        fun newInstance() = PlayingListFragment()
    }

    private val viewModel: PlayingListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayingListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        val adapter = PlayingAdapter(onLongClickAction, onItemClickAction)
        binding.playings.adapter = adapter

        viewModel.playingList.observe(viewLifecycleOwner) {
          adapter.setList(viewModel.playingList.value ?: emptyList())
        }

        return binding.root
    }

  private val onLongClickAction: (Playing) -> Unit = { viewModel.deletePlaying(it) }
  private val onItemClickAction: (Long) -> Unit = {}
}
