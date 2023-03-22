package co.tiagoaguiar.fitnesstracker

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.fitnesstracker.adapter.MainAdapter
import co.tiagoaguiar.fitnesstracker.adapter.OnItemClickListener
import co.tiagoaguiar.fitnesstracker.adapter.item.MainItem

class MainActivity : AppCompatActivity() {
    private lateinit var rvMain: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(10000)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        val mainItems = mutableListOf<MainItem>()
        mainItems.add(
            MainItem(
                id = 1,
                drawableId = R.mipmap.ic_woman_foreground,
                textStringId = R.string.label_imc,
                color = Color.GREEN
            )
        )
        mainItems.add(
            MainItem(
                id = 2,
                drawableId = R.mipmap.ic_working_out_foreground,
                textStringId = R.string.label_tmb,
                color = Color.GREEN
            )
        )

        val adapter = MainAdapter(mainItems) { id ->
            when (id) {
                1 -> startActivity(Intent(this@MainActivity, ImcActivity::class.java))
                2 -> startActivity(Intent(this@MainActivity, TmbActivity::class.java))
            }
            Log.i("Teste", "clicou $id!")
        }
        rvMain = findViewById(R.id.rv_main)
        rvMain.adapter = adapter
        //1 -> Definir o comportamento de exibição do layout da recyclerview (mosaic, grid
        // ou linear(horizontal/vertical))
        rvMain.layoutManager = GridLayoutManager(this, 2)


    }

}