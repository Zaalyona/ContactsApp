package com.example.contactsapp

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testViewModel() {
        val contactRepo = ContactRepo()
        val name = "Stas"
        val surname = "Qmar"
        val number = "+88005555353"

        val contact = FakeContact(
            name = name,
            surname = surname,
            number = number
        )

        contactRepo.addContact(contact)

        val list = contactRepo.getAllContacts()
        val lastContact = list.last()

        assertEquals(contact, lastContact)

    }
}