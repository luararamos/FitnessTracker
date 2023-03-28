package co.tiagoaguiar.fitnesstracker.adapter.item

import co.tiagoaguiar.fitnesstracker.model.Calc

interface OnListClickListener {
    fun onClickDelete(id: Int, type: String)
    fun onClickUpdate(position: Int, calc: Calc)
}