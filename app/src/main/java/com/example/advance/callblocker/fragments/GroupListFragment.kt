package com.example.advance.callblocker.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.example.advance.callblocker.R
import com.example.advance.callblocker.adapters.GroupAdapter
import com.example.advance.callblocker.base.BaseFragment
import com.example.advance.callblocker.base.baseActivity
import com.example.advance.callblocker.base.log
import com.example.advance.callblocker.callbacks.OnBackPressed
import com.example.advance.callblocker.callbacks.OnItemClickListener
import com.example.advance.callblocker.models.Group
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_group_list.*


class GroupListFragment : BaseFragment() , OnBackPressed{


    override val layoutResources: Int
        get() = R.layout.fragment_group_list

    companion object{
       // fun newInstance() = GroupListFragment()
        @Volatile var instance : GroupListFragment?= null
        operator fun invoke() = instance ?: synchronized(this){
            instance ?: GroupListFragment().also { instance = it }
        }

    }

    private lateinit var adapter : GroupAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = GroupAdapter()
        rv_groups.layoutManager= GridLayoutManager(baseActivity , 3)
        rv_groups.adapter  = adapter
        contactsRepository.loadGroups(baseActivity , observer);

        adapter.setOnItemClickListener(object : OnItemClickListener<Group>{
            override fun onItemClick(t: Group) {
                baseActivity.replaceFragment(savedInstanceState , R.id.fm_groups , GroupContactListFragment.newInstance(t.id))
            }
        })
    }

    private val observer = object : Observer<Group>{
        override fun onComplete() {

        }

        override fun onSubscribe(d: Disposable) {
        }

        override fun onNext(t: Group) {
            adapter.addItem(t)
        }

        override fun onError(e: Throwable) {
        }

    }

    override fun onBackPressed() {
        if(GroupAdapter.isGroupDeleteShow){
            adapter.resetGroupItem()
        }
    }



}
