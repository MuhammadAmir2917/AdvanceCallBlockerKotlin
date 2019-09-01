package com.example.advance.callblocker.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.advance.callblocker.R
import com.example.advance.callblocker.base.BaseFragment

class FavoriteListFragment : BaseFragment() {
    override val layoutResources: Int
        get() = R.layout.fragment_favorite_list
    companion object{
        // fun newInstance() = GroupListFragment()
        @Volatile var instance : FavoriteListFragment?= null
        operator fun invoke() = instance ?: synchronized(this){
            instance ?: FavoriteListFragment().also { instance = it }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}
