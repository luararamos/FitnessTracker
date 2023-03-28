package co.tiagoaguiar.fitnesstracker

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.fitnesstracker.adapter.ListCalcAdapter
import co.tiagoaguiar.fitnesstracker.model.Calc

class ListCalcActivity : AppCompatActivity() {
    private lateinit var rvSimple: RecyclerView

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_calc)

        val type =
            intent?.extras?.getString("type") ?: throw IllegalAccessException("type not found")

        val result = mutableListOf<Calc>()

        val adapter = ListCalcAdapter(result) { id ->

            AlertDialog.Builder(this)
                .setTitle(R.string.what_do_you_want_to_do)
                .setPositiveButton(R.string.edit) { dialog, which ->
                    dialog.cancel()
                }
                .setNegativeButton(R.string.delete) { dialog, which ->
                }
                .create()
                .show()

        }
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


}