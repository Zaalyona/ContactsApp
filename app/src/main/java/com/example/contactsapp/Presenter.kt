package com.example.contactsapp

import com.example.contactsapp.presenter.MainAction

class Presenter(private val contactRepository: ContactRepository) {

    private var mainAction: MainAction? = null

    fun initAction(mainAction: MainAction) {
        this.mainAction = mainAction
    }

    fun addContact(name: String, surname: String, number: String) {
        contactRepository.addContact(name, surname, number)
        mainAction?.onAddContact(contactRepository.getContact())
    }
}