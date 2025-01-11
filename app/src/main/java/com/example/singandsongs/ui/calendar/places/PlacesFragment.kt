package com.example.singandsongs.ui.calendar.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.singandsongs.databinding.FragmentPlacesBinding
import com.example.singandsongs.model.playing.Place
import com.example.singandsongs.ui.calendar.dialogs.AddPlaceDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlacesFragment : Fragment() {

  private val viewModel: PlacesViewModel by viewModels()
  private lateinit var binding: FragmentPlacesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

      binding = FragmentPlacesBinding.inflate(inflater, container, false)

      val adapter = PlaceAdapter(onLongClickAction, onItemClickAction)
      binding.places.adapter = adapter

      binding.addPlaceButton.setOnClickListener { showAddPlaceDialog() }

      viewModel.places.observe(viewLifecycleOwner) {
        adapter.setList(viewModel.places.value ?: emptyList())
      }

      return binding.root
    }

  private fun showAddPlaceDialog(): Boolean {
    val newFragment = AddPlaceDialogFragment(viewModel.addPlace)
    newFragment.show(activity?.supportFragmentManager!!,  tag)
    return true
  }

  private val onLongClickAction: (Place) -> Unit = { viewModel.deletePlace.invoke(it) }
  private val onItemClickAction: (Long) -> Unit = {}
}
