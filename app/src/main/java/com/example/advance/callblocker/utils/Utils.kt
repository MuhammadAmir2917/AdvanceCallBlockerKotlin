package com.example.advance.callblocker.utils

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.example.advance.callblocker.models.Contact
import java.nio.file.Files.size
import java.nio.file.Files.size





object Utils {
    fun visibleViews(duration: Long, vararg views: View) {
        for (view in views) {
            view.animate().alpha(1f).setDuration(duration).setInterpolator(
                AccelerateDecelerateInterpolator()
            )
                .withStartAction { view.visibility = View.VISIBLE }
        }
    }

    fun invisibleViews(duration: Long, vararg views: View) {
        for (view in views) {
            view.animate().alpha(0f).setDuration(duration).setInterpolator(AccelerateDecelerateInterpolator())
                .withEndAction { view.visibility = View.INVISIBLE }
        }
    }

    fun goneViews(duration: Long, vararg views: View) {
        for (view in views) {
            view.animate().alpha(0f).setDuration(duration).setInterpolator(AccelerateDecelerateInterpolator())
                .withEndAction { view.visibility = View.GONE }
        }
    }

    fun addAlphabets(list: MutableList<Contact>): MutableList<Contact> {
        var index = 0
        val customList = mutableListOf<Contact>()
        val firstContact = Contact()
        firstContact.name = list[0].name[0].toString()
        firstContact.sectionType = TYPE_LETTER
        customList.add(firstContact)
        index = 0
        while (index < list.size - 1) {
            val contact = Contact()
            val name1 = list[index].name[0]
            val name2 = list[index + 1].name[0]
            if (name1 == name2) {
                list[index].sectionType= TYPE_CONTACT
                customList.add(list[index])
            } else {
                list[index].sectionType=TYPE_CONTACT
                customList.add(list[index])
                contact.name  = name2.toString()
                contact.sectionType=TYPE_LETTER
                customList.add(contact)
            }
            index++
        }

        list[index].sectionType =TYPE_CONTACT
        customList.add(list[index])
        return customList
    }
}