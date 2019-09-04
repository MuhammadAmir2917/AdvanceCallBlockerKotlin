package com.example.advance.callblocker.fragments


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.advance.callblocker.R
import com.example.advance.callblocker.adapters.ContactsAdapter
import com.example.advance.callblocker.base.BaseFragment
import com.example.advance.callblocker.base.baseActivity
import com.example.advance.callblocker.base.toast
import com.example.advance.callblocker.callbacks.OnContactFavoriteChangeListener
import com.example.advance.callblocker.callbacks.OnContactOptionClickListener
import com.example.advance.callblocker.callbacks.OnItemClickListener
import com.example.advance.callblocker.events.Events
import com.example.advance.callblocker.events.GlobalBus
import com.example.advance.callblocker.models.Contact
import com.example.advance.callblocker.models.ContactsType
import com.example.advance.callblocker.utils.Utils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_contact_list.*
import org.greenrobot.eventbus.Subscribe

class ContactListFragment(
    private val contactsType: ContactsType,
    private val groupId: Long
) : BaseFragment(), OnContactOptionClickListener,
    OnContactFavoriteChangeListener {


    companion object {
        fun newInstance(contactsType: ContactsType = ContactsType.CONTACTS, groupId: Long = -1) =
            ContactListFragment(contactsType, groupId)
        /*@Volatile var instance : ContactListFragment?= null
        operator fun invoke() = instance ?: synchronized(this){
            instance ?: ContactListFragment().also { instance = it }
        }*/
    }

    private lateinit var adapter: ContactsAdapter

    override val layoutResources: Int
        get() = R.layout.fragment_contact_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!GlobalBus.invoke().isRegistered(this))
            GlobalBus.invoke().register(this)

        adapter = ContactsAdapter()
        rv_contacts.layoutManager = LinearLayoutManager(baseActivity)
        rv_contacts.adapter = adapter

        when (contactsType) {
            ContactsType.CONTACTS -> {
                contactsRepository.loadContacts(baseActivity, observer)
            }
            ContactsType.GROUP -> {
                contactsRepository.loadContactByGroupId(baseActivity, groupId, observer)
            }
        }


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
        adapter.setOnContactFavoriteChangListener(this)


    }

    val list = mutableListOf<Contact>()
    private val observer = object : Observer<Contact> {
        override fun onComplete() {
            adapter.addItem(Utils.addAlphabets(list))
        }

        override fun onSubscribe(d: Disposable) {
            list.clear()
        }

        override fun onNext(t: Contact) {
            list.add(t)
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

    override fun onContactFavoriteChange(contact: Contact) {
        contactsRepository.updateFavoriteByContactId(baseActivity, contact.id, contact.fav)
        val event = Events.ContactFavoriteEvent(contact)
        GlobalBus.invoke().post(event)
    }

    @Subscribe
    fun favoriteContactRemoveEvent(event: Events.FavoriteContactRemoveEvent) {
        adapter.updateFavorite(event.contact)
    }

    override fun onDestroy() {
        GlobalBus.invoke().unregister(this)
        super.onDestroy()
    }
}

