package com.example.singandsongs.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.singandsongs.R
import com.example.singandsongs.databinding.FragmentHomeBinding
import com.example.singandsongs.model.Canto
import com.example.singandsongs.utils.FilterCondition
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewModel = homeViewModel
        binding.lifecycleOwner = this

        val adapter = CantoAdapter(
            requireContext(),
            deleteAction = deleteCanto,
            editAction = editCanto,
            onClickItemCanto = homeViewModel.addCantoToCurrentPlayList,
            onClickItemDraft = addDraftToCantos,
            checkFav = homeViewModel.checkFav
        )

        binding.allCantos.adapter = adapter
        setObservers(adapter)
        resolveTabLayout()

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

    private fun setObservers(adapter: CantoAdapter) {
        homeViewModel.cantos.observe(viewLifecycleOwner) { adapter.setList(homeViewModel.cantos.value ?: emptyList()) }
        homeViewModel.drafts.observe(viewLifecycleOwner) { resolveDraftBadge() }
        homeViewModel.playListWithCantos.observe(viewLifecycleOwner) {}
    }

    private fun resolveTabLayout() {
        binding.tabLayout.addOnTabSelectedListener (object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    val condition = when(it.position) {
                        0 -> FilterCondition.CANTOS
                        1 -> FilterCondition.FAVOURITE
                        2 -> FilterCondition.DRAFTS
                        else -> FilterCondition.CANTOS
                    }
                    homeViewModel.checkFilterCondition(condition)
                }
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })
    }

    private fun resolveDraftBadge() {
        val badge = binding.tabLayout.getTabAt(2)?.orCreateBadge
        badge?.number = homeViewModel.drafts.value?.size ?: 0
    }

    private val deleteCanto: (Int) -> Unit = { showDeleteCantoConfirmationDialog(it) }
    private fun showDeleteCantoConfirmationDialog(position: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Czy chcesz usunąć pozycję?")
            .setPositiveButton("OK") { dialog, _ -> homeViewModel.deleteCanto(position)}
            .setNegativeButton("Anuluj") {dialog, _ -> dialog.dismiss() }
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
        val newFragment = AddCantoDialogFragment(action = homeViewModel.addDraft, draftName = name)
        newFragment.show(activity?.supportFragmentManager!!, "add_draft")
    }

    private fun showAddCantoDialog() {
        val newFragment = AddCantoDialogFragment(homeViewModel.addCanto)
        newFragment.show(activity?.supportFragmentManager!!, "add_canto")
    }

    private fun showEditCantoDialog(canto: Canto? = null) {
        val newFragment = AddCantoDialogFragment(homeViewModel.editCanto, canto = canto)
        newFragment.show(activity?.supportFragmentManager!!, "edit_canto")
    }

    private val addDraftToCantos: (Canto) -> Unit = { canto ->
        homeViewModel.transformDraft(canto)
        Snackbar.make(binding.root, R.string.snackbar_draft_label, Snackbar.LENGTH_LONG)
            .setAction("Cofnij") { homeViewModel.undoAddDraftToCantos(canto) }.show()
    }
}