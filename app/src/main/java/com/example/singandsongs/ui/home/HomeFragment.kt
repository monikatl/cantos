package com.example.singandsongs.ui.home

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.singandsongs.R
import com.example.singandsongs.data.DatabaseInit
import com.example.singandsongs.databinding.FragmentHomeBinding
import com.example.singandsongs.model.Canto
import com.example.singandsongs.ui.notifications.AddPlayListDialogFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.FieldPosition
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val homeViewModel: HomeViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.viewModel = homeViewModel
        binding.lifecycleOwner = this

        val adapter = CantoAdapter(requireContext(), deleteCanto, editCanto, homeViewModel.addCantoToCurrentPlayList, homeViewModel.checkFav)
        binding.allCantos.adapter = adapter

        homeViewModel.cantos.observe(viewLifecycleOwner) {
            adapter.setList(homeViewModel.cantos.value ?: emptyList())
        }

        homeViewModel.drafts.observe(viewLifecycleOwner) {
            adapter.setList(homeViewModel.drafts.value ?: emptyList())
        }

        binding.tabLayout.addOnTabSelectedListener (object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let {
                        val cantos = homeViewModel.filterCantos(it)
                        adapter.setList(cantos ?: emptyList())
                    }
                }
                override fun onTabReselected(tab: TabLayout.Tab?) {
                    // Handle tab reselect
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    // Handle tab unselect
                }
        })

        homeViewModel.playListWithCantos.observe(viewLifecycleOwner) {}

        binding.search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener,
           SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(msg: String): Boolean {
                filter(msg, adapter)
                return false
            }
        })

        binding.addCantoButton.setOnClickListener { showAddCantoDialog() }

        return binding.root
    }

    private val deleteCanto: (Int) -> Unit = { showDeleteCantoConfirmationDialog(it) }
    private fun showDeleteCantoConfirmationDialog(position: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Czy chcesz usunąć pozycję?")
            .setPositiveButton("OK") { dialog, _ -> homeViewModel.deleteCanto(position)}
            .setNegativeButton("Przejdź do zbioru") {dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private val editCanto: (Canto) -> Unit = { canto -> showEditCantoDialog(canto) }

    private fun filter(text: String, adapter: CantoAdapter) {
        val filteredList: MutableList<Canto> = mutableListOf()

        for (item in homeViewModel.cantos.value!!) {
            if (item.name.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }
        if (filteredList.isEmpty()) {
            enableDraftAdd(text)
            adapter.setList(filteredList)
        }
        else {
            adapter.filterList(filteredList)
            setDraftButtonVisibility(false)
        }

    }

    private fun enableDraftAdd(text: String) {
        setDraftButtonVisibility(true)
        binding.addDraftButton.setOnClickListener {  showAddDraftDialog(text) }
    }

    private fun setDraftButtonVisibility(isVisible: Boolean) {
        binding.addDraftButton.visibility = if(isVisible)  View.VISIBLE else View.GONE
    }

    private fun showAddDraftDialog(name: String) {
        val newFragment = AddCantoDialogFragment(homeViewModel.addDraft, draftName = name)
        newFragment.show(activity?.supportFragmentManager!!, "add_draft")
    }

    private fun showAddCantoDialog() {
        val newFragment = AddCantoDialogFragment(homeViewModel.addCanto)
        newFragment.show(activity?.supportFragmentManager!!, "add_canto")
    }

    private fun showEditCantoDialog(canto: Canto? = null) {
        val newFragment = AddCantoDialogFragment(homeViewModel.editCanto, canto)
        newFragment.show(activity?.supportFragmentManager!!, "edit_canto")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}