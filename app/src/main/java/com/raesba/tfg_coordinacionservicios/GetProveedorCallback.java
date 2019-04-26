package com.raesba.tfg_coordinacionservicios;

interface GetProveedorCallback {
    void onSuccess(Proveedor proveedor, boolean currentUser);
    void onFailure(String error);
}
