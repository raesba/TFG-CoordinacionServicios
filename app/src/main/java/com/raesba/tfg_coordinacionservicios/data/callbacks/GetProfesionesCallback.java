package com.raesba.tfg_coordinacionservicios.data.callbacks;

import java.util.ArrayList;

public interface GetProfesionesCallback {
    void onSuccess(ArrayList<String> profesiones);
    void onError(String error);
}
