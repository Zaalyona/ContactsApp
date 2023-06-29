package com.example.contactsapp

interface ContactRepository {

    fun addContact(name: String, surname: String, number: String)

    fun getContact(): List<Contact>

    fun editContact(
        contactId: String,
        newContactName: String,
        newContactSurname: String,
        newContactNumber: String)

    fun deleteContact(contactId: String)
}