package com.example.codessy.learn

data class LearnModel(

    val id: String,
    val topic: String,
    val subtitle: String,
    val contentList : List<ContentModel>
) {
    constructor() : this("", "", "", emptyList())

}
data class ContentModel(

    val learnTopic: String,
    val explanation: List<String>
){
    constructor():this("", emptyList())
}
