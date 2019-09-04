package com.example.advance.callblocker.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.advance.callblocker.R
import com.example.advance.callblocker.adapters.ContactsAdapter
import com.example.advance.callblocker.base.BaseFragment
import com.example.advance.callblocker.base.baseActivity
import com.example.advance.callblocker.base.toast
import com.example.advance.callblocker.callbacks.OnContactOptionClickListener
import com.example.advance.callblocker.callbacks.OnItemClickListener
import com.example.advance.callblocker.events.Events
import com.example.advance.callblocker.events.GlobalBus
import com.example.advance.callblocker.models.Contact
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_favorite_list.*
import org.greenrobot.eventbus.Subscribe

class FavoriteListFragment : BaseFragment(), OnContactOptionClickListener {
    override val layoutResources: Int
        get() = R.layout.fragment_favorite_list

    private lateinit var adapter : ContactsAdapter
    companion object{
        // fun newInstance() = GroupListFragment()
        @Volatile var instance : FavoriteListFragment?= null
        operator fun invoke() = instance ?: synchronized(this){
            instance ?: FavoriteListFragment().also { instance = it }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlobalBus.invoke().register(this)

        adapter = ContactsAdapter()


        rv_contacts.layoutManager = LinearLayoutManager(baseActivity)
        rv_contacts.adapter = adapter
        contactsRepository.loadFavoriteContacts(baseActivity, observer)

        adapter.setOnItemClickListener(object : OnItemClickListener<Contact> {
            override fun onItemClick(t: Contact) {
                baseActivity.replaceFragment(
                    savedInstanceState,
                    R.id.fm_contacts,
                    ContactDetailFragment.newInstance(t)
                )
            }
        })

        adapter.setOnContactOptionClickListener(this)
    }

    private val observer  = object : Observer<Contact>{
        override fun onComplete() {
            showView()
        }

        override fun onSubscribe(d: Disposable) {
        }

        override fun onNext(t: Contact) {
            adapter.addItem(t)
        }

        override fun onError(e: Throwable) {
        }

    }

    private fun showView() {
        if(adapter.itemCount==0){
            rv_contacts.visibility = View.GONE
            tv_msg.visibility = View.VISIBLE
        }else{
            rv_contacts.visibility = View.VISIBLE
            tv_msg.visibility = View.GONE
        }
    }

    override fun onCallItemClick(contact: Contact) {
        baseActivity.toast("Call to")
    }

    override fun onMessageItemClick(contact: Contact) {
        baseActivity.toast("Message to")
    }

    override fun onEditItemClick(contact: Contact) {
        baseActivity.toast("Edit")
    }

    override fun onDeleteItemClick(contact: Contact) {
        baseActivity.toast("Delete")
    }


    override fun onDestroy() {
        GlobalBus.invoke().unregister(this)
        super.onDestroy()
    }

    @Subscribe
    fun contactFavoriteEvent(event : Events.ContactFavoriteEvent){
        if(event.contact.fav==1){
            adapter.addItem(event.contact)
            showView()
        }else{
            adapter.removeItem(event.contact)
            showView()

        }
    }
}
