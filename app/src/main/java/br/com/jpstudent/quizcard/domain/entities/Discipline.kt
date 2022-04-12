package br.com.jpstudent.quizcard.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Discipline(
    val id: Int = 0,
    val name: String,
    val cards: List<Card> = listOf()
) : Parcelable