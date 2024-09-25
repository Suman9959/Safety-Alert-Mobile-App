package com.kroger.classapp.data.repository

import androidx.lifecycle.LiveData
import com.kroger.classapp.data.ContactDao
import com.kroger.classapp.data.EmergencyEventDao
import com.kroger.classapp.model.Contact
import javax.inject.Inject

class ContactRepository @Inject constructor (
    private val contactDao: ContactDao
){
    fun getContacts(): LiveData<List<Contact>> = contactDao.getAllContacts()
    fun getEmergencyContacts(): List<Contact> = contactDao.getEmergencyContacts()

    fun saveContact(contact: Contact) = contactDao.insertAllContacts(contact)


    fun deleteContact(contact: Contact) = contactDao.deleteContact(contact)

    suspend fun updateContact(contact: Contact) {
        contactDao.updateContact(contact)
    }

}
