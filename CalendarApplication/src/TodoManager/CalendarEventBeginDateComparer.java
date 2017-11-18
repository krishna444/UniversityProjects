/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package TodoManager;

import java.util.Comparator;

/**
 *
 * @author Admin
 */
public class CalendarEventBeginDateComparer implements Comparator<CalendarEvent>
{
    public int compare(CalendarEvent o1, CalendarEvent o2)
    {
        return DateController.compareDates(o1.getBeginDate(), o2.getBeginDate());
    }
}
