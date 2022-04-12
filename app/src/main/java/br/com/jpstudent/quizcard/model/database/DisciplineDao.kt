package br.com.jpstudent.quizcard.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.jpstudent.quizcard.domain.entities.Discipline
import br.com.jpstudent.quizcard.model.entity.DisciplineModel


@Dao
interface DisciplineDao {

    @Insert
    fun addDiscipline(disceiplineModel: DisciplineModel)

    @Query ("select * from DisciplineModel")
    fun getDisciplines() : MutableList<DisciplineModel>

    @Query("select* from DisciplineModel where id in (:idDiscipline)")
    fun getDisciplineById(idDiscipline: Int) : DisciplineModel

    @Delete
    fun deleteDiscipline(disciplineModel : DisciplineModel)
}