package com.raesba.tfg_coordinacionservicios.utils;

import android.text.style.BackgroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

public class EventDecorator implements DayViewDecorator {

    private final int color;
    private final CalendarDay day;

    public EventDecorator(int color, CalendarDay day) {
        this.color = color;
        this.day = day;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return this.day.equals(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(15, color));
//        view.addSpan(new BackgroundColorSpan(color));
    }
}
