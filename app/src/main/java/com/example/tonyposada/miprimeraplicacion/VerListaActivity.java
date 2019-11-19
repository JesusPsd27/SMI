package com.example.tonyposada.miprimeraplicacion;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.tonyposada.miprimeraplicacion.adapter.ProductoAdapter;
import com.example.tonyposada.miprimeraplicacion.entidades.Producto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class VerListaActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    RecyclerView recyclerProductos;
    ArrayList<Producto> listaProductos;
    private RequestQueue rq;
    private JsonRequest jrq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_lista);

        listaProductos = new ArrayList<>();

        recyclerProductos = findViewById(R.id.idRecycler);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recyclerProductos.setHasFixedSize(true);

        rq = Volley.newRequestQueue(getApplicationContext());

        cargarWebService();

    }

    private void cargarWebService() {
        String url = "https://jesuspsd27.000webhostapp.com/wsJSONConsultaLista.php";

        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "No se puede conectar"+error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("Error: ", error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        Producto producto = null;

        JSONArray jsonArray = response.optJSONArray("datos");

        try {
            for (int i=0;i<jsonArray.length();i++) {
                producto = new Producto();
                JSONObject jsonObject = null;
                jsonObject = jsonArray.getJSONObject(i);
                producto.setNombre(jsonObject.optString("nombre"));
                producto.setCodigo(jsonObject.optString("codigo"));
                producto.setCategoria(jsonObject.optString("categoria"));
                producto.setCantidad(jsonObject.optString("cantidad"));
                producto.setPrecio(jsonObject.optDouble("precio"));
                listaProductos.add(producto);
            }
            ProductoAdapter adapter = new ProductoAdapter(listaProductos);
            recyclerProductos.setAdapter(adapter);

        }catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "No se a podido establecer conexion con el servidor" + " " +response,Toast.LENGTH_LONG).show();
        }
    }
}

