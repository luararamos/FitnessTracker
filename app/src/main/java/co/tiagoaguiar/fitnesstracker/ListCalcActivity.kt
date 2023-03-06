package co.tiagoaguiar.fitnesstracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.fitnesstracker.adapter.ListCalcAdapter

class ListCalcActivity : AppCompatActivity() {
    private lateinit var rvSimple: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_calc)

        val type = intent?.extras?.getString("type") ?: throw IllegalAccessException("type not found")

        Thread {
            val app = application as App
            val dao = app.db.calcDao()
            val response = dao.getRegisterByType(type)

            runOnUiThread {
                Log.i("Teste", "resposta: $response")
                rvSimple = findViewById(R.id.rv_simple)
                rvSimple.adapter = ListCalcAdapter(response)
                rvSimple.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

            }
        }.start()


    }
}