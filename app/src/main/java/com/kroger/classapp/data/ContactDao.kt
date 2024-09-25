package com.kroger.classapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kroger.classapp.model.Contact

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact ORDER BY uid DESC")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM contact")
    fun getEmergencyContacts(): List<Contact>

    @Insert
    fun insertAllContacts(vararg contacts: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @Update
    fun updateContact(contact: Contact)
}
