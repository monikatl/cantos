package com.example.singandsongs.ui.home

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.singandsongs.R
import com.example.singandsongs.databinding.CantoListItemBinding
import com.example.singandsongs.model.Canto
import com.example.singandsongs.model.CantoPlayListCrossRef
import java.net.URLEncoder
import java.util.*


class CantoAdapter(private val context: Context,
                   private val deleteAction: ((Int) -> Unit)? = null,
                   private val editAction: ((Canto) -> Unit)? = null,
                   private val onClickItem: ((Long) -> Unit)? = null
):RecyclerView.Adapter<CantoAdapter.IntentHolder>() {

    var datalist:List<Canto> = emptyList()

    fun setList(datalist:List<Canto>) {
        this.datalist = datalist
        notifyDataSetChanged()
    }

    fun filterList(filterList: List<Canto>) {
        datalist = filterList
        notifyDataSetChanged()
    }

    fun onItemMove(fromPosition: Int?, toPosition: Int?): Boolean {
        fromPosition?.let {
            toPosition?.let {
                if (fromPosition < toPosition) {
                    for (i in fromPosition until toPosition) {
                        Collections.swap(datalist, i, i + 1)
                    }
                } else {
                    for (i in fromPosition downTo toPosition + 1) {
                        Collections.swap(datalist, i, i - 1)
                    }
                }
                notifyItemMoved(fromPosition, toPosition)
                return true
            }
        }
        return false
    }

    class IntentHolder(val binding: CantoListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(canto: Canto) {
            binding.canto = canto
            binding.color = Color.parseColor(canto.kind.color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntentHolder {
        val binding = CantoListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return IntentHolder(binding)
    }

    override fun onBindViewHolder(holder: IntentHolder, position: Int) {
        val canto = datalist[position]
        holder.bind(canto)

        holder.itemView.setOnClickListener { onClickItem?.invoke(canto.cantoId) }

        if(deleteAction != null) {
            holder.itemView.setOnLongClickListener { view ->
                val popupMenu = PopupMenu(context, view)
                popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.action_search -> {
                            searchOnGoogle(canto.name)
                            true
                        }
                        R.id.action_edit -> {
                            editAction?.invoke(canto)
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
    }

    private fun searchOnGoogle(query: String) {
        val searchUri = Uri.parse("https://www.google.com/search?q=${URLEncoder.encode(query, "UTF-8")}")
        val searchIntent = Intent(Intent.ACTION_VIEW, searchUri)
        searchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(searchIntent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}