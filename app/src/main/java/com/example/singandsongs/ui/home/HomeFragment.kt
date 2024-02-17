package com.example.singandsongs.ui.home

import android.app.AlertDialog
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
import com.example.singandsongs.data.DatabaseInit
import com.example.singandsongs.databinding.FragmentHomeBinding
import com.example.singandsongs.model.Canto
import com.example.singandsongs.ui.notifications.AddPlayListDialogFragment
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

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val adapter = CantoAdapter(requireContext(), deleteCanto, editCanto)
        binding.allCantos.adapter = adapter

        homeViewModel.cantos.observe(viewLifecycleOwner) {
            adapter.setList(homeViewModel.cantos.value ?: emptyList())
        }


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

    private val editCanto: (Canto) -> Unit = { showAddCantoDialog(it) }

    private fun filter(text: String, adapter: CantoAdapter) {
        val filteredList: MutableList<Canto> = mutableListOf()

        for (item in homeViewModel.cantos.value!!) {
            if (item.name.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }
        if (filteredList.isEmpty())
            Toast.makeText(requireContext(), "Nie znaleziono pieśni!", Toast.LENGTH_SHORT).show()
        else
            adapter.filterList(filteredList)
    }

    private fun showAddCantoDialog(canto: Canto? = null) {
        val newFragment = AddCantoDialogFragment(homeViewModel.addCanto, canto)
        newFragment.show(activity?.supportFragmentManager!!, "add_canto")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}