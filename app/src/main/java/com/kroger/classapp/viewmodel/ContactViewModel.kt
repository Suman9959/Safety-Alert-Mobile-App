package com.kroger.classapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kroger.classapp.data.repository.ContactRepository
import com.kroger.classapp.model.Contact
import com.kroger.classapp.model.EmergencyEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val contactRepository: ContactRepository
) : ViewModel() {

    // LiveData holding the list of all contacts, observed by the UI.
    val allContacts: LiveData<List<Contact>> = contactRepository.getContacts()

    // Retrieves a list of contacts designated as emergency contacts.
    fun getEmergencyContacts(): List<Contact> = contactRepository.getEmergencyContacts()

    // Adds a new contact
    fun add(contact: Contact) = viewModelScope.launch {
        contactRepository.saveContact(contact)
    }
    // Deletes an existing contact
    fun delete(contact: Contact) = viewModelScope.launch {
        contactRepository.deleteContact(contact)
    }

    // Updates an existing contact's details
    fun update(contact: Contact) = viewModelScope.launch {
        contactRepository.updateContact(contact)
    }
}

