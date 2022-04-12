package br.com.jpstudent.quizcard.ui.adapter

import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.com.jpstudent.quizcard.R
import br.com.jpstudent.quizcard.domain.entities.Discipline

class DisciplinesAdapter() : RecyclerView.Adapter<DisciplinesAdapter.DisciplineViewHolder>() {
    private var disciplines: List<Discipline> = listOf()
     var disciplinesSelected: MutableList<Discipline> = mutableListOf()

    var clickItem: ((Discipline) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisciplineViewHolder {
        return DisciplineViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_discipline, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DisciplineViewHolder, position: Int) {
        holder.bind(disciplines[position])
    }

    override fun getItemCount(): Int {
        return disciplines.size
    }

    fun refreshList(listDisciplines: List<Discipline>) {
        this.disciplines = listDisciplines
        notifyDataSetChanged()
    }

    inner class DisciplineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNameDiscipline: TextView by lazy { itemView.findViewById(R.id.tv_name_discipline) }
        private val tvNumberCards: TextView by lazy { itemView.findViewById(R.id.tv_number_cards) }
        private val cardView: CardView by lazy { itemView.findViewById(R.id.cardview_discipline) }

        fun bind(discipline: Discipline) {
            if(disciplinesSelected.contains(discipline)){
                cardView.setCardBackgroundColor(itemView.context.getColor(R.color.card_selected))
            } else {
                cardView.setCardBackgroundColor(itemView.context.getColor(R.color.white))
            }
            tvNameDiscipline.text = discipline.name
            tvNumberCards.text =
                itemView.context.getString(R.string.number_cards, discipline.cards.size.toString())
            itemView.setOnLongClickListener {
               disciplinesSelected.add(discipline)

                notifyItemChanged(adapterPosition)
                false
            }
            itemView.setOnClickListener {
                if(disciplinesSelected.isNotEmpty()){
                    choiceSelectedItem(discipline)
                }else {
                    clickItem?.invoke(discipline)
                }
            }

        }

        private fun choiceSelectedItem(discipline: Discipline) {
            if (disciplinesSelected.contains(discipline)) {
                disciplinesSelected.remove(discipline)
            } else {
                disciplinesSelected.add(discipline)
            }
            notifyItemChanged(adapterPosition)
        }
    }
}