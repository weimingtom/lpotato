package com.ora.jsp.tags;

import java.util.*;
import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class MonthCalendarTag extends SimpleTagSupport {
    private Date date;
    private String var;
    private JspFragment padPattern;
    private JspFragment beforePattern;
    private JspFragment afterPattern;
    private JspFragment dayNamePattern;
    private JspFragment weekdayPattern;
    private JspFragment weekendPattern;

    public void setDate(Date  date) {
        this.date = date;
    }
    
    public void setVar(String  var) {
        this.var = var;
    }

    public void setBeforePattern(JspFragment beforePattern) {
        this.beforePattern = beforePattern;
    }
    
    public void setAfterPattern(JspFragment afterPattern) {
        this.afterPattern = afterPattern;
    }
    
    public void setPadPattern(JspFragment padPattern) {
        this.padPattern = padPattern;
    }
    
    public void setDayNamePattern(JspFragment dayNamePattern) {
        this.dayNamePattern = dayNamePattern;
    }
    
    public void setWeekdayPattern(JspFragment weekdayPattern) {
        this.weekdayPattern = weekdayPattern;
    }
    
    public void setWeekendPattern(JspFragment weekendPattern) {
        this.weekendPattern = weekendPattern;
    }
    
    public void doTag() throws JspException, IOException {
        Calendar calendar = new GregorianCalendar();
        int firstDayOfWeek = calendar.getFirstDayOfWeek();

        // Evaluate the day name pattern, if any, once for each week day.
        if (dayNamePattern != null) {
            evalDayNamePattern(calendar, firstDayOfWeek);
        }

        // Set the calendar to the first day of the month.        
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        /* 
         * If padding is needed before the first of the month, evaluate
         * the padding pattern for each day of the previous month in the
         * first week.
         */
        if (padPattern != null) {
            evalPrePadPattern(calendar, firstDayOfWeek);
        }

        // Evaluate the weekday and weekend patterns
        evalDayPatterns(calendar, firstDayOfWeek);

        /*
         * If padding is needed after the last of the month, evaluate
         * the padding pattern for each day of the next month in the
         * last week.
         */
        if (padPattern != null) {
            evalPostPattern(calendar, firstDayOfWeek);
        }
    }

    private void evalDayNamePattern(Calendar calendar, int firstDayOfWeek) 
        throws JspException, IOException {
        if (beforePattern != null) {
            beforePattern.invoke(null);
        }
        for (int i = 0, day = firstDayOfWeek; i < 7; i++, day++) {
            calendar.set(Calendar.DAY_OF_WEEK, day);
            if (var != null) {
                getJspContext().setAttribute(var, calendar.getTime());
            }
            dayNamePattern.invoke(null);
        }
        if (afterPattern != null) {
            afterPattern.invoke(null);
        }
    }

    private void evalPrePadPattern(Calendar calendar, int firstDayOfWeek)  
        throws JspException, IOException {
        // Reset to start of week, possibly in the previous month
        int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, firstDayOfWeek - firstDayOfMonth);

        if (beforePattern != null) {
            beforePattern.invoke(null);
        }

        int padDays = firstDayOfMonth - firstDayOfWeek;
        for (int i = 0; i < padDays; i++) {
            if (var != null) {
                getJspContext().setAttribute(var, calendar.getTime());
            }
            padPattern.invoke(null);
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }
    }

    private void evalDayPatterns(Calendar calendar, int firstDayOfWeek) 
        throws JspException, IOException {

        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int lastDayOfWeek = firstDayOfWeek - 1 == 0 ? 7 : firstDayOfWeek - 1;
        for (int i = 0; i < daysInMonth; i++) {
            if (var != null) {
                getJspContext().setAttribute(var, calendar.getTime());
            }
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            if (day == firstDayOfWeek && beforePattern != null) {
                beforePattern.invoke(null);
            }

            if ((day == Calendar.SATURDAY || day == Calendar.SUNDAY) &&
                weekendPattern != null) {
                weekendPattern.invoke(null);
            }
            else {
                weekdayPattern.invoke(null);
            }

            if (day == lastDayOfWeek && afterPattern != null) {
                afterPattern.invoke(null);
            }            
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    private void evalPostPattern(Calendar calendar, int firstDayOfWeek) 
        throws JspException, IOException {
        while (calendar.get(Calendar.DAY_OF_WEEK) != firstDayOfWeek) {
            if (var != null) {
                getJspContext().setAttribute(var, calendar.getTime());
            }
            padPattern.invoke(null);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        if (afterPattern != null) {
            afterPattern.invoke(null);
        }
    }
}
