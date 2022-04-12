package br.com.jpstudent.quizcard.usecase.exceptions

class SaveDisciplineException(val errorMessage : String) : Exception(errorMessage)
class StartQuizException(val errorMessage : String) : Exception(errorMessage)
class SaveCardException(val errorMessage : String) : Exception(errorMessage)
class CardsIsEmptyException(val errorMessage : String) : Exception(errorMessage)
