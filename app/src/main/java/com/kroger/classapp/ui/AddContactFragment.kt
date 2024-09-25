package com.kroger.classapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kroger.classapp.MainActivity
import com.kroger.classapp.R
import com.kroger.classapp.model.Contact
import com.kroger.classapp.viewmodel.ContactViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddContactFragment : Fragment() {
    private val contactViewModel: ContactViewModel by viewModels()

    private lateinit var nameEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var backButton: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_contact, container, false)

        // Initialize views
        nameEditText = view.findViewById(R.id.nameEditText)
        phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText)
        saveButton = view.findViewById(R.id.saveButton)
        backButton = view.findViewById(R.id.backButton)

        // Set click listeners
        saveButton.setOnClickListener {
            saveContact()
        }

        backButton.setOnClickListener {
            if (activity is MainActivity) {
                (activity as MainActivity).replaceFragment(ContactFragment())
            }
        }

        return view
    }

    private fun saveContact() {
        val name = nameEditText.text.toString().trim()
        val phoneNumber = phoneNumberEditText.text.toString().trim()



        // Validate input
        if (name.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Name and phone number cannot be empty",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val contact = Contact(name = name, number = phoneNumber)
        contactViewModel.add(contact)

        Toast.makeText(requireContext(), "Contact saved", Toast.LENGTH_SHORT).show()

        // Clear the input fields
        nameEditText.text.clear()
        phoneNumberEditText.text.clear()
    }
}
