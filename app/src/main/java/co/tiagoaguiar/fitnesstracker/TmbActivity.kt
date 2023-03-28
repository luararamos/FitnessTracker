package co.tiagoaguiar.fitnesstracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import co.tiagoaguiar.fitnesstracker.databinding.ActivityTmbBinding
import co.tiagoaguiar.fitnesstracker.model.Calc

class TmbActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTmbBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTmbBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val items = resources.getStringArray(R.array.tmb_lifestyle)
        binding.autoLifestyle.setText(items.first())

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        binding.autoLifestyle.setAdapter(adapter)

        binding.autoLifestyle.setOnClickListener {
            val keyboardService =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboardService.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }

        binding.btnTmbSend.setOnClickListener {
            val weight = binding.editTmbWeight.text.toString()
            val height = binding.editTmbHeight.text.toString()
            val age = binding.editAge.text.toString()

            if (!validate(weight, height, age)) {
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result = calculate(weight.toInt(), height.toInt(), age.toInt())
            val response = tmbResponse(result)

            AlertDialog.Builder(this)
                .setMessage(getString(R.string.tmb_response, response))
                .setPositiveButton(android.R.string.ok) { dialog, which ->
                    dialog.cancel()
                }
                .setNegativeButton(R.string.save) { dialog, which ->
                    Thread {
                        val app = application as App
                        val dao = app.db.calcDao()

                        // FIXME: checa se tem um updateId, se tiver, significa
                        // FIXME: que viemos da tela da lista de itens e devemos
                        // FIXME: editar ao inves de inserir
                        val updateId = intent.extras?.getInt("updateId")
                        if (updateId != null) {
                            dao.update(Calc(id = updateId, type = "tmb", res = response))
                        } else {
                            dao.insert(Calc(type = "tmb", res = response))
                        }

                        runOnUiThread {
                            openListActivity()
                        }
                    }.start()
                }
                .create()
                .show()
        }


    }

    private fun tmbResponse(result: Double): Double {

        val items = resources.getStringArray(R.array.tmb_lifestyle)

        return when (binding.autoLifestyle.text.toString()) {
            items[0] -> result * 1.2
            items[1] -> result * 1.375
            items[2] -> result * 1.55
            items[3] -> result * 1.725
            items[4] -> result * 1.9
            else -> 0.0
        }
    }

    private fun calculate(weight: Int, height: Int, age: Int): Double {
        return 66 + (13.8 * weight) + (5 * height) - (6.8 * age)
    }

    private fun validate(weight: String, height: String, age: String): Boolean {
        return (weight.isNotEmpty()
                && height.isNotEmpty()
                && age.isNotEmpty()
                && !weight.startsWith("0")
                && !height.startsWith("0")
                && !age.startsWith("0"))
    }

    private fun openListActivity() {
        val i = Intent(this, ListCalcActivity::class.java)
        i.putExtra("type", "tmb")
        startActivity(i)
    }
}