package com.example.tonyposada.miprimeraplicacion;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.tonyposada.miprimeraplicacion.entidades.Producto;
import com.example.tonyposada.miprimeraplicacion.entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActualizarActivity extends AppCompatActivity {

    private EditText editTextNP, textViewCt, textViewCan, textViewPrec;
    private TextView textViewCo;
    private Button buttonC, buttonAct, buttonEl;

    private JsonRequest jrq;
    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);

        textViewCt = findViewById(R.id.textViewCt);
        textViewCo = findViewById(R.id.textViewCo);
        editTextNP = findViewById(R.id.editTextNP);
        textViewCan = findViewById(R.id.textViewCan);
        textViewPrec = findViewById(R.id.textViewPrec);

        buttonEl = findViewById(R.id.buttonEl);
        buttonEl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webServiceEliminar();
            }
        });

        buttonC = findViewById(R.id.buttonC);
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultar_usuario();
            }
        });

        buttonAct = findViewById(R.id.buttonAct);
        buttonAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webServiceActualizar();
            }
        });
    }

    private void webServiceEliminar() {
        String url = "https://jesuspsd27.000webhostapp.com/wsJSONEliminar.php?nombre="+editTextNP.getText().toString();

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equalsIgnoreCase("elimina")) {
                    editTextNP.setText("");
                    textViewCt.setText("");
                    textViewCan.setText("");
                    textViewPrec.setText("");
                    textViewCo.setText("Codigo");
                    Toast.makeText(getApplicationContext(), "Se ha eliminado con exito", Toast.LENGTH_SHORT).show();
                } else {
                    if (response.trim().equalsIgnoreCase("noExiste")) {
                        Toast.makeText(getApplicationContext(), "No se encuentra el producto ", Toast.LENGTH_SHORT).show();
                        Log.i("RESPUESTA: ", "" + response);
                    } else {
                        Toast.makeText(getApplicationContext(), "No se ha eliminado ", Toast.LENGTH_SHORT).show();
                        Log.i("RESPUESTA: ", "" + response);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"No se ha podido conectar",Toast.LENGTH_SHORT).show();
            }
        });
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void webServiceActualizar() {
        String url = "https://jesuspsd27.000webhostapp.com/wsJSONActualizar.php?";

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (response.trim().equalsIgnoreCase("actualiza")) {
                    Toast.makeText(getApplicationContext(), "Se ha actualizado con exito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No se ha actualizado ", Toast.LENGTH_SHORT).show();
                    Log.i("RESPUESTA: ", "" + response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"No se ha podido conectar",Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError{
                String nombre=editTextNP.getText().toString();
                String categoria=textViewCt.getText().toString();
                String cantidad=textViewCan.getText().toString();
                String precio=textViewPrec.getText().toString();
                String codigo=textViewCo.getText().toString();

                Map<String,String> parametros = new HashMap<>();
                parametros.put("nombre",nombre);
                parametros.put("categoria",categoria);
                parametros.put("cantidad",cantidad);
                parametros.put("precio",precio);
                parametros.put("codigo",codigo);

                return parametros;
            }
        };
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);
    }

        private void consultar_usuario() {
        String url = "https://jesuspsd27.000webhostapp.com/wsJSONConsulta.php?nombre="+editTextNP.getText().toString();
        jrq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Se ha consultado el producto " + editTextNP.getText().toString(), Toast.LENGTH_SHORT).show();

                Producto miProducto = new Producto();

                JSONArray jsonArray = response.optJSONArray("datos");
                JSONObject jsonObject = null;

                try {
                    jsonObject = jsonArray.getJSONObject(0);
                    miProducto.setCodigo(jsonObject.optString("codigo"));
                    miProducto.setCantidad(jsonObject.optString("cantidad"));
                    miProducto.setCategoria(jsonObject.optString("categoria"));
                    miProducto.setPrecio(jsonObject.optDouble("precio"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                textViewCt.setText(miProducto.getCategoria());
                textViewCan.setText(miProducto.getCantidad());
                textViewCo.setText(miProducto.getCodigo());
                textViewPrec.setText(String.valueOf(miProducto.getPrecio()));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No se encontro el producto "+editTextNP.getText().toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                Log.d("ERROR: ", error.toString());
            }
        });
            VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jrq);
    }
}