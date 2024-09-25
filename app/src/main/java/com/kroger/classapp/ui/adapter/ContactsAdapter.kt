package com.kroger.classapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kroger.classapp.R
import com.kroger.classapp.model.Contact

class ContactsAdapter(
    private var contacts: List<Contact>,
    private val onEdit: (Contact) -> Unit,
    private val onDelete: (Contact) -> Unit
) : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.contactName.text = contact.name
        holder.contactNumber.text = contact.number
        holder.editButton.setOnClickListener { onEdit(contact) }
        holder.deleteButton.setOnClickListener { onDelete(contact) }
    }

    override fun getItemCount() = contacts.size

    fun updateContacts(newContacts: List<Contact>) {
        contacts = newContacts
        notifyDataSetChanged()
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contactName: TextView = itemView.findViewById(R.id.contactNameTextView)
        val contactNumber: TextView = itemView.findViewById(R.id.contactNumberTextView)
        val editButton: Button = itemView.findViewById(R.id.editContactButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteContactButton)
    }
}
