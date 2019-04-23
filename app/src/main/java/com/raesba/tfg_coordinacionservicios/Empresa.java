package com.raesba.tfg_coordinacionservicios;

public class Empresa {

    private int idEmpresa;
    private String email;
    private String password;
    private String razonSocial;
    private String cif;
    private String direccion;
    private String poblacion;
    private String provincia;
    private String telefonoFijo;
    private String movil;

    public Empresa() {
    }

    public Empresa(int idEmpresa, String email, String password, String razonSocial, String cif,
                   String direccion, String poblacion, String provincia, String telefonoFijo,
                   String movil) {

        this.idEmpresa = idEmpresa;
        this.email = email;
        this.password = password;
        this.razonSocial = razonSocial;
        this.cif = cif;
        this.direccion = direccion;
        this.poblacion = poblacion;
        this.provincia = provincia;
        this.telefonoFijo = telefonoFijo;
        this.movil = movil;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }
}
