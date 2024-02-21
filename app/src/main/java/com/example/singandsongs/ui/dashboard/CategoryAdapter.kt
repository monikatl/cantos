package com.example.singandsongs.ui.dashboard

import android.graphics.Color
import com.example.singandsongs.databinding.CategoryItemBinding
import com.example.singandsongs.model.Kind
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(private val onCategoryClicked: (Int) -> Unit):RecyclerView.Adapter<CategoryAdapter.IntentHolder>() {

    var datalist:List<Kind> = emptyList()

    fun setList(datalist:List<Kind>) {
        this.datalist = datalist
        notifyDataSetChanged()
    }

    fun filterList(filterList: List<Kind>) {
        datalist = filterList
        notifyDataSetChanged()
    }


    class IntentHolder(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(kind: Kind) {
            binding.category = kind
            binding.color = Color.parseColor(kind.color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntentHolder {
        val binding = CategoryItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return IntentHolder(binding)
    }

    override fun onBindViewHolder(holder: IntentHolder, position: Int) {
        holder.bind(datalist[position])

        holder.itemView.setOnClickListener { onCategoryClicked.invoke(position) }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}