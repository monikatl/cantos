package com.example.singandsongs.ui.current_playlist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


        return binding.root
    }

}