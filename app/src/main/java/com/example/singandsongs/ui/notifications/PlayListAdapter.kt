package com.example.singandsongs.ui.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.singandsongs.databinding.PlayListItemBinding
import com.example.singandsongs.model.PlayList


class PlayListAdapter():RecyclerView.Adapter<PlayListAdapter.IntentHolder>() {

    private var datalist:List<PlayList> = emptyList()

    fun setList(datalist:List<PlayList>) {
        this.datalist = datalist
    }

    fun filterList(filterList: List<PlayList>) {
        datalist = filterList
        notifyDataSetChanged()
    }

    class IntentHolder(val binding: PlayListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(playList: PlayList) {
            binding.playList = playList
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntentHolder {
        val binding = PlayListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return IntentHolder(binding)
    }

    override fun onBindViewHolder(holder: IntentHolder, position: Int) {
        holder.bind(datalist[position])
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}