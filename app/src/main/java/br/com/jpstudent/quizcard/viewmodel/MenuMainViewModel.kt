package br.com.jpstudent.quizcard.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.jpstudent.quizcard.R
import br.com.jpstudent.quizcard.domain.entities.Card
import br.com.jpstudent.quizcard.domain.entities.Discipline
import br.com.jpstudent.quizcard.usecase.SaveDisciplineUseCase
import br.com.jpstudent.quizcard.usecase.GetDisciplineUseCase
import br.com.jpstudent.quizcard.usecase.StartQuizUseCase
import br.com.jpstudent.quizcard.usecase.exceptions.SaveDisciplineException
import br.com.jpstudent.quizcard.usecase.exceptions.StartQuizException

class MenuMainViewModel(
    private val saveUsecase : SaveDisciplineUseCase,
    private val startQuizUseCase : StartQuizUseCase,
    private val getDisicplinesUseCase : GetDisciplineUseCase,
    private val context: Context
) : ViewModel() {


    var discilplinesIsEmptyLiveData = MutableLiveData<Boolean>()
    val oppenBottomSheetLiveData = MutableLiveData<Unit?>()
    var successAddDisciplineLiveData = MutableLiveData<String>()
    val disciplinesLiveData = MutableLiveData<List<Discipline>>()
    var errorAddDisciplineLiveData = MutableLiveData<String>()
    var disciplinesSelectedIsEmpty = MutableLiveData<String>()
    val tapOnDiscipline = MutableLiveData<Discipline>()
    val startQuizLiveData = MutableLiveData<List<Card>>()


    init {
        getDisciplines()
    }

    fun tapOnAddDiscipline() {
        oppenBottomSheetLiveData.value = null
    }

    fun saveDiscipline(nameDiscipline: String) {
        try {
            saveUsecase.saveDiscipline(nameDiscipline)
            successAddDisciplineLiveData.value = context.getString(R.string.discipline_add)
            getDisciplines()
        } catch (exception : SaveDisciplineException){
            errorAddDisciplineLiveData.value = exception.message
        }

    }


    fun getDisciplines() {
        val disciplines = getDisicplinesUseCase.getDisciplines()
        discilplinesIsEmptyLiveData.value = disciplines.isEmpty()

        disciplinesLiveData.value = disciplines
    }

    fun tapOnDiscipline(discipline: Discipline) {
        tapOnDiscipline.value = discipline
    }

    fun startQuiz(disciplinesSelected: MutableList<Discipline>) {
        val cardsSelected = disciplinesSelected.flatMap { discipline -> discipline.cards }
        try {
            startQuizUseCase.startQuizUseCase(disciplinesSelected, cardsSelected)
            startQuizLiveData.value = cardsSelected
        } catch (exception : StartQuizException){
            disciplinesSelectedIsEmpty.value = exception.errorMessage
        }

    }


}