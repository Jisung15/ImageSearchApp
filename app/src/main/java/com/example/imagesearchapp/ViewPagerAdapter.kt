package com.example.imagesearchapp

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.imagesearchapp.fragment.ChoiceFragment
import com.example.imagesearchapp.fragment.SearchFragment

class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> SearchFragment()
        else -> ChoiceFragment()
    }
}