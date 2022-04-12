package br.com.jpstudent.quizcard.ui.activitys

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import br.com.jpstudent.quizcard.databinding.ActivityMenuMainBinding
import br.com.jpstudent.quizcard.ui.adapter.DisciplinesAdapter
import br.com.jpstudent.quizcard.ui.bottomsheet.NewDisciplineBottomSheet
import br.com.jpstudent.quizcard.viewmodel.MenuMainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuMainActivity : AppCompatActivity() {

    private val bottomSheet by lazy { NewDisciplineBottomSheet() }
    private val viewModel: MenuMainViewModel by viewModel()
    private val adapter by lazy { DisciplinesAdapter() }


    private lateinit var binding: ActivityMenuMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMenuMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.rvDiscipline.adapter = adapter


        setupListeners()
        setupObservers()
    }


    private fun setupListeners() {
        binding.btnNewDiscipline.setOnClickListener {
            viewModel.tapOnAddDiscipline()
        }

        binding.btnStartQuiz.setOnClickListener {
            viewModel.startQuiz(adapter.disciplinesSelected)
        }

        bottomSheet.clickSave = { discipline ->
            viewModel.saveDiscipline(discipline)
        }

        adapter.clickItem = { discipline ->
            viewModel.tapOnDiscipline(discipline)
        }

    }

    private fun setupObservers() {
        viewModel.disciplinesLiveData.observe(this) { disciplines ->
            adapter.refreshList(disciplines)
        }

        viewModel.oppenBottomSheetLiveData.observe(this) {
            bottomSheet.show(supportFragmentManager, null)
        }

        viewModel.successAddDisciplineLiveData.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            bottomSheet.dismiss()

        }

        viewModel.errorAddDisciplineLiveData.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        viewModel.discilplinesIsEmptyLiveData.observe(this) {
            binding.tvDisciplinesEmpty.isVisible = it
        }

        viewModel.tapOnDiscipline.observe(this) { discipline ->
            val intent = Intent(this, EditDisciplineActivity::class.java)
            intent.putExtra(EditDisciplineActivity.DISCIPLINE, discipline)
            startActivityForResult(intent, CODE_REGISTER_DISCIPLINE)
        }

        viewModel.disciplinesSelectedIsEmpty.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.startQuizLiveData.observe(this) { disciplinesSelected ->
            val intent = Intent(this, QuizActivity::class.java)
            intent.putParcelableArrayListExtra(QuizActivity.EXTRA_DISCIPLINES, ArrayList(disciplinesSelected))
            startActivity(intent)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE_REGISTER_DISCIPLINE && resultCode == Activity.RESULT_OK) {
            viewModel.getDisciplines()
        }
    }

    companion object {
        const val CODE_REGISTER_DISCIPLINE = 1
    }
}