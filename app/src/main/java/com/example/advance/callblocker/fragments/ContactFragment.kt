package com.example.advance.callblocker.fragments


import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advance.callblocker.R
import com.example.advance.callblocker.adapters.ContactsAdapter
import com.example.advance.callblocker.adapters.ViewPagerAdapter
import com.example.advance.callblocker.base.BaseFragment
import com.example.advance.callblocker.base.baseActivity
import com.example.advance.callblocker.base.toast
import com.example.advance.callblocker.callbacks.OnContactOptionClickListener
import com.example.advance.callblocker.callbacks.OnItemClickListener
import com.example.advance.callblocker.models.Contact
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_contact.*


class ContactFragment : BaseFragment(){
    override val layoutResources: Int
        get() = R.layout.fragment_contact

    companion object{
        // fun newInstance() = GroupListFragment()
        @Volatile var instance : ContactFragment?= null
        operator fun invoke() = instance ?: synchronized(this){
            instance ?: ContactFragment().also { instance = it }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseActivity.addFragment(savedInstanceState , R.id.fm_contacts , ContactListFragment.invoke())
    }



}
