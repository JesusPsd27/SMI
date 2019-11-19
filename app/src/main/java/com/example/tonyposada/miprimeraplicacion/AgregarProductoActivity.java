package com.example.tonyposada.miprimeraplicacion;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;



public class AgregarProductoActivity extends AppCompatActivity  implements Response.Listener<JSONObject>, Response.ErrorListener {

    private EditText editTextC,editTextN,editTextCt,editTextCan, editTextPrec;
    private RequestQueue rq;
    private JsonRequest jrq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);


        editTextC = findViewById(R.id.editTextC);
        editTextN = findViewById(R.id.editTextN);
        editTextCt = findViewById(R.id.editTextCt);
        editTextCan = findViewById(R.id.editTextCan);
        editTextPrec = findViewById(R.id.editTextPrec);
        Button buttonE = findViewById(R.id.buttonE);
        Button scan_btn = findViewById(R.id.scan_btn);

        rq = Volley.newRequestQueue(getApplicationContext());

        buttonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar_usuario();
            }
        });

        final AppCompatActivity activity = this;
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("                        Escanea el codigo." +
                        "\nActivar linterna: Vol+. Desactivar linterna: Vol-");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(this, "Cancelaste el escaneo", Toast.LENGTH_LONG).show();
            }else {
                editTextC.setText(result.getContents());
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "No se pudó registrar el producto " + editTextN.getText().toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("Error: ", error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(), "Se ha registrado el producto " + editTextN.getText().toString(), Toast.LENGTH_SHORT).show();
        limpiarCajas();
    }

    private void limpiarCajas() {
        editTextC.setText("");
        editTextN.setText("");
        editTextCt.setText("");
        editTextCan.setText("");
        editTextPrec.setText("");
    }

    private void registrar_usuario(){
        String url = "https://jesuspsd27.000webhostapp.com/wsJSONRegistro.php?codigo="+editTextC.getText().toString()+"&nombre="+ editTextN.getText().toString() +
                "&categoria=" + editTextCt.getText().toString()+ "&cantidad=" + editTextCan.getText().toString()+ "&precio=" + editTextPrec.getText().toString();

        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }

}