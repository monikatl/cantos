package com.example.singandsongs.ui.current_playlist

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.example.singandsongs.databinding.FragmentCurrentPlayListBinding
import com.example.singandsongs.ui.home.CantoAdapter
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

        val adapter = CantoAdapter(requireContext())
        binding.cantosRecyclerView.adapter = adapter

        viewModel.playListWithCantos.observe(viewLifecycleOwner) {
            adapter.setList(viewModel.playListWithCantos.value?.cantos ?: emptyList())
        }

        val touchHelper = ItemTouchHelper(object : SimpleCallback(UP + DOWN , RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition);
                return true
            }

            override fun isLongPressDragEnabled(): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteCanto(adapter.datalist[position])
                adapter.notifyItemRemoved(position)
            }
        })

        touchHelper.attachToRecyclerView(binding.cantosRecyclerView)

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