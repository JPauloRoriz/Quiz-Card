package br.com.jpstudent.quizcard.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.jpstudent.quizcard.R
import br.com.jpstudent.quizcard.domain.entities.Card
import br.com.jpstudent.quizcard.domain.entities.Discipline
import br.com.jpstudent.quizcard.repository.DisciplineRepository
import br.com.jpstudent.quizcard.usecase.SaveCardUseCase
import br.com.jpstudent.quizcard.usecase.exceptions.SaveCardException

class EditCardViewModel(
    private val saveCardUseCase: SaveCardUseCase,
    private val repository: DisciplineRepository,
    private val context: Context
) : ViewModel() {


    val addCardLiveData = MutableLiveData<String>()
    val successLiveData = MutableLiveData<String>()
    val informationCardLiveData = MutableLiveData<Unit?>()
    val sucessDeleteLiveData = MutableLiveData<Unit?>()


    fun tapOnSave(card: Card?, discipline: Discipline?, question: String, answer: String) {
        if (card == null) {
            try {
                saveCardUseCase.saveCardUseCase(card, discipline, question, answer)
                successLiveData.value = context.getString(R.string.card_add)
            } catch (exception: SaveCardException) {
                addCardLiveData.value = exception.errorMessage
            }


        } else {
            repository.updateCard(card, question, answer)
            successLiveData.value = context.getString(R.string.card_update)
        }
    }

    fun editOrCreate(card: Card?) {
        if (card != null) {
            informationCardLiveData.value = null
        }
    }

    fun tapOnDelete(card: Card?) {
        if (card != null) {
            repository.removeCard(card)
            successLiveData.value = context.getString(R.string.card_delete)
            sucessDeleteLiveData.value = null
        }

    }
}