package com.raesba.tfg_coordinacionservicios.utils;

import java.util.Calendar;

public class Utils {

    public static long getToday(){
        Calendar ahora = Calendar.getInstance();
        ahora.setTimeInMillis(System.currentTimeMillis());

        int dia = ahora.get(Calendar.DATE);
        int mes = ahora.get(Calendar.MONTH);
        int anno = ahora.get(Calendar.YEAR);

        Calendar hoy = Calendar.getInstance();
        hoy.setTimeInMillis(0);

        hoy.set(Calendar.DATE, dia);
        hoy.set(Calendar.MONTH, mes);
        hoy.set(Calendar.YEAR, anno);

        hoy.set(Calendar.HOUR, 8);
        hoy.set(Calendar.MINUTE, 0);
        hoy.set(Calendar.SECOND, 0);
        hoy.set(Calendar.MILLISECOND, 0);

        return hoy.getTimeInMillis();
    }

    public static long getDay(int anno, int mes, int dia){
        Calendar fecha = Calendar.getInstance();
        fecha.setTimeInMillis(0);

        fecha.set(Calendar.DATE, dia);
        fecha.set(Calendar.MONTH, mes);
        fecha.set(Calendar.YEAR, anno);

        fecha.set(Calendar.HOUR, 8);
        fecha.set(Calendar.MINUTE, 0);
        fecha.set(Calendar.SECOND, 0);
        fecha.set(Calendar.MILLISECOND, 0);

        return fecha.getTimeInMillis();
    }
}
