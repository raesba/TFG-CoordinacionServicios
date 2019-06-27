package com.raesba.tfg_coordinacionservicios.data.callbacks;

import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;

public interface GetProveedorCallback {
    void onSuccess(Proveedor proveedor, boolean currentUser);
    void onError(String error);
}
