package br.com.jpstudent.quizcard.ui.activitys

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import br.com.jpstudent.quizcard.databinding.ActivityQuizBinding
import br.com.jpstudent.quizcard.domain.entities.Card
import br.com.jpstudent.quizcard.viewmodel.QuizViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private val cardsSelect by lazy {
        intent.getParcelableArrayListExtra<Card>(
            EXTRA_DISCIPLINES
        )?.toMutableList()
    }
    private val cardFirst by lazy { viewModel.fristCardRandom(cardsSelect) }

    private val viewModel: QuizViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityQuizBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvQuestion.text = cardFirst?.question
        binding.tvAnswer.text = cardFirst?.answer

        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.btnNext.setOnClickListener {
            viewModel.tapOnNext(cardsSelect, cardFirst)
        }

        binding.imgEye.setOnClickListener {
            viewModel.tapOnEye(binding.tvAnswer.isInvisible)
        }

    }

    private fun setupObservers() {
        viewModel.cardsSelectLiveData.observe(this) { card ->
            binding.tvQuestion.text = card.question
            binding.tvAnswer.text = card.answer
            binding.tvAnswer.isInvisible = true
            binding.imgInterrogation.isInvisible = false
        }
        viewModel.cardsIsEmptyLiveData.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            finish()
        }

        viewModel.revealAnswerViewModel.observe(this){
            binding.imgInterrogation.isInvisible = !it
            binding.tvAnswer.isInvisible = it


        }
    }

    companion object {
        const val EXTRA_DISCIPLINES: String = "extra_discipline"
    }
}