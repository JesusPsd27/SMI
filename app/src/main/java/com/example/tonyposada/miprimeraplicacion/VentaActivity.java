package com.example.tonyposada.miprimeraplicacion;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.os.Handler;
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
import android.widget.ListView;
import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.tonyposada.miprimeraplicacion.entidades.Producto;
import com.example.tonyposada.miprimeraplicacion.adapter.ProductosArrayAdapter;
import com.example.tonyposada.miprimeraplicacion.task.GetProductByBarcodeTask;
import com.example.tonyposada.miprimeraplicacion.Inteface.OnNewTotalListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class VentaActivity extends AppCompatActivity implements OnNewTotalListener, Response.Listener<JSONObject>, Response.ErrorListener {

    private ListView mRecyclerView;
    private Activity mActivity;
    private ProductosArrayAdapter mAdapter;
    private List<Producto> productList = new ArrayList<>();
    private RequestQueue rq;
    private JsonRequest jrq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);

        Button scan_btn_vnt = findViewById(R.id.scan_btn_vnt);
        Button buttonSell = findViewById(R.id.sell);
        mRecyclerView = (ListView) findViewById(R.id.productList);

        rq = Volley.newRequestQueue(getApplicationContext());

        Handler handler = new Handler();
        int delay = 1000; //milliseconds

        mAdapter = new ProductosArrayAdapter(getApplicationContext(), productList, this);
        mRecyclerView.setAdapter(mAdapter);
        setListViewHeightBasedOnChildren(mRecyclerView);

        buttonSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarVenta();
            }
        });

        final AppCompatActivity activity = this;
        scan_btn_vnt.setOnClickListener(new View.OnClickListener() {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        Producto product = new Producto();
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(this, "Cancelaste el escaneo", Toast.LENGTH_LONG).show();
            } else {
            try{
                product = new GetProductByBarcodeTask(this).execute(result.getContents()).get();
            }catch (Exception ex){

            }
            if (product.getCodigo() == null || product.getNombre().equals("")){
                Toast.makeText(this, "No se encontro el producto", Toast.LENGTH_SHORT).show();
            }
                Toast.makeText(this, "Scanned: " + product.getCodigo(), Toast.LENGTH_LONG).show();
                productList.add(product);
                mAdapter.notifyDataSetChanged();
        }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        updateTotal();
    }

    public void updateTotal(){
        TextView total = findViewById(R.id.total);
        double totalQ = mAdapter.total;
        total.setText("Total "+totalQ);
        setListViewHeightBasedOnChildren(mRecyclerView);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    public void onNumberIncremented() {
        updateTotal();
    }

    private void limpiarPantalla() {
        TextView total = findViewById(R.id.total);
        double totalQ = mAdapter.total;
        totalQ=0;
        total.setText("Total "+totalQ);
        setListViewHeightBasedOnChildren(mRecyclerView);
        openMainActivity();
    }

    private void openMainActivity() {
        Intent intent = new Intent(VentaActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "No se pudó registrar la venta.", Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("Error: ", error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(), "Se ha registrado la venta.", Toast.LENGTH_LONG).show();
    }

    private void registrarVenta(){
        TextView total = findViewById(R.id.total);
        double totalQ = mAdapter.total;
        setListViewHeightBasedOnChildren(mRecyclerView);
        String url = "https://jesuspsd27.000webhostapp.com/wsJSONRegistroVenta.php?total="+totalQ;

        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
        limpiarPantalla();
    }
}
