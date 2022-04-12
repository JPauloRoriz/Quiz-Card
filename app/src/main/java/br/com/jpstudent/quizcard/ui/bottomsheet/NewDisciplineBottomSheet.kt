package br.com.jpstudent.quizcard.ui.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.jpstudent.quizcard.R
import br.com.jpstudent.quizcard.databinding.BottomSheetNewDisciplineBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewDisciplineBottomSheet : BottomSheetDialogFragment() {
    var clickSave: ((String) -> Unit)? = null
    private lateinit var binding: BottomSheetNewDisciplineBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetTheme)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetNewDisciplineBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialog.behavior.skipCollapsed = true
        dialog.behavior.isHideable = true
        dialog.behavior.isDraggable = true
        return dialog
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListners()
    }

    private fun setupListners() {
        binding.btnAddDiscipline.setOnClickListener { clickSave?.invoke(binding.edtNameDiscipline.text.toString())
        binding.edtNameDiscipline.text?.clear()}
    }


    companion object {
        fun newInstance() = NewDisciplineBottomSheet()
    }
}