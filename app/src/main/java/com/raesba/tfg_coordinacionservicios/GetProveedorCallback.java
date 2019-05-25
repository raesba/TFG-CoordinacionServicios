package com.raesba.tfg_coordinacionservicios;

public interface GetProveedorCallback {
    void onSuccess(Proveedor proveedor, boolean currentUser);
    void onFailure(String error);
}
