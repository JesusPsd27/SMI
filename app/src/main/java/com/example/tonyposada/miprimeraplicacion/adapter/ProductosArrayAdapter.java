package com.example.tonyposada.miprimeraplicacion.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import com.example.tonyposada.miprimeraplicacion.Inteface.OnNewTotalListener;
import com.example.tonyposada.miprimeraplicacion.R;
import com.example.tonyposada.miprimeraplicacion.entidades.Producto;

import org.w3c.dom.Text;

public class ProductosArrayAdapter extends ArrayAdapter<Producto> {

    private Context mContext;
    private List<Producto> productosList = new ArrayList<>();
    public double total;
    private OnNewTotalListener mListener;

    public ProductosArrayAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes List<Producto> list, OnNewTotalListener listener) {
        super(context, 0 , list);
        mContext = context;
        productosList = list;
        mListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        View contenido = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.product_list_venta,parent,false);

        final Producto currentProduct = productosList.get(position);
        final int posicion = position;

        if (contenido == null)
            contenido = LayoutInflater.from(mContext).inflate(R.layout.contenido_venta,parent,false);


        TextView productName = listItem.findViewById(R.id.productName);
        TextView productPrice = listItem.findViewById(R.id.productPrice);
        TextView total = contenido.findViewById(R.id.total);

        StringBuffer sb = new StringBuffer();
        sb.append(currentProduct.getPrecio());
        String numberAsString = sb.toString();

        productName.setText(currentProduct.getNombre());
        productPrice.setText(numberAsString);
        getTotal();
        return listItem;
    }

    public void getTotal(){
        double totalT = 0;
        for(int i = 0; i<productosList.size(); i++){
            totalT += productosList.get(i).precio;
        }
        this.total = totalT;
    }
}
