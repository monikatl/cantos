package com.example.singandsongs.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.singandsongs.R
import com.example.singandsongs.databinding.CantoListItemBinding
import com.example.singandsongs.model.Canto


class CantoAdapter(private val context: Context, private val deleteAction: (Int) -> Unit, private val editAction: (Canto) -> Unit):RecyclerView.Adapter<CantoAdapter.IntentHolder>() {

    var datalist:List<Canto> = emptyList()

    fun setList(datalist:List<Canto>) {
        this.datalist = datalist
        notifyDataSetChanged()
    }

    fun filterList(filterList: List<Canto>) {
        datalist = filterList
        notifyDataSetChanged()
    }

    class IntentHolder(val binding: CantoListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(canto: Canto) {
            binding.canto = canto
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntentHolder {
        val binding = CantoListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return IntentHolder(binding)
    }

    override fun onBindViewHolder(holder: IntentHolder, position: Int) {
        holder.bind(datalist[position])

        holder.itemView.setOnLongClickListener { view ->
            val popupMenu = PopupMenu(context, view)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_edit -> {
                        editAction.invoke(datalist[position])
                        true
                    }
                    R.id.action_delete -> {
                        deleteAction.invoke(position)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
            true
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}