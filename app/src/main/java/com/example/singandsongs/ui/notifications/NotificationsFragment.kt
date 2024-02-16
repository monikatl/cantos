package com.example.singandsongs.ui.notifications

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.singandsongs.databinding.FragmentNotificationsBinding

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

        val adapter = PlayListAdapter()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}