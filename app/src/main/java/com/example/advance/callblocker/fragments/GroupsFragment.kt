package com.example.advance.callblocker.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.advance.callblocker.R
import com.example.advance.callblocker.base.BaseFragment
import com.example.advance.callblocker.base.baseActivity

class GroupsFragment : BaseFragment() {
    override val layoutResources: Int
        get() = R.layout.fragment_groups

    companion object{
        // fun newInstance() = GroupListFragment()
        @Volatile var instance : GroupsFragment?= null
        operator fun invoke() = instance ?: synchronized(this){
            instance ?: GroupsFragment().also { instance = it }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseActivity.addFragment(savedInstanceState , R.id.fm_groups , GroupListFragment.invoke())
    }






}
