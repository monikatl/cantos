package com.example.singandsongs.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.singandsongs.databinding.FragmentCategoryBinding
import com.example.singandsongs.model.playlist.Kind
import com.example.singandsongs.ui.home.CantoAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    companion object {
        fun newInstance() = CategoryFragment()
    }

    private val viewModel: CategoryViewModel by viewModels()
    private lateinit var binding: FragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val categoryString = arguments?.getString("category")
        val categoryEnum = categoryString?.let { Kind.valueOf(it) }
        categoryEnum?.let { viewModel.initList(it) }

        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        val adapter = CantoAdapter(requireContext())
        binding.cantos.adapter = adapter

        viewModel.cantos.observe(viewLifecycleOwner) {
            adapter.setList(viewModel.cantos.value ?: emptyList())
        }

        return binding.root
    }
}
