package br.com.jpstudent.quizcard.ui.activitys

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.jpstudent.quizcard.R
import br.com.jpstudent.quizcard.databinding.ActivityEditCardBinding
import br.com.jpstudent.quizcard.domain.entities.Card
import br.com.jpstudent.quizcard.domain.entities.Discipline
import br.com.jpstudent.quizcard.viewmodel.EditCardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditCardActivity : AppCompatActivity() {
    private val viewModel: EditCardViewModel by viewModel()
    private lateinit var binding: ActivityEditCardBinding
    private val discipline by lazy { intent.extras?.getParcelable<Discipline>(EXTRA_DISCIPLINE) }
    private val card by lazy { intent.extras?.getParcelable<Card?>(EXTRA_CARD) }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityEditCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.editOrCreate(card)
        binding.tvEditCardText.text = discipline?.name

        setupListners()
        setupObservers()

    }

    private fun setupListners() {
        binding.btnSave.setOnClickListener {
            viewModel.tapOnSave(card,
                discipline,
                binding.edtQuestion.text.toString(),
                binding.edtAnswer.text.toString()
            )
        }
        binding.btnDellete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.atention))
                .setMessage(getString(R.string.certain_delete_card))
                .setPositiveButton(getString(R.string.yes)){ dialog, which ->
                    viewModel.tapOnDelete(card)
                }
                .setNegativeButton(getString(R.string.not)){ dialog, witch ->
                    dialog.dismiss()
                }
                .create()
                .show()


        }
    }

    private fun setupObservers() {
        viewModel.addCardLiveData.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK)
        }
        viewModel.successLiveData.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK)
            finish()
        }

       viewModel.sucessDeleteLiveData.observe(this){
           setResult(Activity.RESULT_OK)
           finish()
       }


        viewModel.informationCardLiveData.observe(this){
            binding.btnSave.text = getString(R.string.update)
            binding.btnDellete.text = getString(R.string.delete)
            binding.edtAnswer.setText(card?.answer)
            binding.edtQuestion.setText(card?.question)
        }
    }


    companion object {
        const val EXTRA_DISCIPLINE = "discipline"
        const val EXTRA_CARD = "card"
    }
}