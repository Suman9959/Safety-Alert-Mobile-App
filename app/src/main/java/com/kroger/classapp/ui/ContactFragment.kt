package com.kroger.classapp.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kroger.classapp.MainActivity
import com.kroger.classapp.R
import com.kroger.classapp.model.Contact
import com.kroger.classapp.ui.adapter.ContactsAdapter
import com.kroger.classapp.viewmodel.ContactViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactFragment : Fragment() {

    private val contactViewModel: ContactViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact, container, false)

        val contactsRecyclerView =
            view.findViewById<RecyclerView>(R.id.contactsRecyclerView).apply {
                layoutManager = LinearLayoutManager(view.context)
            }

        val addContactButton =
            view.findViewById<FloatingActionButton>(R.id.addContactButton).apply {
                setOnClickListener {
                    // Navigate to AddContactFragment
                    if (activity is MainActivity) {
                        (activity as MainActivity).replaceFragment(AddContactFragment())
                    }
                }
            }

        // Initialize the adapter with empty list and setup later with LiveData observation
        val adapter = ContactsAdapter(emptyList(), { contact ->
            showEditDialog(view.context, contact)
        }, { contact ->
            showDeleteConfirmation(view.context, contact)
        })


        contactsRecyclerView.adapter = adapter

        // Observe changes to contacts from the ViewModel
        contactViewModel.allContacts.observe(viewLifecycleOwner) { contacts ->
            adapter.updateContacts(contacts)
        }

        return view
    }

    private fun showDeleteConfirmation(context: Context, contact: Contact) {
        AlertDialog.Builder(context).apply {
            setTitle("Confirm Delete")
            setMessage("Are you sure you want to delete this contact?")
            setPositiveButton("Yes") { _, _ ->
                contactViewModel.delete(contact)
            }
            setNegativeButton("No", null)
            show()
        }
    }

    private fun showEditDialog(context: Context, contact: Contact) {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.edit_contact_dialog, null)
        val nameInput = view.findViewById<EditText>(R.id.editName)
        val phoneInput = view.findViewById<EditText>(R.id.editPhone)

        nameInput.setText(contact.name)
        phoneInput.setText(contact.number)

        AlertDialog.Builder(context).apply {
            setTitle("Edit Contact")
            setView(view)
            setPositiveButton("Edit") { _, _ ->
                val newName = nameInput.text.toString()
                val newNumber = phoneInput.text.toString()
                contactViewModel.update(contact.copy(name = newName, number = newNumber))
            }
            setNegativeButton("Cancel", null)
            show()
        }
    }
}

