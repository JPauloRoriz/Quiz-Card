package br.com.jpstudent.quizcard.usecase

import br.com.jpstudent.quizcard.domain.entities.Discipline
import br.com.jpstudent.quizcard.repository.DisciplineRepository

class GetCardsUseCase(private val repository : DisciplineRepository) {
    
    fun getCards(discipline : Discipline) = repository.getCards(discipline)
}