package com.example.singandsongs.ui.calendar.places

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.singandsongs.databinding.PlaceItemBinding
import com.example.singandsongs.model.playing.Place

class PlaceAdapter(
  private val onLongClickAction: (Int) -> Unit,
  private val onItemClickAction: (Long) -> Unit
): RecyclerView.Adapter<PlaceAdapter.IntentHolder>() {

  private var datalist:List<Place> = emptyList()

  fun setList(datalist:List<Place>) {
    this.datalist = datalist
    notifyDataSetChanged()
  }

  fun filterList(filterList: List<Place>) {
    datalist = filterList
    notifyDataSetChanged()
  }

  class IntentHolder(val binding: PlaceItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(place: Place) {
      binding.place = place
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntentHolder {
    val binding = PlaceItemBinding
      .inflate(LayoutInflater.from(parent.context), parent, false)
    return IntentHolder(binding)
  }

  override fun onBindViewHolder(holder: IntentHolder, position: Int) {
    val place = datalist[position]
    holder.bind(place)

    holder.itemView.setOnLongClickListener {
      onLongClickAction.invoke(position)
      true
    }

    holder.itemView.setOnClickListener {
      onItemClickAction.invoke(place.placeId)
    }
  }

  override fun getItemCount(): Int {
    return datalist.size
  }
}
