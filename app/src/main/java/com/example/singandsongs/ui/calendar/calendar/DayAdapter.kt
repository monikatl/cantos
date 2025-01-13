package com.example.singandsongs.ui.calendar.calendar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.singandsongs.databinding.DayItemBinding
import com.example.singandsongs.model.playing.Day
import com.example.singandsongs.model.playing.FullDay

class DayAdapter(
  private val onLongClickAction: (Day?) -> Unit,
  private val onItemClickAction: (FullDay) -> Unit
): RecyclerView.Adapter<DayAdapter.IntentHolder>() {

  private var datalist:List<FullDay> = emptyList()

  fun setList(datalist:List<FullDay>) {
    val diffCallback = MyDiffCallback(this.datalist, datalist)
    val diffResult = DiffUtil.calculateDiff(diffCallback)

    this.datalist = datalist
    diffResult.dispatchUpdatesTo(this)
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

    if (day.playings.isNotEmpty()) {
      holder.itemView.setBackgroundColor(Color.BLUE)
    } else {
      holder.itemView.setBackgroundColor(Color.WHITE)
    }

    holder.itemView.setOnLongClickListener {
      onLongClickAction.invoke(day.day)
      true
    }

    holder.itemView.setOnClickListener {
      onItemClickAction.invoke(day)
    }
  }

  override fun getItemCount(): Int {
    return datalist.size
  }

  class MyDiffCallback(
    private val oldList: List<FullDay>,
    private val newList: List<FullDay>
  ) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
      return oldList[oldItemPosition].number == newList[newItemPosition].number
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
      return oldList[oldItemPosition] == newList[newItemPosition]
    }
  }
}
