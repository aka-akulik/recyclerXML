package com.example.recyclerviewx

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewx.mvvm.Contact
import com.example.recyclerviewx.mvvm.ContactAdapter
import com.example.recyclerviewx.mvvm.ContactViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addContactButton: Button
    private lateinit var contactAdapter: ContactAdapter

    private val contactViewModel: ContactViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        addContactButton = findViewById(R.id.addContactButton)

        contactAdapter = ContactAdapter(mutableListOf()) { position ->
            contactViewModel.deleteContact(position)
        }
        recyclerView.adapter = contactAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        contactViewModel.contacts.observe(this, Observer { contacts ->
            contactAdapter.updateContacts(contacts)
        })

        addContactButton.setOnClickListener {
            showAddContactDialog()
        }
    }

    private fun showAddContactDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_contact, null)
        val nameEditText: EditText = dialogView.findViewById(R.id.nameEditText)
        val phoneEditText: EditText = dialogView.findViewById(R.id.phoneEditText)

        AlertDialog.Builder(this)
            .setTitle("Добавить контакт")
            .setView(dialogView)
            .setPositiveButton("Добавить") { _, _ ->
                val name = nameEditText.text.toString()
                val phone = phoneEditText.text.toString()
                if (name.isNotBlank() && phone.isNotBlank()) {
                    contactViewModel.addContact(Contact(name, phone))
                } else {
                    Toast.makeText(this, "Введите имя и номер телефона", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
}