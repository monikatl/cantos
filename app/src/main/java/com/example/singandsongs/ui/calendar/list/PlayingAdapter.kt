package com.example.singandsongs.ui.calendar.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.singandsongs.databinding.PlayingItemBinding
import com.example.singandsongs.model.playing.FullPlaying
import com.example.singandsongs.model.playing.Playing

class PlayingAdapter(
  private val onLongClickAction: (Playing) -> Unit,
  private val onItemClickAction: (Long) -> Unit
): RecyclerView.Adapter<PlayingAdapter.IntentHolder>() {

  private var datalist:List<FullPlaying> = emptyList()

  fun setList(datalist:List<FullPlaying>) {
    this.datalist = datalist
    notifyDataSetChanged()
  }

  fun filterList(filterList: List<FullPlaying>) {
    datalist = filterList
    notifyDataSetChanged()
  }

  class IntentHolder(val binding: PlayingItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(playing: FullPlaying) {
      binding.playing = playing
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntentHolder {
    val binding = PlayingItemBinding
      .inflate(LayoutInflater.from(parent.context), parent, false)
    return IntentHolder(binding)
  }

  override fun onBindViewHolder(holder: IntentHolder, position: Int) {
    val playing = datalist[position]
    holder.bind(playing)

    holder.itemView.setOnLongClickListener {
      onLongClickAction.invoke(playing.playing)
      true
    }

    holder.itemView.setOnClickListener {
      onItemClickAction.invoke(playing.playing.playingId)
    }
  }

  override fun getItemCount(): Int {
    return datalist.size
  }
}

