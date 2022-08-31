package co.tiagoaguiar.fitnesstracker

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var btnImc: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnImc = findViewById(R.id.btn_imc)

        btnImc.setOnClickListener {
            val intent = Intent(this, ImcActivity::class.java)
            startActivity(intent)
        }

    }
}