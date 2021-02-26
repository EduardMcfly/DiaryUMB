package com.umb.diaryumb.diary

data class Diary(
    var id: Long?,
    val date: String,
    val affair: String,
    val activity: String,
    var selected: Boolean = false
) {
    fun isSelected(): Boolean {
        return selected
    }
}
