package com.example.advance.callblocker.dataloader

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.R.id



object ContactsLoader {
    fun finAll(context: Context) : Cursor?{

        return context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null , null ,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
    }



    fun findContactsByGroupId(context: Context, groupId : Long) : Cursor?{
        val groupURI = ContactsContract.Data.CONTENT_URI
        val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.GroupMembership.CONTACT_ID)

        return context.contentResolver.query(
            groupURI,
            projection,
            ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID
            + "=" + groupId, null, null)
    }

    fun findEmailByContactId(context: Context, id : String) : Cursor?{
        return context.contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
            arrayOf(id),
            null
        )

    }

    fun finContactById(context : Context, id: String) : Cursor?{
        return context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
            arrayOf(id), null
        )
    }

    fun findContactByPhone(phone : String, context: Context) : Cursor?{
        val uri = Uri.withAppendedPath(
            ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
            Uri.encode(phone))

        val projection = arrayOf(
            ContactsContract.PhoneLookup.STARRED,
            //ContactsContract.PhoneLookup.CONTACT_ID,
            ContactsContract.PhoneLookup.DISPLAY_NAME,
            ContactsContract.PhoneLookup.TYPE,
            ContactsContract.PhoneLookup.PHOTO_THUMBNAIL_URI,
            ContactsContract.PhoneLookup.NUMBER
        )

        return context.contentResolver.query(uri , projection , null, null, null)
    }

    fun makeDeleteContactCursor(context: Context, contactUri: Uri?): Cursor? {
        return context.contentResolver.query(contactUri!!, null, null, null, null)

    }

    fun findByFavoriteContact(context: Context) : Cursor?{

        return context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, "${ContactsContract.CommonDataKinds.Phone.STARRED} = ?" ,
            arrayOf("1") ,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " DESC")

    }

    fun updateFavoriteByContactId(context: Context, contactID: Long, i : Int){
        val values = ContentValues()
        values.put(ContactsContract.Contacts.STARRED , i)
        context.contentResolver.update(ContactsContract.Contacts.CONTENT_URI,
            values , "${ContactsContract.Contacts._ID} = ?",
            arrayOf(contactID.toString()))
    }

    fun findAllGroup(context : Context) : Cursor?{
        val projection = arrayOf(
            ContactsContract.Groups._ID ,
            ContactsContract.Groups.TITLE
        )

        return context.contentResolver.query(ContactsContract.Groups.CONTENT_URI,
            projection, null, null, ContactsContract.Groups.TITLE);
    }

    fun countContactsInGroup(context : Context , id : Long) : Int{
        val where = (ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID + "="
                + id + " AND "
                + ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE + "='"
                + ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE + "'")

        val c= context.contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            arrayOf<String>(
                ContactsContract.CommonDataKinds.GroupMembership.RAW_CONTACT_ID,
                ContactsContract.Data.DISPLAY_NAME
            ), where, null, ContactsContract.Data.DISPLAY_NAME + " COLLATE LOCALIZED ASC"
        )

        c?.let {
            return it.count
        }
        return 0

    }
}