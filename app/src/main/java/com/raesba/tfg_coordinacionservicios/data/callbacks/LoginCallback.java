package com.raesba.tfg_coordinacionservicios.data.callbacks;

public interface LoginCallback {
    void onLoginSuccess(String uid, int userType);
    void onLoginFailed(String error);
}
