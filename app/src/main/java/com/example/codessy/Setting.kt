package com.example.codessy

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.codessy.databinding.ActivitySettingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class Setting : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseR: DatabaseReference
    private lateinit var storageReference: StorageReference
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var faqB:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val currentUser= FirebaseAuth.getInstance().currentUser
        val uEm = currentUser?.email
        binding.edEmail.setText(uEm)



        faqB=findViewById(R.id.faqBtn)

        faqB.setOnClickListener{
            startActivity(Intent(this@Setting,Faq::class.java))

        }

        setupFirebase()
        setupViews()
        setupButtonClickListeners()
    }

    private fun setupFirebase() {
        auth = FirebaseAuth.getInstance()
        databaseR = FirebaseDatabase.getInstance().getReference("User")
        storageReference = FirebaseStorage.getInstance().reference.child("profile_pictures")
            .child(auth.currentUser?.uid!!)
    }

    private fun setupViews() {
        val userName = intent.getStringExtra("userName")
        val userBio1 = intent.getStringExtra("userBio")
        val userEmail = intent.getStringExtra("userEmail")
        val userPp = intent.getStringExtra("userProfilePic")

        if (userEmail != null && userName != null && userBio1 != null && userPp != null) {
            binding.editUserName.setText(userName)
            binding.edBio.setText(userBio1)
            binding.edEmail.setText(userEmail)
            Glide.with(this).load(userPp).into(binding.profilePic)
        }else{

        }
    }

    private fun setupButtonClickListeners() {
        binding.svBtn.setOnClickListener {
            saveUserProfile()
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.lgOtBtn.setOnClickListener {
            signOutUser()
        }

        binding.deleteBTn.setOnClickListener {
            showDeleteAccountDialog()
        }

        binding.profilePic.setOnClickListener {
            openImagePicker()
        }
    }

    private fun saveUserProfile() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uiD = currentUser?.uid

        val uName = binding.editUserName.text.toString()
        val uEmail = binding.edEmail.text.toString()
        val userBio = binding.edBio.text.toString()

        val user = Users(uiD, uName, uEmail, userBio)

        if (uiD != null) {
            databaseR.child(uiD).setValue(user).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    binding.editUserName.setText(uName)
                    binding.edEmail.setText(uEmail)
                    binding.edBio.setText(userBio)

                    Toast.makeText(this, "Profile updated", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Update profile failed", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun signOutUser() {
        auth.signOut()
        Intent(this@Setting, MainActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }

    private fun showDeleteAccountDialog() {

        val builder = AlertDialog.Builder(this@Setting)
        val inflater = this@Setting.layoutInflater
        val dialogView = inflater.inflate(R.layout.alert_delete_dialog, null)
        builder.setView(dialogView)

        val title = dialogView.findViewById<TextView>(R.id.title)
        val message = dialogView.findViewById<TextView>(R.id.message)

        title.text = "Delete Account Warning"
        message.text =
            "Are you sure you want to delete your account? This action cannot be undone."

        val dialog = builder.create()
        dialog.show()

        // Find the positive button and set custom design
        val positiveButton = dialog.findViewById<Button>(R.id.positiveBtn)
        positiveButton.text = "Yes"
        positiveButton.setOnClickListener {
            // Handle positive button click

            val user = FirebaseAuth.getInstance().currentUser
            user?.delete()?.addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        this,
                        "User account deleted successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    Intent(this@Setting, Login::class.java).also {
                        it.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }

                } else {
                    Toast.makeText(
                        this,
                        "Failed to delete user account",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
            dialog.dismiss()
        }

        // Find the negative button and set its click listener
        val negativeButton = dialog.findViewById<Button>(R.id.negativeBtn)
        negativeButton.text = "No"
        negativeButton.setOnClickListener {
            // Handle negative button click
            dialog.dismiss()
        }
        // Dialog setup
        // ...
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data?.data != null) {

            val imageUri = data.data


            if (imageUri != null) {
                storageReference.putFile(imageUri)
                    .addOnSuccessListener {
                        // Get the URL of the uploaded image
                        storageReference.downloadUrl.addOnSuccessListener { uri ->
                            // Update user's profile with the image URL
                            val imageUrl = uri.toString()
                            databaseR.child(auth.currentUser?.uid!!).child("profilePictureUrl")
                                .setValue(imageUrl)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // Load the profile picture into CircleImageView using an image loading library like Glide
                                        Glide.with(this@Setting)
                                            .load(imageUrl)
                                            .into(binding.profilePic)
                                        Toast.makeText(
                                            this@Setting,
                                            "Profile picture updated",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            this@Setting,
                                            "Failed to update profile picture",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                        }
                    }.addOnFailureListener { e ->
                        Toast.makeText(this, "Upload failed: ${e.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
            }
        }

    }
}


