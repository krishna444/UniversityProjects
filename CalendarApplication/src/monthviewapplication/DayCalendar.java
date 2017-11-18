/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monthviewapplication;

import TodoManager.CalendarEvent;
import TodoManager.CustomDate;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class DayCalendar extends Calendar<CalendarEvent> {

    public DayCalendar(CalendarModel<CalendarEvent> model) {
        super(model, 1,1,
                new CustomDate(Integer.toString(new Date().getYear()) + "." +
                Integer.toString(new Date().getMonth()) + "." + new Date().getDay() + ",00:00"),
                new CustomDate(Integer.toString(new Date().getYear()) + "." +
                Integer.toString(new Date().getMonth()) + "." + new Date().getDay() +",23:59"));
    }

    private int getHourHeightUnit()
    {
        return (this.getHeight() - getCalendarHeader().height) / 24;
    }

    private int getMinuteHeightUnit()
    {
        return (int)getHourHeightUnit() / 60;
    }
    
    private int getEventEndYPosition(CalendarEvent e)
    {
        CustomDate dt = e.getEndDate();
        return dt.getHour() * getHourHeightUnit() + dt.getMinute() * getMinuteHeightUnit();
    }

    private int getEventStartYPosition(CalendarEvent e)
    {
        CustomDate dt = e.getBeginDate();
        return dt.getHour() * getHourHeightUnit() + dt.getMinute() * getMinuteHeightUnit();
    }

    @Override
    public void drawCalendarHeader(Graphics g) {
        
    }

    @Override
    public Rectangle getCalendarHeader() {
        return new Rectangle(0,0,0, 0);
    }

    @Override
    public void drawCalendarFramework(Graphics g) {

        g.drawRect(getCalendarHeader().x,getCalendarHeader().y + getCalendarHeader().height,
                getLeftMargin(),_dayCardList.get(0).getHeight());

        g.setColor(Color.BLUE);

        for(int i = 0; i < 24; i++)
        {
            g.drawString(
                    Integer.toString(i)+ " - " + Integer.toString(i+1),
                    0,
                    getHourHeightUnit() * (i+1) + getCalendarHeader().height);


            g.drawLine(0, i * getHourHeightUnit(), getLeftMargin(), i * getHourHeightUnit());
        }
    }

    @Override
    public DayCard<CalendarEvent> createDayCardFor(int index) {
        return new MonthDayCard("No events!", this);
    }

    @Override
    public DayCard<CalendarEvent> getDayCard(CalendarEvent event) {
        return _dayCardList.get(0);
    }
}