package co.tiagoaguiar.codelab.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.tiagoaguiar.codelab.myapplication.adapter.MainAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvMain;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMain = findViewById(R.id.main_rv);

        List<MainItem> mainItems = new ArrayList<>();
        mainItems.add(new MainItem(1, R.drawable.ic_imc, R.string.imc, Color.GREEN));
        mainItems.add(new MainItem(2, R.drawable.ic_baseline_add_reaction_24, R.string.tmb, Color.YELLOW));

        //1 -> Definir o comportamento de exibição do layout da recyclerview (mosaic, grid
        // ou linear(horizontal/vertical))
        rvMain.setLayoutManager(new GridLayoutManager(this, 2));
        MainAdapter adapter = new MainAdapter(mainItems);

        adapter.setListener(id -> {
            switch (id) {
                case 1:
                    startActivity(new Intent(MainActivity.this, ImcActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(MainActivity.this, TmbActivity.class));
                    break;
            }
        });

        rvMain.setAdapter(adapter);
    }


}