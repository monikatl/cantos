package com.example.singandsongs.ui.dashboard

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.singandsongs.R
import com.example.singandsongs.databinding.FragmentDashboardBinding
import com.example.singandsongs.model.Kind
import com.example.singandsongs.ui.home.CantoAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private lateinit var  binding: FragmentDashboardBinding
    private val  dashboardViewModel: DashboardViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        val categories = binding.categoryRecyclerView
        categories.layoutManager = LinearLayoutManager(requireContext())
        val adapter = CategoryAdapter(navigateToCategoryFragment)
        categories.adapter = adapter

        dashboardViewModel.categories.observe(viewLifecycleOwner) {
            adapter.setList(dashboardViewModel.categories.value ?: emptyList())
        }

        return  binding.root
    }

    private val navigateToCategoryFragment: (Int) -> Unit = {
        val category = Kind.values()[it]
        val bundle = bundleOf("category" to category.name)
        findNavController().navigate(R.id.action_navigation_dashboard_to_categoryFragment, bundle)
    }
}