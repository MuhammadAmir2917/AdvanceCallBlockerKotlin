package com.example.advance.callblocker.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm){
    private val framents = mutableListOf<Fragment>()

    fun addFragment(fragment : Fragment){
        this.framents.add(fragment)
    }

    override fun getItem(position: Int) = framents[position]

    override fun getCount()= framents.size

}