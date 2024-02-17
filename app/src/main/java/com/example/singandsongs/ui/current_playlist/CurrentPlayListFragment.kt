package com.example.singandsongs.ui.current_playlist

import android.app.AlertDialog
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.example.singandsongs.R
import com.example.singandsongs.databinding.FragmentCurrentPlayListBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CurrentPlayListFragment : Fragment() {

    private val viewModel: CurrentPlayListViewModel by viewModels()
    private lateinit var binding: FragmentCurrentPlayListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCurrentPlayListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.deletePlayList.setOnClickListener { showDeleteConfirmDialog() }
        binding.attachPlayList.setOnClickListener { showPlayListsDialog() }

        return binding.root
    }

    private fun showDeleteConfirmDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Czy chcesz usunąć zbiór: ${viewModel.playList.value?.name}?")
            .setPositiveButton("TAK") { dialog, _ -> viewModel.deletePlayList(dialog)}
            .setNegativeButton("NIE") {dialog, _ ->  dialog.dismiss()}
            .create()
            .show()
    }

    private fun showPlayListsDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Wybierz zbiór")
            .setItems(arrayOf("a,b,c", "a,b,c","a,b,c")) { dialog, which ->
                // Do something on item tapped.
            }
            .create()
            .show()
    }

}