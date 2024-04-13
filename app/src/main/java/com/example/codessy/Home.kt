package com.example.codessy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.codessy.code.CodePage
import com.example.codessy.learn.LearnPage
import com.example.codessy.quiz.QuizPage

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        // Inflate the layout for this fragment
        val rootView= inflater.inflate(R.layout.fragment_home, container, false)

        val learnBtn:ImageButton = rootView.findViewById(R.id.learnIBtn)
        val quizP:ImageButton = rootView.findViewById(R.id.quizBtn)
        val codeP:ImageButton = rootView.findViewById(R.id.codeBtn2)


        learnBtn.setOnClickListener{
            val intent = Intent(requireContext(),LearnPage::class.java)
            startActivity(intent)
        }
        quizP.setOnClickListener{
            val intent = Intent(requireContext(),QuizPage::class.java)
            startActivity(intent)
        }
        codeP.setOnClickListener{
            val intent = Intent(requireContext(),CodePage::class.java)
            startActivity(intent)
        }


        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}