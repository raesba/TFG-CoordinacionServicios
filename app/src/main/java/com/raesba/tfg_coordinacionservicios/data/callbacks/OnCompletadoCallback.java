package com.raesba.tfg_coordinacionservicios.data.callbacks;

public interface OnCompletadoCallback {
    void onFinalizadoCorrectamente(String mensaje);
    void onError(String mensaje);
}
