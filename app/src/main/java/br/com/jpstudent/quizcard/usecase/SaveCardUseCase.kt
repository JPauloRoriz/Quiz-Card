package br.com.jpstudent.quizcard.usecase

import android.content.Context
import br.com.jpstudent.quizcard.R
import br.com.jpstudent.quizcard.domain.entities.Card
import br.com.jpstudent.quizcard.domain.entities.Discipline
import br.com.jpstudent.quizcard.repository.DisciplineRepository
import br.com.jpstudent.quizcard.usecase.exceptions.SaveCardException

class SaveCardUseCase(
    private val context: Context,
    private val repository: DisciplineRepository
) {

    fun saveCardUseCase(card: Card?, discipline: Discipline?, question: String, answer: String) {
            when {
                question.isEmpty() -> {
                    throw SaveCardException(context.getString(R.string.question_empty))
                }
                answer.isEmpty() -> {
                    throw SaveCardException(context.getString(R.string.answer_empty))
                }
                else -> {
                    repository.addCard(discipline, question, answer)
                }
        }
    }
}