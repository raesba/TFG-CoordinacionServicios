package com.raesba.tfg_coordinacionservicios.data.callbacks;

import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Empresa;

public interface GetTransaccionCallback {
    void onSuccess(Transaccion transaccion);
    void onError(String error);
}
