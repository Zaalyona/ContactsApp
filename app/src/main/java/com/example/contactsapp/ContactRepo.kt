package com.example.contactsapp

class ContactRepo() {

    private val contactList = mutableListOf<FakeContact>()

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
    }
}