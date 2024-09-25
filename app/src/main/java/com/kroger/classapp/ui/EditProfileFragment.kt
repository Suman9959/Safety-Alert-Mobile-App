package com.kroger.classapp.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kroger.classapp.R
import android.widget.EditText
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.kroger.classapp.MainActivity


class EditProfileFragment : Fragment() {

    private lateinit var nameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    private lateinit var selectImageButton: Button
    private lateinit var profileImageView: ImageView
    private var imageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 1

    fun handleImageSelection(uri: Uri?) {
        this.imageUri = uri
        uri?.let { profileImageView.setImageURI(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)


        nameEditText = view.findViewById(R.id.nameEditText)
        phoneEditText = view.findViewById(R.id.phoneEditText)
        saveButton = view.findViewById(R.id.saveButton)
        cancelButton = view.findViewById(R.id.cancelButton)
        selectImageButton = view.findViewById(R.id.selectImageButton)
        profileImageView = view.findViewById(R.id.profileImageView)


        // Setting click listener for the back button
        val backButton = view.findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            if (activity is MainActivity) {
                (activity as MainActivity).replaceFragment(SettingFragment())
            }
        }

        selectImageButton.setOnClickListener {
            openFileChooser()
        }


        saveButton.setOnClickListener {
            saveProfileChanges()
        }

        cancelButton.setOnClickListener {
            if (activity is MainActivity) {
                (activity as MainActivity).replaceFragment(SettingFragment())
            }
        }

        return view
    }

    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data
            handleImageSelection(imageUri)
            profileImageView.setImageURI(imageUri)


            // Pass the selected image URI to the SettingFragment
            val settingFragment =
                (activity as MainActivity).supportFragmentManager.findFragmentByTag("SettingFragment") as? SettingFragment
            settingFragment?.updateProfileInfo(
                nameEditText.text.toString().trim(),
                phoneEditText.text.toString().trim(),
                imageUri
            )
        }
    }

    private fun saveProfileChanges() {
        // Retrieve the edited name and phone number from the EditText fields

        val editedName = nameEditText.text.toString().trim()
        val editedPhone = phoneEditText.text.toString().trim()
        val imageUri = this.imageUri


        // Validate input
        if (editedName.isEmpty() || editedPhone.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Name and phone number cannot be empty",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val sharedPreferences =
            requireActivity().getSharedPreferences("YOUR_PREF_NAME", Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("name", editedName)
            putString("phone", editedPhone)
            putString("imageUri", imageUri?.toString())
            apply()
        }
        val settingFragment =
            (activity as MainActivity).supportFragmentManager.findFragmentByTag("SettingFragment") as? SettingFragment
        settingFragment?.updateProfileInfo(editedName, editedPhone, imageUri)


        Toast.makeText(requireContext(), "Profile updated", Toast.LENGTH_SHORT).show()
        nameEditText.text.clear()
        phoneEditText.text.clear()
        profileImageView.setImageDrawable(null)

        requireActivity().supportFragmentManager.popBackStack()
    }
}
