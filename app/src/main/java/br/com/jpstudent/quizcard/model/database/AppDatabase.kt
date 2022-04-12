package br.com.jpstudent.quizcard.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.jpstudent.quizcard.model.entity.CardModel
import br.com.jpstudent.quizcard.model.entity.DisciplineModel

@Database(entities = [CardModel::class, DisciplineModel::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun cardDao(): CardDao
    abstract fun disciplineDao(): DisciplineDao

}