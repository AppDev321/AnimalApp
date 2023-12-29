package com.example.myapplication.utils

import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat


val String.Companion.empty: String get() = ""
val String.Companion.space: String get() = " "

fun String?.safeGet(): String = this ?: String.empty

fun String.nonAlphabetCharPresent(): Boolean = this.matches("^[a-zA-Z]*$".toRegex()).not()

fun String.removeWhiteSpaces(): String = this.replace("\\s".toRegex(), String.empty)

fun String.Companion.randomString(length: Int): String {
    val allowedChars = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789"
    return (1..length)
        .map { allowedChars.random() }
        .joinToString(String.empty)
}

fun String.removeEmojis(): String {
    val regex = Regex("[\\p{So}]")
    return this.replace(regex, String.empty)
}


fun String.areDigitsOnly() = matches(Regex("[0-9]+"))
fun String?.getSafeString(): String = if (this.isNullOrEmpty()) "0" else this ?: "0"

fun String?.getHtmlSpannedText(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}