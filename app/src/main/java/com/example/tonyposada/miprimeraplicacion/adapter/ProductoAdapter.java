package com.example.tonyposada.miprimeraplicacion.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tonyposada.miprimeraplicacion.R;
import com.example.tonyposada.miprimeraplicacion.entidades.Producto;


import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoHolder>{

    List<Producto> listaProducto;

    public ProductoAdapter(List<Producto> listaProducto){
        this.listaProducto = listaProducto;
    }

    @Override
    public ProductoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.productos_list,parent,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new ProductoHolder(vista);
    }

    @Override
    public void onBindViewHolder(ProductoHolder holder, int position) {
        holder.txtNombre.setText(listaProducto.get(position).getNombre());
        holder.txtCodigo.setText(listaProducto.get(position).getCodigo());
        holder.txtCategoria.setText(listaProducto.get(position).getCategoria());
        holder.txtCantidad.setText(listaProducto.get(position).getCantidad());
        holder.txtPrecio.setText(String.valueOf(listaProducto.get(position).getPrecio()));
    }

    @Override
    public int getItemCount() {
        return listaProducto.size();
    }

    public class ProductoHolder extends RecyclerView.ViewHolder{

        TextView txtNombre, txtCodigo, txtCategoria, txtCantidad, txtPrecio;

        public ProductoHolder(View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtCodigo = itemView.findViewById(R.id.txtCodigo);
            txtCategoria = itemView.findViewById(R.id.txtCategoria);
            txtCantidad = itemView.findViewById(R.id.txtCantidad);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);
        }
    }
}
