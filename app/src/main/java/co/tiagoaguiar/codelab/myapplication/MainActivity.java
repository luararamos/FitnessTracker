package co.tiagoaguiar.codelab.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private View btnImc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnImc = findViewById(R.id.button_imc);

        btnImc.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ImcActivity.class);
            startActivity(intent);
        });
    }
}