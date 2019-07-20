package com.raesba.tfg_coordinacionservicios.data.callbacks;

import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;

public interface GetTransaccionCallback {
    void onSuccess(Transaccion transaccion);
    void onError(String error);
}
