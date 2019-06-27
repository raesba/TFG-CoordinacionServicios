package com.raesba.tfg_coordinacionservicios.data.callbacks;

public interface OnCompletadoCallback {
    void onSuccess(String mensaje);
    void onError(String mensaje);
}
