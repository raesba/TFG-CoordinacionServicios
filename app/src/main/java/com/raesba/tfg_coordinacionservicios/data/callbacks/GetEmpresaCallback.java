package com.raesba.tfg_coordinacionservicios.data.callbacks;

import com.raesba.tfg_coordinacionservicios.data.modelo.user.Empresa;

public interface GetEmpresaCallback {
    void onSuccess(Empresa empresa);
    void onFailure(String error);
}
