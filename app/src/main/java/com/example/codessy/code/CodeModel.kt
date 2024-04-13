
package com.example.codessy.code

data class CodeModel(

    val id: String,
    val codeTopic: String,
    val codeSubtitle: String,
    val codeContentList : List<CodeContentModel>
) {
    constructor() : this("", "", "", emptyList())

}
data class CodeContentModel(

    val codeSubTopic: String,
    val sourceCode: List<String>
){
    constructor():this("", emptyList())
}

