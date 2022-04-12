package br.com.jpstudent.quizcard.di

import androidx.room.Room
import br.com.jpstudent.quizcard.model.database.AppDatabase
import br.com.jpstudent.quizcard.repository.DisciplineRepository
import br.com.jpstudent.quizcard.usecase.*
import br.com.jpstudent.quizcard.viewmodel.EditCardViewModel
import br.com.jpstudent.quizcard.viewmodel.EditDisciplineViewModel
import br.com.jpstudent.quizcard.viewmodel.MenuMainViewModel
import br.com.jpstudent.quizcard.viewmodel.QuizViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "database_name"
        ).allowMainThreadQueries().build()
    }

    //DAOS
    factory { get<AppDatabase>().cardDao() }
    factory { get<AppDatabase>().disciplineDao() }

    //REPOSITORIES
    factory { DisciplineRepository(get(), get()) }

    //VIEWMODELS
    viewModel { EditCardViewModel(get(), get(), get()) }
    viewModel { EditDisciplineViewModel(get(), get()) }
    viewModel { MenuMainViewModel(get(), get(), get(), get()) }
    viewModel { QuizViewModel(get(), get()) }

    //USECASES
    single { GetDisciplineUseCase(get()) }
    single { GetCardsUseCase(get()) }
    single { SaveDisciplineUseCase(get(), get()) }
    single { StartQuizUseCase(get()) }
    single { SaveCardUseCase(get(), get()) }
    single { ValidationNextCardUseCase(get()) }
}
