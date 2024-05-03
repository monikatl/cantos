package com.example.singandsongs.ui.current_playlist

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.example.singandsongs.R
import com.example.singandsongs.databinding.FragmentCurrentPlayListBinding
import com.example.singandsongs.model.CantoAndContent
import com.example.singandsongs.ui.home.CantoAdapter
import com.example.singandsongs.utils.searchOnGoogle
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
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

        val adapter = CantoAdapter(requireContext(), onPlayListClickItem = resolveCantoContent)
        binding.cantosRecyclerView.adapter = adapter


        with(viewModel){
            playListWithCantos.observe(viewLifecycleOwner) { adapter.setList(viewModel.playListWithCantos.value?.cantos ?: emptyList()) }
            cantoContent.observe(viewLifecycleOwner) { it?.let {  resolveCantoContentAction(it) } }
            cantosAndContents.observe(viewLifecycleOwner) {}
            id.observe(viewLifecycleOwner) {}
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
        binding.sharePlayList.setOnClickListener { sharePlayList() }

        binding.attachPlayList.setOnClickListener { showPlayListsDialog() }

        return binding.root
    }

    private fun sharePlayList() {
        val textList = viewModel.playListWithCantos.value?.convertToSend()
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, textList)
            type = "text/plain"
        }
        try {
            startActivity(sendIntent)
        } catch (e: ActivityNotFoundException) {
            // Define what your app should do if no activity can handle the intent.
        }
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

    private val resolveCantoContent: (Long) -> Unit = {cantoId ->
        viewModel.setCurrentCanto(cantoId)
    }

    private fun resolveCantoContentAction(cantoAndContent: CantoAndContent) {
        if(cantoAndContent.content?.text.isNullOrBlank())
            showNoContentSnackbar(cantoAndContent)
        else
            showCantoContentDialog(cantoAndContent)
    }

    private fun showNoContentSnackbar(cantoAndContent: CantoAndContent) {
        Snackbar.make(binding.root, "Brak tekstu pieśni", Snackbar.LENGTH_LONG)
            .setAction("Dodaj treść") { showCantoContentAddDialog(cantoAndContent) }
            .show()
    }

    private fun showCantoContentAddDialog(cantoAndContent: CantoAndContent) {
        val name = cantoAndContent.canto.name
        AlertDialog.Builder(requireContext())
            .setTitle(name)
            .setView(R.layout.dialog_add_canto_content)
            .setPositiveButton("DODAJ") {dialog, _ ->
                val text = view?.findViewById<TextInputEditText>(R.id.cantoContentInput)
                cantoAndContent.content?.text = text?.text.toString()
                viewModel.addContentToCanto(cantoAndContent)
                dialog.dismiss()
            }
            .setNeutralButton ("WYSZUKAJ") { _, _ ->
                searchOnGoogle(requireContext(), name)
            }
            .create()
            .show()
    }

    private fun showCantoContentDialog(cantoAndContent: CantoAndContent) {
        AlertDialog.Builder(requireContext())
            .setTitle(cantoAndContent.canto.name)
            .setMessage(cantoAndContent.content?.text)
            .setPositiveButton("OK") {dialog, _ ->
                dialog.dismiss()
                viewModel.setCurrentCanto(0)
            }
            .create()
            .show()
    }
}
