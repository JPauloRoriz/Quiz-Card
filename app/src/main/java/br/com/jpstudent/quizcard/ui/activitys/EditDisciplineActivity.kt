package br.com.jpstudent.quizcard.ui.activitys

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import br.com.jpstudent.quizcard.R
import br.com.jpstudent.quizcard.databinding.ActivityEditDisciplineBinding
import br.com.jpstudent.quizcard.domain.entities.Discipline
import br.com.jpstudent.quizcard.ui.adapter.QuestionsAdapter
import br.com.jpstudent.quizcard.viewmodel.EditDisciplineViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditDisciplineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditDisciplineBinding
    private val viewModel: EditDisciplineViewModel by viewModel()
    private val adapter by lazy { QuestionsAdapter() }

    private val discipline by lazy { intent.extras?.getParcelable<Discipline>(DISCIPLINE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDisciplineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvQuestion.adapter = adapter
        binding.tvDiscipline.text = discipline?.name

        viewModel.getCards(discipline)

        setupListeners()
        setupObservers()

    }

    private fun setupListeners() {
        binding.btnDelleteDiscupline.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(R.string.atention)
                .setMessage(getString(R.string.certain_delete_discipline, discipline?.name))
                .setPositiveButton(R.string.yes) { dialog, which ->
                    viewModel.tapOnDelleteDiscipline(discipline)
                }
                .setNegativeButton(R.string.not) { dialog, witch ->
                    dialog.dismiss()
                }
                .create()
                .show()

        }
        binding.btnAddCard.setOnClickListener {
            viewModel.tapOnAddCard(null)
        }

        adapter.clickItem = { card ->
            viewModel.tapOnAddCard(card)
        }
    }

    private fun setupObservers() {
        viewModel.deleteDisciplineLiveData.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK)
            finish()
        }

        viewModel.goAddCardLiveData.observe(this) { card ->
            val intent = Intent(this, EditCardActivity::class.java)
            intent.putExtra(EditCardActivity.EXTRA_DISCIPLINE, discipline)
            intent.putExtra(EditCardActivity.EXTRA_CARD, card)
            startActivityForResult(intent, CODE_REGISTER_CARD)
        }

        viewModel.cardsLiveData.observe(this) { cards ->
            adapter.refreshList(cards)
        }

        viewModel.cardsEmptyLiveData.observe(this) {
            binding.tvDisciplineEmpty.isVisible = it
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE_REGISTER_CARD && resultCode == Activity.RESULT_OK) {
            setResult(Activity.RESULT_OK)
            viewModel.getCards(discipline)
        }
    }


    companion object {
        const val DISCIPLINE: String = "discipline"
        const val CODE_REGISTER_CARD = 1
    }
}