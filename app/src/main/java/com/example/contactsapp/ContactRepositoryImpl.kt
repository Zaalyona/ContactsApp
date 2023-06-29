package com.example.contactsapp

import io.realm.Realm
import io.realm.kotlin.deleteFromRealm
import java.util.UUID

class ContactRepositoryImpl(
    private val realm: Realm
) : ContactRepository {
    override fun addContact(name: String, surname: String, number: String) {
        realm.executeTransaction {
            it.createObject(Contact::class.java, UUID.randomUUID().toString()).apply {
                this.name = name
                this.surname = surname
                this.number = number
            }
        }
    }

    override fun getContact(): List<Contact> {
        return realm.where(Contact::class.java).findAll()
    }

    override fun editContact(
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

    override fun deleteContact(contactId: String) {
        val contact = realm.where(Contact::class.java)
            .equalTo("id", contactId)
            .findFirst()

        realm.executeTransaction {
            contact!!.deleteFromRealm()
        }
    }
}

    /*private val contactList = mutableListOf<FakeContact>()

    fun addContact(contact: FakeContact) {
        contactList.add(contact)
    }

    fun getAllContacts(): MutableList<FakeContact> {
        return contactList
    }

    fun editContact(
        contactName: String,
        contactSurname: String,
        contactNumber: String,
        newContactNumber: String) {
        contactList.find { it.name == contactName }?.number = newContactNumber
    }

    fun deleteContact(contact: FakeContact) {
        contactList.remove(contact)
    }*/
