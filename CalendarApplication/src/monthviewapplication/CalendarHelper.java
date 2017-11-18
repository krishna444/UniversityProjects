/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package monthviewapplication;

import TodoManager.CustomDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.freixas.jcalendar.JCalendar;

/**
 *
 * @author Admin
 */
class CalendarHelper
{
    /**
     * This method returns the number of weeks that constitute the specified month.
     * @param year The specific year of interest
     * @param month The specific month of interest
     * @return The total number of weeks in the specified month and year.
     */
    public static int getNumberOfWeek(int year, int month)
    {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(year, month, 1);
        int totalday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int start = cal.get(Calendar.WEEK_OF_YEAR);
        cal.set(year, month, totalday);
        int end = cal.get(Calendar.WEEK_OF_YEAR);
        return end - start + 1;
    }

    /**
     * This returns the week number i.e. first week or second week or third or so on
     * of the specified date
     * @param year year of interest
     * @param month month of interest
     * @param day date of interest
     * @return The week number that the date lies in.
     */
    public static int getWeekNumber(int year, int month, int day)
    {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(year, month, 1);
        int start = cal.get(Calendar.WEEK_OF_YEAR);
        cal.set(year, month, day);
        int end = cal.get(Calendar.WEEK_OF_YEAR);
        return end - start + 1;
    }

    /**
     * This should return which week day is it for the specified date i.e.
     * 1 represetns sunday, 2 monday, 3 tuesday and so on.
     * @param year year of interest
     * @param month month of interest
     * @param day date of interest
     * @return The week day the date specifies.
     */
    public static int getDayInWeekNumber(int year, int month, int day)
    {
        GregorianCalendar cal = new GregorianCalendar(year, month, day);

        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * This computes which day number should be shown in the calendar grid.
     * like if the first day i.e. 1 june is in friday then all first 5 box
     * should be empty and only from the friday it should populate the date.
     * @param year year of interest
     * @param month month of interest
     * @param rownumber the month grid row number
     * @param columnnumber the month grid column number
     * @return
     */
    public static int getDayNumberForGridPosition(int year, int month, int rownumber, int columnnumber)
    {
        GregorianCalendar cal = new GregorianCalendar(year, month, 1);

        int totalday = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        int offset = cal.get(GregorianCalendar.DAY_OF_WEEK)-1;
        System.out.println(offset);

        int value = (rownumber * 7) + (columnnumber + 1) - offset;

        if(value > totalday)
            value = -1;

        return value;
    
    }

    private CustomDate getCustomDate(int year, int month, int day, boolean timeStart)
    {
        String hh = "00";
        String mm = "00";
        if(!timeStart)
        {
            hh = "23";
            mm = "59";
        }

        return new CustomDate(Integer.toString(year) + "." +
                Integer.toString(month) + "." +
                Integer.toString(day) + ","+hh+":"+mm+"");
    }

}
