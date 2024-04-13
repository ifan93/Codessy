package com.example.codessy

data class FaqData(
    val title: String,
    val desc: String,
    var isExpandable: Boolean = false
)
