package co.tiagoaguiar.fitnesstracker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.fitnesstracker.adapter.ListCalcAdapter
import co.tiagoaguiar.fitnesstracker.adapter.item.OnListClickListener
import co.tiagoaguiar.fitnesstracker.model.Calc

class ListCalcActivity : AppCompatActivity(), OnListClickListener {
    private lateinit var rvSimple: RecyclerView
    private lateinit var result: MutableList<Calc>
    private lateinit var adapter: ListCalcAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_calc)

        val type =
            intent?.extras?.getString("type") ?: throw IllegalAccessException("type not found")

        val result = mutableListOf<Calc>()

        val adapter = ListCalcAdapter(result, this)
        rvSimple = findViewById(R.id.rv_list)
        rvSimple.adapter = adapter
        rvSimple.layoutManager = LinearLayoutManager(this)

        Thread {
            val app = application as App
            val dao = app.db.calcDao()
            val response = dao.getRegisterByType(type)

            runOnUiThread {
                Log.i("Teste", "resposta: $response")
                result.addAll(response)
                adapter.notifyDataSetChanged()

            }
        }.start()


    }

    override fun onClickDelete(id: Int, type: String) {
        when(type) {
            "imc" -> {
                val intent = Intent(this, ImcActivity::class.java)
                // FIXME: passando o ID do item que precisa ser atualizado, ou seja, na outra tela
                // FIXME: vamos buscar o item e suas propriedades com esse ID
                intent.putExtra("updateId", id)
                startActivity(intent)
            }
            "tmb" -> {
                val intent = Intent(this, TmbActivity::class.java)
                intent.putExtra("updateId", id)
                startActivity(intent)
            }
        }

    }

    override fun onClickUpdate(position: Int, calc: Calc) {
        // FIXME: pergunta se realmente quer excluir
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.delete_message))
            .setNegativeButton(android.R.string.cancel) { dialog, which ->
            }
            .setPositiveButton(android.R.string.ok) { dialog, which ->
                Thread {
                    val app = application as App
                    val dao = app.db.calcDao()

                    // FIXME: exclui o item que foi clicado com long-click
                    val response = dao.delete(calc)

                    if (response > 0) {
                        runOnUiThread {
                            // FIXME: remove da lista e do adapter o item
                            result.removeAt(position)
                            adapter.notifyItemRemoved(position)
                        }
                    }
                }.start()

            }
            .create()
            .show()
    }


}