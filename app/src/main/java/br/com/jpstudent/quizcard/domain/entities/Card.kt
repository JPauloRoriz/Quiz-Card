package br.com.jpstudent.quizcard.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Card (
    val id : Int,
    var question: String,
    var answer: String
) : Parcelable