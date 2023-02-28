package co.tiagoaguiar.fitnesstracker

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ImcActivity : AppCompatActivity() {
    private lateinit var editWheight: EditText
    private lateinit var editHeight: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        editWheight = findViewById(R.id.edit_imc_weight)
        editHeight = findViewById(R.id.edit_imc_height)

        val btnSend: Button = findViewById(R.id.btn_imc_send)
        btnSend.setOnClickListener {
            if (!validate()) {
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // aqui deu sucesso
            val weight = editWheight.text.toString().toInt()
            val height = editHeight.text.toString().toInt()
            val result = calculateImc(weight, height)
            val imcResponseId = imcResponse(result)

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.imc_response, result))
                .setMessage(imcResponseId)
                .setPositiveButton( android.R.string.ok) { dialog, which ->
                    dialog.cancel()
                }
                .create()
                .show()

            // Para esconder o Teclado
            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    // Uma notacao para retornar string de referencia e nao permitir outro como int
    @StringRes
    private fun imcResponse(imc: Double): Int {
        return when {
            imc < 15.0 -> R.string.imc_severely_low_weight
            imc < 16.0 -> R.string.imc_very_low_weight
            imc < 18.5 -> R.string.imc_low_weight
            imc < 25.0 -> R.string.normal
            imc < 30.0 -> R.string.imc_high_weight
            imc < 35.0 -> R.string.imc_so_high_weight
            imc < 40.0 -> R.string.imc_severely_high_weight
            else -> R.string.imc_extreme_weight
        }

    }

    private fun calculateImc(weight: Int, height: Int): Double {
        // peso / (altura * altura)
        return weight / ((height / 100.0) * (height / 100.0))
    }

    private fun validate(): Boolean {
        // nao pode inserir valores nulos / vazio
        // nao pode inserir/comecar com 0
        return (editWheight.text.toString().isNotEmpty()
                && editHeight.text.toString().isNotEmpty()
                && !editWheight.text.toString().startsWith("0")
                && !editHeight.text.toString().startsWith("0"))
    }
}