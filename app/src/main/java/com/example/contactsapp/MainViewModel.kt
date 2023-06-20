package com.example.contactsapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.createObject
import io.realm.kotlin.deleteFromRealm
import java.util.UUID

class MainViewModel : ViewModel() {

    private val realm: Realm = Realm.getDefaultInstance()
    val allContacts: LiveData<List<Contact>>
        get() = getAllContacts()

    fun addContact(name: String, surname: String, number: String) {
        realm.executeTransaction {
            val model = it.createObject<Contact>(UUID.randomUUID().toString()).apply {
                this.name = name
                this.surname = surname
                this.number = number
            }

            it.insertOrUpdate(model)
        }
    }

    private fun getAllContacts(): MutableLiveData<List<Contact>> {
        val list = MutableLiveData<List<Contact>>()

        val allContacts = realm.where(Contact::class.java).findAll()
        list.value = allContacts.subList(0, allContacts.size)
        return list
    }

    fun editContact(
        contactId: String,
        newContactName: String,
        newContactSurname: String,
        newContactNumber: String) {

        val contact = realm.where(Contact::class.java)
            .equalTo("id", contactId)
            .findFirst()

        realm.executeTransaction {
            contact?.name = newContactName
            contact?.surname = newContactSurname
            contact?.number = newContactNumber
            realm.insertOrUpdate(contact)
        }
    }

    fun deleteContact(contactId: String) {
        val contact = realm.where(Contact::class.java)
            .equalTo("id", contactId)
            .findFirst()

        realm.executeTransaction {
            contact!!.deleteFromRealm()
        }
    }
}
