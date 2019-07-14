package com.raesba.tfg_coordinacionservicios.data.callbacks;

public interface OnDefaultCallback<Tipo> {
    void onSuccess(Tipo result);
    void onError(String mensaje);
}
