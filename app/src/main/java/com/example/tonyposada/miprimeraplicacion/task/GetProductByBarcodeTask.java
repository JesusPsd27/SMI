package com.example.tonyposada.miprimeraplicacion.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.example.tonyposada.miprimeraplicacion.entidades.Producto;

public class GetProductByBarcodeTask extends AsyncTask<String, Void, Producto> {
    private Activity activity;

    public GetProductByBarcodeTask(Activity act) {
        this.activity = act;
    }

    @Override
    protected Producto doInBackground(String... barCodes) {
        URL url;
        HttpURLConnection urlConnection = null;
        OkHttpClient client = new OkHttpClient();
        Producto product = new Producto();

        try {
            url = new URL("https://jesuspsd27.000webhostapp.com/wsJSONVenta.php?codigo=" + barCodes[0]);
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            //Toast.makeText(this, "Scanned: " + barCode, Toast.LENGTH_LONG).show();

            try (Response response = client.newCall(request).execute()) {
                //Toast.makeText(this.activity, "Called" + "https://192.168.100.40:10000/api/Productos/scanProduct/" + barCodes[0], Toast.LENGTH_LONG).show();
                JSONObject result = new JSONObject(response.body().string());
                product.setCantidad(String.valueOf(1));
                product.setCodigo(result.getJSONArray("datos").getJSONObject(0).getString("codigo"));
                product.setNombre(result.getJSONArray("datos").getJSONObject(0).getString("nombre"));
                product.setPrecio(result.getJSONArray("datos").getJSONObject(0).getDouble("precio"));
            }
            //Toast.makeText(this.activity, "Scanned: " + barCodes[0], Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            //Toast.makeText(this.activity, "OOPS " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return product;
    }
}
