package com.raesba.tfg_coordinacionservicios.data.callbacks;

import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;

import java.util.ArrayList;

public interface GetProveedoresCallback {
    void onSuccess(ArrayList<Proveedor> proveedores);
    void onError(String error);
}
