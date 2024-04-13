package com.example.codessy


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codessy.databinding.ActivityFaqBinding
import java.util.Locale

class Faq : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<FaqData>()
    private lateinit var adapter: FaqAdapter
    private lateinit var binding:ActivityFaqBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        addDataToList()
        adapter = FaqAdapter(mList)
        recyclerView.adapter = adapter

        binding.backBtn.setOnClickListener {
            finish()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
    }

    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = ArrayList<FaqData>()
            for (i in mList) {
                if (i.title.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setFilteredList(filteredList)
            }
        }
    }

    private fun addDataToList() {
        mList.add(
            FaqData(
                "How do I navigate through different sections of the app?",
                "To navigate through different sections of the app, you can use the intuitive navigation features offered by Codessy. Our app ensures easy and seamless navigation to enhance your user experience."
            )
        )
        mList.add(
            FaqData(
                "What is the purpose of this application?",
                "The aim of Codessy is to provide an enjoyable and stress-free approach to learning JAVA."
            )
        )
        mList.add(
            FaqData(
                "How do i create an account?",
                "Upon launching the app, you will be directed to the login page to authenticate your account. "
            )
        )
        mList.add(
            FaqData(
                "Can I customize my profile?",
                "Yes, Codessy provide the user with the ability to edit their user name, user bio and edit their profile pic"
            )
        )
        mList.add(
            FaqData(
                "What features does Codessy offer?",
                "Codessy provides learning features such as Learn where user can learn through slides regarding JAVA basic concept, it also provides Quiz and Code tips where user can test their java knowledge by answering the quiz and user can see code snippet in JAVA structures "

            )
        )
        mList.add(
            FaqData(
                "Do Codessy offers any subscription plan?",
                "No, Codessy does not offer any subcription plan to the user, as it is a small project and offers basic JAVA learning journey "
            )
        )
        mList.add(
            FaqData(
                "What coding language can be learn in Codessy?",
                "JAVA, Codessy only offers basic JAVA concept and learning path "
            )
        )
        mList.add(
            FaqData(
                "Can i use Codessy online?",
                "No, Codessy doesn't support online use at the moment, You need an active internet connection to use Codessy"
            )
        )
    }
}