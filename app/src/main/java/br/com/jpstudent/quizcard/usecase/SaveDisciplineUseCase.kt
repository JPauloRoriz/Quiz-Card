package br.com.jpstudent.quizcard.usecase

import android.content.Context
import br.com.jpstudent.quizcard.R
import br.com.jpstudent.quizcard.domain.entities.Discipline
import br.com.jpstudent.quizcard.repository.DisciplineRepository
import br.com.jpstudent.quizcard.usecase.exceptions.SaveDisciplineException


class SaveDisciplineUseCase(
    private val context : Context,
    private val repository: DisciplineRepository
    ) {

    fun saveDiscipline(nameDiscipline: String) {
        when {
            nameDiscipline.isEmpty() -> {
                throw SaveDisciplineException(context.getString(R.string.name_discipline))
            }
            repository.discplineIsExisting(nameDiscipline) -> {
                throw SaveDisciplineException(context.getString(R.string.discipline_existing))
            }
            else -> {
                repository.addDiscipline(Discipline(name = nameDiscipline))
            }
        }
    }


}