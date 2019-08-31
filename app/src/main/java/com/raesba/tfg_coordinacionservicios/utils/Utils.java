package com.raesba.tfg_coordinacionservicios.utils;

import android.content.Intent;

import com.prolificinteractive.materialcalendarview.CalendarDay;

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

    public static long getDay(String date){
        Calendar fecha = Calendar.getInstance();
        fecha.setTimeInMillis(0);

        String[] dates = date.split("/");

        fecha.set(Calendar.DATE, Integer.parseInt(dates[0]));
        fecha.set(Calendar.MONTH, Integer.parseInt(dates[1])-1);
        fecha.set(Calendar.YEAR, Integer.parseInt(dates[2]));

        fecha.set(Calendar.HOUR, 8);
        fecha.set(Calendar.MINUTE, 0);
        fecha.set(Calendar.SECOND, 0);
        fecha.set(Calendar.MILLISECOND, 0);

        return fecha.getTimeInMillis();
    }

    public static String getDayText(long date){
        Calendar fecha = Calendar.getInstance();
        fecha.setTimeInMillis(date);

        int dia = fecha.get(Calendar.DATE);
        int mes = fecha.get(Calendar.MONTH);
        int anno = fecha.get(Calendar.YEAR);

        return dia + "/" + (mes+1) + "/" + anno;
    }


    public static CalendarDay getCalendarDay(long timemilis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timemilis);

        int dia = calendar.get(Calendar.DATE);
        int mes = calendar.get(Calendar.MONTH);
        int anno = calendar.get(Calendar.YEAR);

        CalendarDay calendarDay = CalendarDay.from(anno, mes + 1, dia);

        return calendarDay;
    }
}
