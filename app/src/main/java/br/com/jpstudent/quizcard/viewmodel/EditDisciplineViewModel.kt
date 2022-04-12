package br.com.jpstudent.quizcard.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.jpstudent.quizcard.R
import br.com.jpstudent.quizcard.domain.entities.Card
import br.com.jpstudent.quizcard.domain.entities.Discipline
import br.com.jpstudent.quizcard.repository.DisciplineRepository

class EditDisciplineViewModel(
    private val repository : DisciplineRepository,
    private val context: Context
) : ViewModel() {


    val deleteDisciplineLiveData = MutableLiveData<String>()
    val goAddCardLiveData = MutableLiveData<Card?>()
    val cardsLiveData = MutableLiveData<MutableList<Card>>()
    val cardsEmptyLiveData = MutableLiveData<Boolean>()



    fun tapOnDelleteDiscipline(discipline: Discipline?) {
        discipline?.let {
            repository.deleteDiscipline(discipline)
            deleteDisciplineLiveData.value = context.getString(R.string.message_discipline_escluded)
        }
    }

    fun tapOnAddCard(card : Card?) {
        goAddCardLiveData.value = card
    }

    fun getCards(discipline: Discipline?){
        val cardsOfDisciplines = repository.getCards(discipline)
        cardsLiveData.value = cardsOfDisciplines
        cardsEmptyLiveData.value = cardsOfDisciplines.isNotEmpty() == false
    }


}