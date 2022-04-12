package br.com.jpstudent.quizcard.model.database

import androidx.room.*
import br.com.jpstudent.quizcard.domain.entities.Card
import br.com.jpstudent.quizcard.model.entity.CardModel
import br.com.jpstudent.quizcard.model.entity.DisciplineModel


@Dao
interface CardDao {

    @Query("select * from CardModel where idDiscipline in (:idDiscipline)")
    fun getCardsByDiscipline(idDiscipline : Int) : MutableList<CardModel>

    @Query("select * from CardModel where id in (:idCard)")
    fun getCardById(idCard : Int) : CardModel

    @Insert
    fun addCard(cardModel : CardModel)

    @Delete
    fun removeCard(cardModel: CardModel)

    @Delete
    fun removeCards(cards: MutableList<CardModel>)

    @Update
    fun updateCard(cardModel : CardModel)

}