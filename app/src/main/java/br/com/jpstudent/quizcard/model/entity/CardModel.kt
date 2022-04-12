package br.com.jpstudent.quizcard.model.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class CardModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @NonNull @ColumnInfo var idDiscipline : Int = 0,
    @NonNull @ColumnInfo var question: String,
    @NonNull @ColumnInfo var answer: String
)