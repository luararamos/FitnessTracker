package co.tiagoaguiar.fitnesstracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.fitnesstracker.R
import co.tiagoaguiar.fitnesstracker.adapter.item.OnListClickListener
import co.tiagoaguiar.fitnesstracker.model.Calc
import java.text.SimpleDateFormat
import java.util.*

class ListCalcAdapter(
    private val simpleItems: List<Calc>,
    private val listener: OnListClickListener
) : RecyclerView.Adapter<ListCalcAdapter.ListCalcViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCalcViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calc_item, parent, false)
        return ListCalcViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListCalcViewHolder, position: Int) {
        val itemCurrent = simpleItems[position]
        holder.bind(itemCurrent)
    }

    override fun getItemCount(): Int {
        return simpleItems.size
    }

    inner class ListCalcViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Calc) {
            val text: TextView = itemView.findViewById(R.id.itemTxtCalc)
            val card: CardView = itemView.findViewById(R.id.itemCardview)

            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
            val data = sdf.format(item.createdDate)
            val res = item.res
            text.text = itemView.context.getString(R.string.list_response, res , data )

            text.setOnClickListener {
                AlertDialog.Builder(itemView.getRootView().getContext())
                    .setTitle(R.string.what_do_you_want_to_do)
                    .setPositiveButton(R.string.delete) { dialog, which ->
                        // FIXME: precisamos da posição corrente (adapterPosition) para saber qual item da lista
                        // FIXME: deve ser removido da recyclerview usando o notify do Adapter
                        listener.onClickUpdate(adapterPosition, item)
                        true

                    }
                    // FIXME: usado para delegar a quem estiver implementando a interface (Activity) o evento para
                    // FIXME: editar um item da lista
                    .setNegativeButton(R.string.edit) { dialog, which ->
                        listener.onClickDelete(item.id, item.type)
                    }
                    .create()
                    .show()
            }

        }

    }
}