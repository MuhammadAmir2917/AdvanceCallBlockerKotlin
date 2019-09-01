package com.example.advance.callblocker.fragments


import android.os.Bundle
import android.view.View

import com.example.advance.callblocker.R
import com.example.advance.callblocker.base.BaseFragment
import com.example.advance.callblocker.base.baseActivity
import com.example.advance.callblocker.dataloader.ProfileLoader
import com.example.advance.callblocker.models.Contact
import com.example.advance.callblocker.utils.NavigationUtils
import kotlinx.android.synthetic.main.fragment_contact_detail.*

class ContactDetailFragment(private val contact : Contact) : BaseFragment() {
    override val layoutResources: Int
        get() = R.layout.fragment_contact_detail

    companion object{
        fun newInstance(contact : Contact) = ContactDetailFragment(contact)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ProfileLoader.loadProfileImage(baseActivity , iv_user_photo , contact.path)
        tv_phone.text = contact.phone
        tv_name.text = contact.name
        tv_email.text = contact.email
        if(contact.email.isEmpty())
            tv_email.text = getString(R.string.no_email)

        iv_email.setOnClickListener {
            if(contact.email.isNotEmpty())
                NavigationUtils.openEmailApp(baseActivity, contact.email)
        }
    }


}
