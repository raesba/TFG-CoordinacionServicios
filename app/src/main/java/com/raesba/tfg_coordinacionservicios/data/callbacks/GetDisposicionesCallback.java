package com.raesba.tfg_coordinacionservicios.data.callbacks;

import java.util.HashMap;

public interface GetDisposicionesCallback {
    void onSuccess(HashMap<String, Boolean> disposiciones);
    void onError(String error);
}
