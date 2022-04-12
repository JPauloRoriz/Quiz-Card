package br.com.jpstudent.quizcard.usecase

import br.com.jpstudent.quizcard.repository.DisciplineRepository

class GetDisciplineUseCase(private val  repository: DisciplineRepository) {

    fun getDisciplines() = repository.getDisciplines()
}