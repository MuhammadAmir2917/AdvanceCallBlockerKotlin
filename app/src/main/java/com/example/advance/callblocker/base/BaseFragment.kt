package com.example.advance.callblocker.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.advance.callblocker.repositories.ContactsRepository

abstract class BaseFragment : Fragment(){

    protected lateinit var contactsRepository: ContactsRepository

    @get:LayoutRes
    protected abstract val layoutResources : Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contactsRepository = ContactsRepository()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResources , container, false)
    }

    fun getColorRes(@ColorRes id : Int) = ContextCompat.getColor(baseActivity , id)





}