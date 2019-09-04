package com.example.advance.callblocker.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.example.advance.callblocker.R
import com.example.advance.callblocker.adapters.GroupAdapter
import com.example.advance.callblocker.adapters.ViewPagerAdapter
import com.example.advance.callblocker.base.BaseActivity
import com.example.advance.callblocker.fragments.*
import kotlinx.android.synthetic.main.bottom_menu.*
import kotlinx.android.synthetic.main.content_main.*
import com.example.advance.callblocker.base.BaseFragment
import com.example.advance.callblocker.models.ContactsType


class MainActivity : BaseActivity() {
    override val layoutResources: Int
        get() = R.layout.content_main

    private lateinit var pagerAdapter: ViewPagerAdapter

    override fun onActivityReady(savedInstanceState: Bundle?) {
        pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(HomeFragment.invoke())
        pagerAdapter.addFragment(FavoriteListFragment.invoke())
        pagerAdapter.addFragment(DialerFragment.invoke())
        pagerAdapter.addFragment(SettingsFragment.invoke())
        viewpager.offscreenPageLimit = 3
        viewpager.adapter = pagerAdapter

        //addFragment(savedInstanceState , R.id.main_frame, ContactFragment.invoke())
        iv_contacts.setImageLevel(1)

        iv_contacts.setOnClickListener {
            resetImageLevel(it)
            viewpager.setCurrentItem(0, true)
        }

        iv_star.setOnClickListener {
            resetImageLevel(it)
            viewpager.setCurrentItem(1, true)
        }

        iv_dialer.setOnClickListener {
            resetImageLevel(it)
            viewpager.setCurrentItem(2, true)
        }

        iv_settings.setOnClickListener {
            resetImageLevel(it)
            viewpager.setCurrentItem(3, true)
        }
    }


    fun resetImageLevel(view: View) {
        view as ImageView
        iv_contacts.setImageLevel(0)
        iv_star.setImageLevel(0)
        iv_dialer.setImageLevel(0)
        iv_settings.setImageLevel(0)

        view.setImageLevel(1)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
       fragmentBackPress()


    }

    private fun fragmentBackPress() {
        if(GroupAdapter.isGroupDeleteShow) {
            val fragments = supportFragmentManager.fragments
            for (f in fragments) {
                if (f != null && f is GroupListFragment) {
                    f.onBackPressed()
                    return
                }
            }

            super.onBackPressed()
        }else
            super.onBackPressed()
    }
}
