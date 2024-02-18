package com.example.singandsongs.ui.notifications

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.singandsongs.R
import com.example.singandsongs.databinding.FragmentNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: NotificationsViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        val adapter = PlayListAdapter(setCurrentPlayList)
        binding.allPlayLists.adapter = adapter

        viewModel.playLists.observe(viewLifecycleOwner) { adapter.setList(viewModel.playLists.value ?: emptyList()) }
        binding.addPlayListButton.setOnClickListener { showAddPlayListDialog() }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showAddPlayListDialog() {
        val newFragment = AddPlayListDialogFragment(viewModel.addNewPlayList)
        newFragment.show(activity?.supportFragmentManager!!, "add_playlist")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val setCurrentPlayList: (Int) -> Unit = { position ->
        val currentPlayList = viewModel.setCurrentPlayList(position)
        if(currentPlayList != null)
            showInfoDialog(currentPlayList.name)
    }

    private fun showInfoDialog(playListName: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Zbiór: $playListName")
            .setMessage("został ustawiony jako domyślny. \nMożesz teraz dodawać do niego pieśni.")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss()}
            .setNegativeButton("Przejdź do zbioru") {dialog, _ -> navigateToCurrentPlayListFragment(dialog) }
            .setNeutralButton("Dodaj pieśni") {dialog, _ -> navigateToHomeFragment(dialog)}
            .create()
            .show()
    }

    private fun navigateToHomeFragment(dialog: DialogInterface) {
        findNavController().navigate(R.id.navigation_home)
        dialog.dismiss()
    }

    private fun navigateToCurrentPlayListFragment(dialog: DialogInterface) {
        findNavController().navigate(R.id.currentPlayListFragment)
        dialog.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}