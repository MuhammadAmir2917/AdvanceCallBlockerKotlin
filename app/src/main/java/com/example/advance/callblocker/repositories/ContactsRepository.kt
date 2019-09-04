package com.example.advance.callblocker.repositories

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import com.example.advance.callblocker.dataloader.ContactsLoader
import com.example.advance.callblocker.models.Contact
import com.example.advance.callblocker.models.Group
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer








class ContactsRepository {


    fun loadContacts(context: Context, observer: Observer<Contact>) {
        val cursor = ContactsLoader.finAll(context)
        cursor?.let {cr->
            Observable.create(ObservableOnSubscribe<Contact> {
                while (cr.moveToNext()) {
                    it.onNext(getContact(context ,cr))
                }
                it.onComplete()
            }).subscribe(observer)


            cr.close()
        }
    }


    fun loadContactByGroupId(context: Context , groupId : Long , observer: Observer<Contact>){
        val cursor = ContactsLoader.findContactsByGroupId(context, groupId)
        cursor?.let { groupCursor->

          Observable.create(ObservableOnSubscribe <Contact>{
              while (groupCursor.moveToNext()){
                  val id = groupCursor.getString(groupCursor.getColumnIndex(ContactsContract.CommonDataKinds.GroupMembership.CONTACT_ID))
                  val contactCursor = ContactsLoader.finContactById(context, id)
                  contactCursor?.let {cr->

                      while (cr.moveToNext()){
                        it.onNext(getContact(context,cr))
                      }

                      cr.close()
                  }
              }

              it.onComplete()

          }).subscribe(observer)

            groupCursor.close()

        }
    }

    private fun getContact(context: Context ,cursor: Cursor): Contact {

        val fav = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.STARRED))
        val type = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE))
        val id =cursor.getLong(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID))
        val name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
        val phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
        val email =getContactEmail(context, id)
        val path = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI))
        val contactType = when (type) {
            ContactsContract.CommonDataKinds.Phone.TYPE_HOME -> "Home"
            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE -> "Mobile"
            ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK -> "Fax Work"
            ContactsContract.CommonDataKinds.Phone.TYPE_CAR -> "Car"
            ContactsContract.CommonDataKinds.Phone.TYPE_WORK -> "WORK"

            else -> "unknown"
        }

        return Contact(id, path, name, phone, email, contactType , fav , phone)

    }

    fun loadGroups(context: Context , observer: Observer<Group>){
        val cursor = ContactsLoader.findAllGroup(context)
        cursor?.let { cr->
            Observable.create(ObservableOnSubscribe <Group>{
                while (cr.moveToNext()){
                    val id = cr.getLong(cr.getColumnIndex(ContactsContract.Groups._ID))
                    var name = cr.getString(cr.getColumnIndex(ContactsContract.Groups.TITLE))
                    if (name.contains("Group:")) {
                        name = name.substring(name.indexOf("Group:") + 6).trim();
                    }
                    if (name.contains("Favorite_")) {
                        name = "Favorites";
                    }
                    if(name.contains("Starred in Android") || name.contains("My Contacts"))
                        continue;

                    it.onNext(Group(id, name, ContactsLoader.countContactsInGroup(context , id)))
                }

                it.onComplete()
            }).subscribe(observer)

            cr.close()
        }
    }

    private fun getContactEmail(context: Context, id: Long): String {
        var email = ""
        val cursor = ContactsLoader.findEmailByContactId(context, id.toString())
        cursor?.let {
            while (it.moveToNext()){
                email = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
            }


            it.close()
        }
        return email
    }

    fun loadFavoriteContacts(context : Context , observer: Observer<Contact>){
        val coursor = ContactsLoader.findByFavoriteContact(context)
        coursor?.let {cr->
            Observable.create(ObservableOnSubscribe<Contact> {
                while (cr.moveToNext()){
                    it.onNext(getContact(context,cr))
                }
                it.onComplete()
            }).subscribe(observer)

            cr.close()
        }
    }

    fun updateFavoriteByContactId(context: Context , contactId : Long , fav : Int){
        ContactsLoader.updateFavoriteByContactId(context, contactId , fav)
    }



}