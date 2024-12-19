package com.example.recyclerviewx.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactViewModel : ViewModel() {

    private val _contacts = MutableLiveData<MutableList<Contact>>(mutableListOf())
    val contacts: LiveData<MutableList<Contact>> get() = _contacts

    fun addContact(contact: Contact) {
        _contacts.value?.add(contact)
        _contacts.value = _contacts.value
    }

    fun deleteContact(position: Int) {
        _contacts.value?.removeAt(position)
        _contacts.value = _contacts.value
    }
}