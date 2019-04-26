package com.raesba.tfg_coordinacionservicios;

public interface LoginCallback {
    void onLoginSuccess(String uid, int userType);
    void onLoginFailed(String error);
}
