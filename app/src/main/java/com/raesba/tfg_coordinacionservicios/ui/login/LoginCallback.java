package com.raesba.tfg_coordinacionservicios.ui.login;

public interface LoginCallback {
    void onLoginSuccess(String uid, int userType);
    void onLoginFailed(String error);
}
