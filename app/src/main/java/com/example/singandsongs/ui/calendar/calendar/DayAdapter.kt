package com.example.singandsongs.ui.calendar.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.singandsongs.databinding.DayItemBinding
import com.example.singandsongs.model.playing.Day
import com.example.singandsongs.model.playing.FullDay

class DayAdapter(
  private val onLongClickAction: (Day) -> Unit,
  private val onItemClickAction: (Long) -> Unit
): RecyclerView.Adapter<DayAdapter.IntentHolder>() {

  private var datalist:List<FullDay> = emptyList()

  fun setList(datalist:List<FullDay>) {
    this.datalist = datalist
    notifyDataSetChanged()
  }

  fun filterList(filterList: List<FullDay>) {
    datalist = filterList
    notifyDataSetChanged()
  }

  class IntentHolder(val binding: DayItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(day: FullDay) {
      binding.day = day
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntentHolder {
    val binding = DayItemBinding
      .inflate(LayoutInflater.from(parent.context), parent, false)
    return IntentHolder(binding)
  }

  override fun onBindViewHolder(holder: IntentHolder, position: Int) {
    val day = datalist[position]
    holder.bind(day)

    holder.itemView.setOnLongClickListener {
      onLongClickAction.invoke(day.day)
      true
    }

    holder.itemView.setOnClickListener {
      onItemClickAction.invoke(day.day.dayId)
    }
  }

  override fun getItemCount(): Int {
    return datalist.size
  }
}
