package com.example.singandsongs.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.singandsongs.databinding.CantoListItemBinding
import com.example.singandsongs.model.Canto


class CantoAdapter():RecyclerView.Adapter<CantoAdapter.IntentHolder>() {

    lateinit var datalist:List<Canto>

    fun setList(datalist:List<Canto>) {
        this.datalist = datalist
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
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}