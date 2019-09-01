package com.example.advance.callblocker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.advance.callblocker.R
import com.example.advance.callblocker.callbacks.OnContactOptionClickListener
import com.example.advance.callblocker.callbacks.OnItemClickListener
import com.example.advance.callblocker.dataloader.ProfileLoader
import com.example.advance.callblocker.models.Contact
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ViewHolder>(){

    private var contacts = mutableListOf<Contact>()
    private var onItemClickListener : OnItemClickListener<Contact>?= null
    private var onContactOptionClickListener : OnContactOptionClickListener?= null


    fun setOnContactOptionClickListener(onContactOptionClickListener : OnContactOptionClickListener?){
        this.onContactOptionClickListener = onContactOptionClickListener
    }


    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<Contact>){
        this.onItemClickListener = onItemClickListener
    }

    fun setItems(contacts : MutableList<Contact>){
        this.contacts.clear()
        this.contacts.addAll(contacts)
        notifyDataSetChanged()
    }

    fun addItem(contact : Contact){
        this.contacts.add(contact)
        notifyItemInserted(this.contacts.size-1)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact , parent , false)
        return ViewHolder(view)
    }

    override fun getItemCount()= contacts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(contacts[position])
    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun onBind(contact: Contact) {
            val context = itemView.context
            ProfileLoader.loadProfileImage(context , itemView.iv_user_photo , contact.path)
            itemView.tv_name.text = contact.name
            itemView.tv_phone.text = contact.phone

            itemView.fgView.setOnClickListener{
                onItemClickListener?.onItemClick(contact)
            }

            itemView.iv_message.setOnClickListener{
                onContactOptionClickListener?.onMessageItemClick(contact)
            }

            itemView.iv_call.setOnClickListener{
                onContactOptionClickListener?.onCallItemClick(contact)
            }

            itemView.iv_edit.setOnClickListener{
                onContactOptionClickListener?.onEditItemClick(contact)
            }

            itemView.iv_delete.setOnClickListener{
                onContactOptionClickListener?.onDeleteItemClick(contact)
            }
        }

    }
}