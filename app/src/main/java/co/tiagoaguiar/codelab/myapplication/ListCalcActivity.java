package co.tiagoaguiar.codelab.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.tiagoaguiar.codelab.myapplication.adapter.ImcListAdapter;

public class ListCalcActivity extends AppCompatActivity {
    private RecyclerView rvListCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_calc);

        rvListCalc = findViewById(R.id.list_calc_rv);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String type = extras.getString("type");
            new Thread(() -> {
                List<Register> registers = SqlHelper.getInstance(this).getRegisterBY(type);
                runOnUiThread(() -> {
                    Log.d("Teste", registers.toString());
                    rvListCalc.setLayoutManager(new LinearLayoutManager(this));
                    ImcListAdapter adapter = new ImcListAdapter(registers);
                    rvListCalc.setAdapter(adapter);
                });

            }).start();
        }
    }
}