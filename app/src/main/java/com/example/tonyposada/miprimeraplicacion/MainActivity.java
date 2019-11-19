package com.example.tonyposada.miprimeraplicacion;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button botona;
    private Button botonl;
    private Button botonac;
    private Button botonv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonac = findViewById(R.id.buttonAc);
        botonac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActualizarActivity();
            }
        });

        botonl = findViewById(R.id.buttonL);
        botonl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVerListaActivity();
            }
        });


        botona = findViewById(R.id.buttonA);
        botona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAgregarProductoActivity();
            }
        });

        botonv = findViewById(R.id.buttonVnt);
        botonv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPuntodeVentaActivity();
            }
        });
    }

    private void openActualizarActivity() {
        Intent intent = new Intent(MainActivity.this, ActualizarActivity.class);
        startActivity(intent);
    }

    private void openAgregarProductoActivity(){
        Intent intent = new Intent(MainActivity.this, AgregarProductoActivity.class);
        startActivity(intent);
    }

    private void openVerListaActivity(){
        Intent intent = new Intent(MainActivity.this, VerListaActivity.class);
        startActivity(intent);
    }

    private void openPuntodeVentaActivity(){
        Intent intent = new Intent(MainActivity.this, VentaActivity.class);
        startActivity(intent);
    }

}
