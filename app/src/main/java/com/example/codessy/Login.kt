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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException


class Login : AppCompatActivity() {

    private var isPasswordVisible = false
    private lateinit var login : Button
    private lateinit var uEmail: EditText
    private lateinit var eUPassword: EditText
    private lateinit var tSignUp : TextView
    private lateinit var visibleBtn: ImageButton
    private lateinit var fireBaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        uEmail = findViewById(R.id.uEmail)
        eUPassword = findViewById(R.id.uPassword)
        tSignUp= findViewById(R.id.tvSignUp1)
        visibleBtn = findViewById(R.id.visibleP)
        login = findViewById(R.id.loginBtn)

        // Initialize FirebaseAuth instance
        fireBaseAuth = FirebaseAuth.getInstance()

        visibleBtn.setOnClickListener{
            togglePasswordVisibility(visibleBtn,eUPassword)
        }

        tSignUp.setOnClickListener{
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
        login.setOnClickListener{
            signInUser()
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

    private fun signInUser(){
        val userE = uEmail.text.toString()
        val userP = eUPassword.text.toString()


        if ( userE.isEmpty() || userP.isEmpty() ) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return
        }

        fireBaseAuth.signInWithEmailAndPassword(userE, userP)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Sign in successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else {
                    // Sign in failed
                    val errorMessage = task.exception?.message
                    if (errorMessage != null) {
                        // Check specific error codes for wrong email or password
                        when (task.exception) {
                            is FirebaseAuthInvalidUserException -> {
                                // Invalid email
                                Toast.makeText(
                                    this,
                                    "Invalid email address",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            is FirebaseAuthInvalidCredentialsException -> {
                                // Wrong password
                                Toast.makeText(
                                    this,
                                    "Wrong password",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else -> {
                                // Display generic error message for other errors
                                Toast.makeText(
                                    this,
                                    errorMessage,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        // If no specific error message is provided, display a generic error message
                        Toast.makeText(
                            this,
                            "Authentication failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }




    }
}