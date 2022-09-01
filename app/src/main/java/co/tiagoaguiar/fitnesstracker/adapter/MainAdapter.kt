package co.tiagoaguiar.fitnesstracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.fitnesstracker.R
import co.tiagoaguiar.fitnesstracker.adapter.item.MainItem

class MainAdapter(private  val mainItems: List<MainItem>) : RecyclerView.Adapter<MainViewHolder>() {
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
}
class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: MainItem) {
        val text = item.textStringId
        val buttonTest: Button = itemView.findViewById(R.id.btn_item)
        buttonTest.setText(item.textStringId)
    }


}