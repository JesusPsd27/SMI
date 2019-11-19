package com.example.tonyposada.miprimeraplicacion.entidades;

public class Producto {

    private String nombre;
    private String codigo;
    private String categoria;
    private String cantidad;
    public Double precio;


    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() { return precio; }

    public void setPrecio(Double precio) { this.precio = precio; }
}
