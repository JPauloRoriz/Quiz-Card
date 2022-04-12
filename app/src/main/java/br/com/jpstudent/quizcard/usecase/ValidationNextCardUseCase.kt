package br.com.jpstudent.quizcard.usecase

import android.content.Context
import br.com.jpstudent.quizcard.R
import br.com.jpstudent.quizcard.domain.entities.Card
import br.com.jpstudent.quizcard.usecase.exceptions.CardsIsEmptyException

class ValidationNextCardUseCase(
   private val context: Context
) {

    fun validationNextCard(cardsSelect: MutableList<Card>){
        if (cardsSelect.isEmpty()) {
            throw CardsIsEmptyException (context.getString(R.string.cards_empty))
        }
    }
}