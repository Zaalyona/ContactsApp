package com.example.contactsapp.presenter

import com.example.contactsapp.Contact

interface MainAction {

    fun onAddContact(contacts: List<Contact>)
}