package br.com.jpstudent.quizcard.usecase

import android.content.Context
import br.com.jpstudent.quizcard.R
import br.com.jpstudent.quizcard.domain.entities.Card
import br.com.jpstudent.quizcard.domain.entities.Discipline
import br.com.jpstudent.quizcard.usecase.exceptions.StartQuizException

class StartQuizUseCase(
    private val context: Context
) {

    fun startQuizUseCase(disciplinesSelected: MutableList<Discipline>, cardsSelected: List<Card>){
        when {
            disciplinesSelected.isEmpty() -> {
                throw StartQuizException(context.getString(R.string.disciplines_is_empty))
            }
            cardsSelected.isEmpty() -> {
                throw StartQuizException(context.getString(R.string.cards_empty_menu))
            }
        }
    }
}