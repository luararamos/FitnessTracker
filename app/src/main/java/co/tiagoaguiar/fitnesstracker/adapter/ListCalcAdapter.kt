package co.tiagoaguiar.fitnesstracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.fitnesstracker.R
import co.tiagoaguiar.fitnesstracker.model.Calc
import java.text.SimpleDateFormat
import java.util.*

class ListCalcAdapter(
    private val simpleItems: List<Calc>
) : RecyclerView.Adapter<co.tiagoaguiar.fitnesstracker.adapter.ListCalcAdapter.ListCalcViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCalcViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return ListCalcViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListCalcViewHolder, position: Int) {
        val itemCurrent = simpleItems[position]
        holder.bind(itemCurrent)
    }

    override fun getItemCount(): Int {
        return simpleItems.size
    }

    class ListCalcViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Calc) {
            val text: TextView = itemView.findViewById(android.R.id.text1)

            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
            val data = sdf.format(item.createdDate)
            val res = item.res
            text.text = itemView.context.getString(R.string.list_response, res , data )
        }

    }
}