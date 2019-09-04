package com.example.advance.callblocker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.advance.callblocker.R
import com.example.advance.callblocker.callbacks.OnContactFavoriteChangeListener
import com.example.advance.callblocker.callbacks.OnContactOptionClickListener
import com.example.advance.callblocker.callbacks.OnItemClickListener
import com.example.advance.callblocker.dataloader.ProfileLoader
import com.example.advance.callblocker.models.Contact
import com.example.advance.callblocker.utils.TYPE_CONTACT
import com.example.advance.callblocker.utils.TYPE_LETTER
import kotlinx.android.synthetic.main.alphabet_section_view.view.*
import kotlinx.android.synthetic.main.item_contact.view.*


class ContactsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var contacts = mutableListOf<Contact>()
    private var onItemClickListener: OnItemClickListener<Contact>? = null
    private var onContactOptionClickListener: OnContactOptionClickListener? = null
    private var onContactFavoriteChangeListener: OnContactFavoriteChangeListener? = null


    fun setOnContactFavoriteChangListener(onContactFavoriteChangeListener: OnContactFavoriteChangeListener?) {
        this.onContactFavoriteChangeListener = onContactFavoriteChangeListener
    }


    fun setOnContactOptionClickListener(onContactOptionClickListener: OnContactOptionClickListener?) {
        this.onContactOptionClickListener = onContactOptionClickListener
    }


    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<Contact>) {
        this.onItemClickListener = onItemClickListener
    }

    fun setItems(contacts: MutableList<Contact>) {
        this.contacts.clear()
        this.contacts.addAll(contacts)
        notifyDataSetChanged()
    }

    fun addItem(contact: Contact) {
        this.contacts.add(contact)
        notifyItemInserted(this.contacts.size - 1)
    }

    fun addItem(contacts: MutableList<Contact>) {
        this.contacts.clear()
        this.contacts.addAll(contacts)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       if (viewType == TYPE_LETTER) {
           val view =  LayoutInflater.from(parent.context)
                .inflate(R.layout.alphabet_section_view, parent, false)
           return LetterViewHolder(view)
        }else {
           val view= LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
            return ViewHolder(view)
        }

    }

    override fun getItemViewType(position: Int): Int {
        var viewType = 0
        if (contacts[position].sectionType == TYPE_LETTER) {
            viewType = TYPE_LETTER
        } else if (contacts[position].sectionType == TYPE_CONTACT) {
            viewType = TYPE_CONTACT
        }

        return viewType
    }

    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder)
            holder.onBind(contacts[position])
        else if(holder is LetterViewHolder){
            holder.onBind(contacts[position])
        }
    }

    fun removeItem(contact: Contact) {
        for (index in 0 until contacts.size) {
            if (contacts[index].id == contact.id) {
                contacts.removeAt(index)
                notifyItemRemoved(index)
                break
            }
        }
    }

    fun updateFavorite(contact: Contact) {
        for (index in 0 until contacts.size) {
            if (contacts[index].id == contact.id) {
                contacts[index].fav = 0
                notifyItemChanged(index)
                break
            }
        }
    }

    fun getItems() = contacts

    inner class LetterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun onBind(contact: Contact) {
            itemView.tv_section.text= contact.name
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(contact: Contact) {
            val context = itemView.context
            ProfileLoader.loadProfileImage(context, itemView.iv_user_photo, contact.path)
            itemView.tv_name.text = contact.name
            itemView.tv_phone.text = contact.phone
            itemView.chk_favorite.setOnCheckedChangeListener(null)
            itemView.chk_favorite.isChecked = when (contact.fav) {
                0 -> false
                else -> true
            }

            itemView.chk_favorite.setOnClickListener {
                it as CheckBox
                if (it.isChecked) {
                    contact.fav = 1
                } else {
                    contact.fav = 0
                }

                onContactFavoriteChangeListener?.onContactFavoriteChange(contact)

            }

            itemView.iv_user_photo.setOnClickListener {
                onItemClickListener?.onItemClick(contact)
            }

            itemView.fgView.setOnClickListener {
                onItemClickListener?.onItemClick(contact)
            }

            itemView.iv_message.setOnClickListener {
                onContactOptionClickListener?.onMessageItemClick(contact)
            }

            itemView.iv_call.setOnClickListener {
                onContactOptionClickListener?.onCallItemClick(contact)
            }

            itemView.iv_edit.setOnClickListener {
                onContactOptionClickListener?.onEditItemClick(contact)
            }

            itemView.iv_delete.setOnClickListener {
                onContactOptionClickListener?.onDeleteItemClick(contact)
            }
        }

    }
}