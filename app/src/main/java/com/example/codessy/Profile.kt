 package com.example.codessy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.codessy.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

 // TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var binding: FragmentProfileBinding
    private lateinit var databaseR: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentProfileBinding.inflate(inflater, container,false)
        val view = binding.root

        val intent = Intent(requireContext(), Setting::class.java)


        databaseR = FirebaseDatabase.getInstance().getReference("User")
        val currentUser= FirebaseAuth.getInstance().currentUser
        val uEm = currentUser?.email
        binding.tVUserEmail.text=(uEm)
        val uID= currentUser?.uid


        binding.progressBarP.visibility=View.VISIBLE
        binding.g.visibility=View.VISIBLE
        binding.lay.visibility=View.GONE

        if(uID != null){
            databaseR.child(uID).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Get the user's data
                    val user = snapshot.getValue(Users::class.java)
                    if (user != null) {
                        // Populate the EditText fields with the user's data
                        binding.tVuserNameP.text=(user.userName)
                        binding.tVbio.text=(user.userBio)
                        binding.tVUserEmail.text=(uEm)
                        val profilePicUrl = user.profilePictureUrl

                        // Load profile picture using Glide
                        if (!profilePicUrl.isNullOrEmpty()) {
                            Glide.with(requireContext())
                                .load(profilePicUrl)
                                .into(binding.profileP)
                        }

                        val userUn = user.userName
                        val userBi = user.userBio
                        val userEm = uEm
                        val userPp = user.profilePictureUrl

                        intent.putExtra("userName", userUn)
                        intent.putExtra("userBio", userBi)
                        intent.putExtra("userEmail", uEm)
                        intent.putExtra("userProfilePic", userPp)

                    }
                    binding.progressBarP.visibility=View.GONE
                    binding.g.visibility=View.GONE
                    binding.lay.visibility=View.VISIBLE
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle database error
                    Toast.makeText(requireContext(), "Failed to retrieve user data", Toast.LENGTH_LONG)
                        .show()
                }
            })
        }





        binding.settingBtn.setOnClickListener{

            intent.putExtra("userEmail", uEm)
            startActivity(intent)
        }


        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(username: String, bio: String) =
            Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, username)
                    putString(ARG_PARAM2, bio)
                }
            }
    }
}
