package com.raesba.tfg_coordinacionservicios;

import java.io.Serializable;

public class Empresa extends Usuario implements Serializable{

    private String razonSocial;
    private String cif;

    public Empresa() {
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

}
