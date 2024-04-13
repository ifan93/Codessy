package com.example.codessy.quiz

data class QuizModel(

    val id: String,
    val title: String,
    val subtitle : String,
    val time :String,
    val questionList :List<Questionmodel>

){
    constructor():this("","","","", emptyList())
}

data class Questionmodel(

    val question : String,
    val options: List<String>,
    val correct:String
){
    constructor():this("", emptyList(),"")
}
