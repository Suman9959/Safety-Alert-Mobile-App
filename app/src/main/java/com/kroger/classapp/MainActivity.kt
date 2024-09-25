package com.kroger.classapp

import com.kroger.classapp.ui.EditProfileFragment
import android.app.Activity
import android.content.Intent
import com.kroger.classapp.ui.SettingFragment
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kroger.classapp.constants.PermissionConstant
import com.kroger.classapp.databinding.ActivityMainBinding
import com.kroger.classapp.helpers.AppPermissions
import com.kroger.classapp.ui.ContactFragment
import com.kroger.classapp.ui.EmergencyHistoryFragment
import com.kroger.classapp.ui.HomepageFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SettingFragment.FragmentNavigation {
    private lateinit var binding: ActivityMainBinding
    private lateinit var permission: AppPermissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Location Permission
        val navBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        navBar.setOnItemSelectedListener() { x ->
            when (x.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomepageFragment())
                    true
                }

                R.id.nav_history -> {
                    replaceFragment(EmergencyHistoryFragment())
                    true
                }

                R.id.nav_Contacts -> {
                    replaceFragment(ContactFragment())
                    true
                }

                R.id.nav_setting -> {
                    replaceFragment(SettingFragment())
                    true
                }

                else -> false
            }
        }

        permission = AppPermissions()

        // Request permissions
        permission.requestPermissions(this, PermissionConstant.PERMISSION_ALL)


        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.main_fragment_view, HomepageFragment())
        }
    }

    private fun openImageChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val fragment = supportFragmentManager.findFragmentById(R.id.main_fragment_view)
            if (fragment is EditProfileFragment) {
                fragment.handleImageSelection(data.data)
            }
        }
    }


    override fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_view, fragment)
        transaction.commit()
    }

    companion object {
        const val PICK_IMAGE_REQUEST = 1
    }
}
