package com.example.advance.callblocker.fragments


import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.advance.callblocker.R
import com.example.advance.callblocker.adapters.ViewPagerAdapter
import com.example.advance.callblocker.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment(){




    companion object{
        // fun newInstance() = GroupListFragment()
        @Volatile var instance : HomeFragment?= null
        operator fun invoke() = instance ?: synchronized(this){
            instance ?: HomeFragment().also { instance = it }
        }
    }



    private lateinit var pagerAdapter : ViewPagerAdapter
    override val layoutResources: Int
        get() = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerAdapter = ViewPagerAdapter(childFragmentManager)
        pagerAdapter.addFragment(ContactFragment.invoke())
        pagerAdapter.addFragment(GroupsFragment.invoke())
        pagerAdapter.addFragment(BlockedListFragment.invoke())
        viewpager.offscreenPageLimit = 2
        viewpager.adapter = pagerAdapter


        tb_contacts.setOnClickListener {
            val layout = it as LinearLayout
            resetTabColor(layout.getChildAt(0)as TextView)
            viewpager.setCurrentItem(0 , true)

        }

        tb_groups.setOnClickListener {
            val layout = it as LinearLayout
            resetTabColor(layout.getChildAt(0)as TextView)
            viewpager.setCurrentItem(1 , true)

        }

        tb_blocked.setOnClickListener {
            val layout = it as LinearLayout
            resetTabColor(layout.getChildAt(0)as TextView)
            viewpager.setCurrentItem(2 , true)

        }

    }

    fun resetTabColor(textView : TextView){
        tv_contact_tab.setTextColor(getColorRes(R.color.grey_text))
        tv_groups_tab.setTextColor(getColorRes(R.color.grey_text))
        tv_blocked_tab.setTextColor(getColorRes(R.color.grey_text))

        textView.setTextColor(getColorRes(R.color.white))
    }








}
