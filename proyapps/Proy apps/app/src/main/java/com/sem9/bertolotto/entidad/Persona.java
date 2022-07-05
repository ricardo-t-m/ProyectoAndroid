package com.sem9.bertolotto.entidad;

public class Persona {
    private  String id;
    private String nombre;
    private int departamento;
    private int celular;
    private String correo;
    private String password;


    public Persona(String id, String nombre, int departamento, int celular, String correo, String password) {
        this.id = id;
        this.nombre = nombre;
        this.departamento = departamento;
        this.celular = celular;
        this.correo = correo;
        this.password = password;
    }
    public Persona(){

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDepartamento() {
        return departamento;
    }

    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
