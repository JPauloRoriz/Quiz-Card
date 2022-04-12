package br.com.jpstudent.quizcard.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.jpstudent.quizcard.R
import br.com.jpstudent.quizcard.domain.entities.Card

class QuestionsAdapter : RecyclerView.Adapter<QuestionsAdapter.CardViewHolder>() {

    private var cards: MutableList<Card> = mutableListOf()
    var clickItem: ((Card) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_question,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.binding(cards[position])
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    fun refreshList(listQuestions: MutableList<Card>){
        this.cards = listQuestions
        notifyDataSetChanged()
    }


    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvQuestion: TextView by lazy { itemView.findViewById(R.id.tv_question_item) }


        fun binding(card: Card) {
            tvQuestion.text = card.question

            itemView.setOnClickListener {
                clickItem?.invoke(card)
            }
        }
    }
}