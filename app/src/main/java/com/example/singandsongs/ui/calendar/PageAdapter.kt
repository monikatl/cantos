package com.example.singandsongs.ui.calendar

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.singandsongs.ui.calendar.calendar.CalendarFragment
import com.example.singandsongs.ui.calendar.list.PlayingListFragment

class PageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

  override fun getItemCount(): Int {
    return 2
  }

  override fun createFragment(position: Int): Fragment {
    return when (position) {
      0 -> CalendarFragment()
      1 -> PlayingListFragment()
      else -> throw IllegalStateException("Invalid position")
    }
  }
}
