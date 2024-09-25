package com.kroger.classapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kroger.classapp.R
import android.content.SharedPreferences
import android.net.Uri
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SwitchCompat
import androidx.preference.PreferenceManager


class SettingFragment : Fragment() {

    private lateinit var nightModeSwitch: SwitchCompat
    private lateinit var notificationsSwitch: SwitchCompat
    private lateinit var privateAccountSwitch: SwitchCompat
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var fragmentNavigation: FragmentNavigation
    private lateinit var nameTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var profileImageView: ImageView


    interface FragmentNavigation {
        fun replaceFragment(fragment: Fragment)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        // Initialize TextViews
        nameTextView = view.findViewById(R.id.nameTextView)
        phoneTextView = view.findViewById(R.id.phoneTextView)
        profileImageView = view.findViewById(R.id.profileImageView)


        // Set initial values for name and phone
        sharedPreferences =
            requireActivity().getSharedPreferences("YOUR_PREF_NAME", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name", "Unknown User")
        val phone = sharedPreferences.getString("phone", "+859 000 0000")
        val imageUriString = sharedPreferences.getString(
            "imageUri",
            "android.resource://com.kroger.classapp/" + R.drawable.user
        )
        nameTextView.text = name
        phoneTextView.text = phone
        if (!imageUriString.isNullOrEmpty()) {
            val imageUri = Uri.parse(imageUriString)
            profileImageView.setImageURI(imageUri)
        } else {
            profileImageView.setImageResource(R.drawable.user)
        }



        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        // Initialize SwitchCompat views
        nightModeSwitch = view.findViewById(R.id.nightModeSwitch)
        notificationsSwitch = view.findViewById(R.id.notificationsSwitch)
        privateAccountSwitch = view.findViewById(R.id.privateAccountSwitch)

        // Set up switch listeners
        setUpSwitchListeners()

        val isNightModeEnabled = sharedPreferences.getBoolean(
            "night_mode_enabled",
            false
        )
        nightModeSwitch.isChecked = isNightModeEnabled


        // Set initial state of notifications switch
        val isNotificationsEnabled = sharedPreferences.getBoolean("notifications_enabled", false)
        notificationsSwitch.isChecked = isNotificationsEnabled


        val isPrivateAccountEnabled = sharedPreferences.getBoolean("private_account_enabled", false)
        privateAccountSwitch.isChecked = isPrivateAccountEnabled

        // Initialize FragmentNavigation
        fragmentNavigation = requireActivity() as FragmentNavigation

        val editProfileButton = view.findViewById<Button>(R.id.editProfileButton)
        editProfileButton.setOnClickListener {
            val editProfileFragment = EditProfileFragment()
            fragmentNavigation.replaceFragment(editProfileFragment)
        }

        return view
    }

    private fun setUpSwitchListeners() {
        // Set up switch listeners for night mode switch
        nightModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Create and show a dialog informing the user that this feature will be available in the future
            if (isChecked) {
                // Show dialog about the unimplemented feature
                showFeatureComingSoonDialog()

                // Reset the switch to its original state
                nightModeSwitch.isChecked = false
            }
        }

        // Set up switch listeners for notifications switch
        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Handle notifications switch change
            sharedPreferences.edit().putBoolean("notifications_enabled", isChecked).apply()
        }

        privateAccountSwitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("private_account_enabled", isChecked).apply()
        }
    }

    fun updateProfileInfo(editedName: String, editedPhone: String, imageUri: Uri?) {
        nameTextView.text = editedName
        phoneTextView.text = editedPhone
        if (imageUri != null) {
            profileImageView.setImageURI(imageUri)
        }

    }


    private fun showFeatureComingSoonDialog() {
        // Create and show a dialog informing the user that this feature will be available in the future
        AlertDialog.Builder(requireContext())  // Context might be required here, 'this' can be replaced with appropriate context
            .setTitle("Feature Coming Soon")
            .setMessage("This feature will be available in a future update. Stay tuned!")
            .setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()  // Close the dialog
            }
            .create()
            .show()
    }
}
