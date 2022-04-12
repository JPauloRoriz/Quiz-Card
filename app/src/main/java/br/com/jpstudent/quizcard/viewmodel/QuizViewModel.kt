package br.com.jpstudent.quizcard.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.jpstudent.quizcard.R
import br.com.jpstudent.quizcard.domain.entities.Card
import br.com.jpstudent.quizcard.usecase.ValidationNextCardUseCase
import br.com.jpstudent.quizcard.usecase.exceptions.CardsIsEmptyException

class QuizViewModel(
    private val validationNextCard : ValidationNextCardUseCase,
    private val context: Context
) : ViewModel() {
    val cardsSelectLiveData = MutableLiveData<Card>()
    val cardsIsEmptyLiveData = MutableLiveData<String>()
    val revealAnswerViewModel = MutableLiveData<Boolean>()

    fun tapOnNext(cardsSelect: MutableList<Card>?, card: Card?) {
        if (cardsSelect != null) {
            try {
                validationNextCard.validationNextCard(cardsSelect)
                cardsSelect.remove(card)
                cardsSelectLiveData.value = cardsSelect[0]

            } catch (exception : CardsIsEmptyException) {
                cardsIsEmptyLiveData.value = exception.errorMessage
            }


        }
    }

    fun fristCardRandom(cardsSelect: MutableList<Card>?) : Card?{
        if (cardsSelect != null) {
            if (cardsSelect.isEmpty()) {
                cardsIsEmptyLiveData.value = context.getString(R.string.cards_empty)
            }
            else {
                cardsSelect.shuffle()
                return cardsSelect[0]
            }
        }
        return null
    }

    fun tapOnEye(tvAnswer: Boolean) {
        revealAnswerViewModel.value = tvAnswer == false
    }

    val cards = mutableListOf<Card>()

}