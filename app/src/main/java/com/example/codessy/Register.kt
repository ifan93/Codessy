package com.example.codessy

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {

    private lateinit var visibleBtn: ImageButton
    private lateinit var visibleBtn1: ImageButton
    private lateinit var tVLogin: TextView
    private lateinit var eUPassword: EditText
    private lateinit var cUpassword: EditText
    private lateinit var uName: EditText
    private lateinit var uEmail: EditText
    private var isPasswordVisible = false
    private lateinit var regBtn: Button
    private lateinit var fireBaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        visibleBtn = findViewById(R.id.visibleP)
        visibleBtn1 = findViewById(R.id.visibleP1)
        eUPassword = findViewById(R.id.uPassword)
        cUpassword = findViewById(R.id.conPassword)
        uEmail = findViewById(R.id.uEmail)
        regBtn = findViewById(R.id.loginBtn)
        tVLogin = findViewById(R.id.tVlgin)
        // Initialize Firebase Auth
        fireBaseAuth = Firebase.auth



        visibleBtn.setOnClickListener {
            togglePasswordVisibility(visibleBtn, eUPassword)

        }

        visibleBtn1.setOnClickListener {
            togglePasswordVisibility(visibleBtn1, cUpassword)

        }

        tVLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        regBtn.setOnClickListener {
            registerUser()

        }


    }


    private fun togglePasswordVisibility(imageButton: ImageButton, editText: EditText) {
        isPasswordVisible = !isPasswordVisible

        if (isPasswordVisible) {
            editText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            imageButton.setImageResource(R.drawable.baseline_visibility_24)
        } else {
            editText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            imageButton.setImageResource(R.drawable.baseline_visibility_off_24)
        }

        // Move the cursor to the end of the text
        this.eUPassword.setSelection(this.eUPassword.text.length)
    }


    private fun registerUser() {

        val userE = uEmail.text.toString()
        val userP = eUPassword.text.toString()
        val userCP = cUpassword.text.toString()

        val passwordRegex = Regex("^(?=.*[A-Z])(?=.*[0-9]).{8,}\$")

        if ( userE.isEmpty() || userP.isEmpty() || userCP.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return
        }

        if (!passwordRegex.matches(userP)) {
            Toast.makeText(
                this,
                "Password must be at least 8 characters long and contain at least 1 uppercase letter",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (userP != userCP) {
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
            return
        }

        // Create user with email and password
        fireBaseAuth.createUserWithEmailAndPassword(userE, userP)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "User registration successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
                    // Moved inside the if (task.isSuccessful) block
                    // Sign up success, update UI or proceed to next step

                } else {
                    // Sign up failed, display error message
                    Toast.makeText(
                        this,
                        "Authentication failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


}

