package co.tiagoaguiar.fitnesstracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.fitnesstracker.R
import co.tiagoaguiar.fitnesstracker.adapter.item.MainItem

class MainAdapter(
    private val mainItems: List<MainItem>,
//    private val onItemClickListener: OnItemClickListener
    private val onItemClickListener: (Int)-> Unit
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    // 1- Qual é o layout XML da célula específica(item)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_item, parent, false)
        return MainViewHolder(view)
    }

    // 2- Disparado toda vez que houver uma rolagem na tela e for necessário trocar o conteúdo
    //da célula
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val itemCurrent = mainItems[position]
        holder.bind(itemCurrent)
    }

    // 3 - Quantas células essa listagem terá
    override fun getItemCount(): Int {
        return mainItems.size
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: MainItem) {
            val img: ImageView = itemView.findViewById(R.id.item_img_icon)
            val name: TextView = itemView.findViewById(R.id.item_txt_name)
            val card: CardView = itemView.findViewById(R.id.item_cardview)

            img.setImageResource(item.drawableId)
            name.setText(item.textStringId)

            card.setOnClickListener {
                //Aqui ele é uma interface
                // onItemClickListener.onClick(item.id)

                // aqui é uma função
                onItemClickListener.invoke(item.id)
            }

            // 3 maneiras de escutar eventos de click usando células (viewholder) activities
            // 1. impl interface
            // 2. objetos anonimos
            // 3. funcional
        }
    }

}