package com.raesba.tfg_coordinacionservicios.data.callbacks;

import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;

import java.util.ArrayList;

public interface GetTransaccionesCallback {
    void onSuccess(ArrayList<Transaccion> transacciones);
    void onError(String error);
}
