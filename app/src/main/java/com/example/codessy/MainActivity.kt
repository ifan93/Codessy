package com.example.codessy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.example.codessy.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(3000)
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)

        val user = Firebase.auth.currentUser
        if (user != null) {

            setContentView(binding.root)
            replaceFragment(Home())

        } else {
            startActivity(Intent(this,Login::class.java))
        }


        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    replaceFragment(Home())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.profile -> {
                    replaceFragment(Profile())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.userManual -> {
                    replaceFragment(Progress())
                    return@setOnNavigationItemSelectedListener true
                }



                else -> return@setOnNavigationItemSelectedListener false





            }
        }



    }


    private fun replaceFragment(fragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}