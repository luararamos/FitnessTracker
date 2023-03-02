package co.tiagoaguiar.fitnesstracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.fitnesstracker.model.Calc

class SimpleAdapter(
    private val simpleItems: List<Calc>
) : RecyclerView.Adapter<co.tiagoaguiar.fitnesstracker.adapter.SimpleAdapter.SimpleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return SimpleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        val itemCurrent = simpleItems[position]
        holder.bind(itemCurrent)
    }

    override fun getItemCount(): Int {
        return simpleItems.size
    }

    class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Calc) {
            val text: TextView = itemView.findViewById(android.R.id.text1)

            text.text = item.res.toString()
        }

    }
}