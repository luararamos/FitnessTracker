package co.tiagoaguiar.codelab.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ListCalcActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_calc);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String type = extras.getString("type");
            new Thread(() -> {
                List<Register> registers = SqlHelper.getInstance(this).getRegisterBY(type);
                runOnUiThread(() -> {
                    Log.d("Teste", registers.toString());
                });
            }).start();
        }
    }
}