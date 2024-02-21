package com.example.singandsongs.ui.notifications

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.*
import com.example.singandsongs.utils.SortCondition.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.singandsongs.R
import com.example.singandsongs.databinding.FragmentNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding
    private val viewModel: NotificationsViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        val adapter = PlayListAdapter(setCurrentPlayList)
        binding.allPlayLists.adapter = adapter

        viewModel.playLists.observe(viewLifecycleOwner) {
            adapter.setList(viewModel.playLists.value ?: emptyList())
        }

        binding.addPlayListButton.setOnClickListener { showAddPlayListDialog() }

        binding.materialButtonToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if(isChecked) {
                val order =  when(checkedId) {
                    R.id.a_z -> AZ
                    R.id.z_a -> ZA
                    R.id.freqAsc -> FREQ_ASC
                    R.id.freqDesc -> FREQ_DESC
                    R.id.dateAsc -> DATE_ASC
                    R.id.dateDesc -> DATE_DESC
                    else -> BY_ID
                }
                viewModel.choseSortCondition(order)
            }
        }
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

    private fun showInfoDialog(playListName: String) = AlertDialog.Builder(requireContext())
        .setTitle(playListName)
        .setMessage("zbiór został ustawiony jako domyślny. \nMożesz dodawać do niego pieśni.")
        .setPositiveButton("OK") { dialog, _ -> dialog.dismiss()}
        .setNegativeButton("do zbioru") { dialog, _ -> navigateToCurrentPlayListFragment(dialog) }
        .setNeutralButton("dodaj pieśni") { dialog, _ -> navigateToHomeFragment(dialog) }
        .create()
        .show()

    private fun navigateToHomeFragment(dialog: DialogInterface) {
        findNavController().navigate(R.id.navigation_home)
        dialog.dismiss()
    }

    private fun navigateToCurrentPlayListFragment(dialog: DialogInterface) {
        findNavController().navigate(R.id.currentPlayListFragment)
        dialog.dismiss()
    }
}