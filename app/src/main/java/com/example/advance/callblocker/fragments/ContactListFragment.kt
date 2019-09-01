package com.example.advance.callblocker.fragments


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.advance.callblocker.R
import com.example.advance.callblocker.adapters.ContactsAdapter
import com.example.advance.callblocker.base.BaseFragment
import com.example.advance.callblocker.base.baseActivity
import com.example.advance.callblocker.base.toast
import com.example.advance.callblocker.callbacks.OnContactOptionClickListener
import com.example.advance.callblocker.callbacks.OnItemClickListener
import com.example.advance.callblocker.models.Contact
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_contact_list.*

class ContactListFragment : BaseFragment(), OnContactOptionClickListener {


    companion object{
        // fun newInstance() = GroupListFragment()
        @Volatile var instance : ContactListFragment?= null
        operator fun invoke() = instance ?: synchronized(this){
            instance ?: ContactListFragment().also { instance = it }
        }
    }



    private lateinit var adapter: ContactsAdapter

    override val layoutResources: Int
        get() = R.layout.fragment_contact_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ContactsAdapter()
        rv_contacts.layoutManager = LinearLayoutManager(baseActivity)
        rv_contacts.adapter = adapter

        contactsRepository.loadContacts(baseActivity, observer)

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


    private val observer = object : Observer<Contact> {
        override fun onComplete() {

        }

        override fun onSubscribe(d: Disposable) {
        }

        override fun onNext(t: Contact) {
            adapter.addItem(t)
        }

        override fun onError(e: Throwable) {
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
}

