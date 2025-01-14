package com.example.singandsongs.ui.notifications

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.singandsongs.R
import com.example.singandsongs.databinding.FragmentNotificationsBinding
import com.example.singandsongs.ui.components.YesNoDialog
import com.example.singandsongs.ui.components.createAndShowSimpleYesNoDialog
import com.example.singandsongs.ui.components.createAndShowYesNoNeutralDialog
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class NotificationsFragment : Fragment() {

  private val viewModel: NotificationsViewModel by viewModels()
  private lateinit var binding: FragmentNotificationsBinding
  private lateinit var adapter: PlayListAdapter

  var list = emptyList<String>()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    binding = FragmentNotificationsBinding.inflate(inflater, container, false)

    createPlayListAdapter()
    setObservers()
    setListeners()
    setAppBarMenuProvider()


    return binding.root
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  private fun createPlayListAdapter() {
    adapter = PlayListAdapter(setCurrentPlayList, resolveCantoContent)
    binding.allPlayLists.adapter = adapter
  }

  private fun setObservers() {
    with(viewModel) {
      playLists.observe(viewLifecycleOwner) {
        adapter.setList(viewModel.playLists.value ?: emptyList())
      }
      playListWithCantos.observe(viewLifecycleOwner) {
        getTextListThenResolveIfShowBottomSheet()
      }
      id.observe(viewLifecycleOwner) {  }
    }
  }

  private fun setListeners() {
    binding.addPlayListButton.setOnClickListener {
      showAddPlayListDialog()
    }

    binding.materialButtonToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
      setSortCondition(isChecked, checkedId)
    }
  }

  private fun setSortCondition(isChecked: Boolean, checkedId: Int) {
    if(isChecked) { viewModel.choseSortCondition(checkedId) }
  }

  private val resolveCantoContent: (Long) -> Unit = {playListId ->
    viewModel.setChosenPlayListId(playListId)
  }

  private fun showAddPlayListDialog() {
    val newFragment = AddPlayListDialogFragment(viewModel.addNewPlayList)
    newFragment.show(activity?.supportFragmentManager!!, "add_playlist")
  }

  private val setCurrentPlayList: (Int) -> Unit = { position ->
    val currentPlayList = viewModel.setCurrentPlayList(position)
    if(currentPlayList != null)
      showInfoDialog(currentPlayList.name)
  }

  private fun getTextListThenResolveIfShowBottomSheet() {
    if(viewModel.checkIfIdIsNotZero) {
      val list = getCantosListWithNumberAndNameTextOrEmpty()
      resolveIfShowBottomSheet(list)
    }
  }

  private fun getCantosListWithNumberAndNameTextOrEmpty() = viewModel.getCantosListWithNumberAndName() ?: emptyList()

  private fun resolveIfShowBottomSheet(list: List<String>) {
    if(list.isEmpty()) showNoCantosToast()
    else showCantosBottomSheet()
  }

  private fun showNoCantosToast() = Toast.makeText(requireContext(),
    getString(R.string.no_cantos_message), Toast.LENGTH_SHORT).show()

  private fun showCantosBottomSheet() {
    val modalBottomSheet = CantosBottomSheet(list, clearPlayListId)
    modalBottomSheet.show(requireActivity().supportFragmentManager, CantosBottomSheet.TAG)
  }

  private val clearPlayListId: () -> Unit = {
    viewModel.setChosenPlayListId(0)
  }

  private fun showInfoDialog(playListName: String)  {
    val dialog = YesNoDialog(requireContext(), playListName, getString(R.string.set_is_pointed)) {
      it?.let { navigateToCurrentPlayListFragment(it) }
    }
    createAndShowYesNoNeutralDialog(dialog, getString(R.string.add_canto)) { it?.let {  navigateToHomeFragment(it) } }
  }

  private fun navigateToHomeFragment(dialog: DialogInterface) {
    findNavController().navigate(R.id.navigation_home)
    dialog.dismiss()
  }

  private fun navigateToCurrentPlayListFragment(dialog: DialogInterface) {
    findNavController().navigate(R.id.currentPlayListFragment)
    dialog.dismiss()
  }

  private fun setAppBarMenuProvider() {

    val menuHost: MenuHost = requireActivity()

    menuHost.addMenuProvider(object : MenuProvider {

      override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.queue_menu, menu)
      }

      override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
          R.id.queue_set -> { resolveOnQueueSetIconClick(); true }
          else -> false
        }
      }
    }, viewLifecycleOwner, Lifecycle.State.RESUMED)
  }

  private fun resolveOnQueueSetIconClick() {
    if(viewModel.isQueueDisabled) showQueueActivateDialog()
    else showQueueList()
  }

  private fun showQueueList() = true

  private fun showQueueActivateDialog(): Boolean {
    val dialog =
      YesNoDialog(
        requireContext(),
        getString(R.string.multiple_set_mode_info),
        getString(R.string.activate_multiple_mode_q)
      ) { viewModel.activateQueueSet() }
    createAndShowSimpleYesNoDialog(dialog)
    return true
  }

}
