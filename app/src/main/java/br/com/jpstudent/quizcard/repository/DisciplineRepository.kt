package br.com.jpstudent.quizcard.repository

import br.com.jpstudent.quizcard.domain.entities.Card
import br.com.jpstudent.quizcard.domain.entities.Discipline
import br.com.jpstudent.quizcard.model.database.CardDao
import br.com.jpstudent.quizcard.model.database.DisciplineDao
import br.com.jpstudent.quizcard.model.entity.CardModel
import br.com.jpstudent.quizcard.model.entity.DisciplineModel

class DisciplineRepository(
    private val disciplineDao: DisciplineDao,
    private val cardDao: CardDao
) {

    fun addDiscipline(discipline: Discipline) {
        disciplineDao.addDiscipline(DisciplineModel(name = discipline.name))
    }

    fun getDisciplines(): List<Discipline> {
        val disciplinesModel = disciplineDao.getDisciplines()
        return disciplinesModel.map { disciplineModel ->
            Discipline(
                disciplineModel.id,
                disciplineModel.name,
                cardDao.getCardsByDiscipline(disciplineModel.id).map { cardModel ->
                    Card(
                        cardModel.id,
                        cardModel.question,
                        cardModel.answer
                    )
                }
            )
        }
    }

    fun deleteDiscipline(discipline: Discipline) {
        cardDao.removeCards(cardDao.getCardsByDiscipline(discipline.id))
        disciplineDao.deleteDiscipline(disciplineDao.getDisciplineById(discipline.id))
    }

    fun discplineIsExisting(nameDiscipline: String): Boolean {
        return disciplineDao.getDisciplines()
            .find { disciplineModel -> disciplineModel.name.equals(nameDiscipline, true) } != null

    }

    fun addCard(discipline: Discipline?, question: String, answer: String) {
        if (discipline != null) {
            cardDao.addCard(
                CardModel(
                    idDiscipline = discipline.id,
                    question = question,
                    answer = answer
                )
            )
        }

    }

    fun getCards(discipline: Discipline?): MutableList<Card> {
        val cards = cardDao.getCardsByDiscipline(discipline?.id ?: return mutableListOf())
        return cards.map { cardModel ->
            Card(
                id = cardModel.id,
                question = cardModel.question,
                answer = cardModel.answer
            )
        }.toMutableList()

    }

    fun removeCard(card: Card) {
        cardDao.removeCard(cardDao.getCardById(card.id))
    }

    fun updateCard(card: Card, question: String, answer: String) {
        val cardDaoEdit = cardDao.getCardById(card.id)
        cardDaoEdit.question = question
        cardDaoEdit.answer = answer
        cardDao.updateCard(cardDaoEdit)
    }
}
