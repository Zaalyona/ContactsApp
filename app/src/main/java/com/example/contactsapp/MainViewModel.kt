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

class MainViewModel(private val contactRepository: ContactRepository) : ViewModel() {

    val allContacts: ContactLiveData
        get() = getAllContacts() as ContactLiveData

    fun addContact(name: String, surname: String, number: String) {

        contactRepository.addContact(name, surname, number)
    }

    private fun getAllContacts(): MutableLiveData<List<Contact>> {
        val list = ContactLiveData()
        val allContacts = contactRepository.getContact()
        list.value = allContacts.subList(0, allContacts.size)
        return list
    }

    fun editContact(
        contactId: String,
        newContactName: String,
        newContactSurname: String,
        newContactNumber: String) {

        contactRepository.editContact(
            contactId = contactId,
            newContactName = newContactName,
            newContactSurname = newContactSurname,
            newContactNumber = newContactNumber
        )
    }

    fun deleteContact(contactId: String) {

        contactRepository.deleteContact(contactId = contactId)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("MainViewModel", "MainViewModel -> onCleared")
    }
}
