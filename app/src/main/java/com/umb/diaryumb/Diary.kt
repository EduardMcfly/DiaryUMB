package com.umb.diaryumb

data class Diary(
    val id: Int,
    val date: String,
    val affair: String,
    val activity: String,
    var selected: Boolean = true
) {
    fun isSelected(): Boolean {
        return selected
    }
}
