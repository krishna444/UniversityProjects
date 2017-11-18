package monthviewapplication;

import TodoManager.CustomDate;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

/**
 * This is an abstraction of a calendar which can be derived to show different
 * kinds of events in the calendar. This class is made generic so that the calendar
 * can be made to supports any kinds of events for e.g. To do task, Appointment,
 * Note etc.
 * @author Rupesh Acharya
 */
public abstract class Calendar<T extends Comparable> extends CalendarPanel implements Observer,
        ISelectedItemView<DayCard<T>> {

    /**
     * The margin to be left from the left side while drawing the calendar panel
     */
    private int leftMargin = 80;
    /**
     * The model for the calendar. This should just have an interface to extract
     * the events to show in the calendar.
     */
    protected CalendarModel<T> _model;
    /**
     * This points to the currently selected EventCard in the calendar.
     */
    protected DayCard<T> _selectedDayCard;
    protected List<DayCard<T>> _dayCardList;
    protected JPanel calendarHeaderPanel;
    protected JPanel calendarBodyPanel;
    protected JPanel calendarLeftPanel;
    private CustomDate dateFrom;

    protected java.util.Calendar _calendar;

    public CustomDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(CustomDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public CustomDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(CustomDate dateTo) {
        this.dateTo = dateTo;
    }
    private CustomDate dateTo;

    /**
     * getter method for the leftMargin.
     * @return Returns the unit that should be left before drawing the calendar panel.
     */
    public int getLeftMargin() {
        return leftMargin;
    }

    /**
     * setter method for the left margin
     * @param leftMargin Sets the leftMargin value.
     */
    public void setLeftMargin(int leftMargin) {
        this.leftMargin = leftMargin;
    }

    /**
     * Returns the calendar data model
     * @return Returns the calendar data model
     */
    public CalendarModel<T> getModel() {
        return _model;
    }

    /**
     * Setter for the calendar data model
     * @param _model Sets the calendar data model
     */
    public void setModel(CalendarModel<T> _model) {
        this._model = _model;
    }

    /**
     * Constructor for creating the Calendar instance.
     * @param model The data model for the calendar
     */
   public Calendar(CalendarModel<T> model, int numberOfRows,
            int numberOfColumns, CustomDate from, CustomDate to) {
        super();

        this._model = model;

        calendarHeaderPanel = new JPanel();
        calendarBodyPanel = new JPanel();
        calendarLeftPanel = new JPanel();

        this.setLayout(new BorderLayout());
        this.add(calendarHeaderPanel, BorderLayout.NORTH);
        this.add(calendarBodyPanel, BorderLayout.CENTER);
        //this.add(calendarLeftPanel, BorderLayout.WEST);

        calendarBodyPanel.setLayout(new GridLayout(numberOfRows, numberOfColumns));

        _dayCardList = new ArrayList<DayCard<T>>();

        this._calendar=new GregorianCalendar();

         for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                DayCard<T> daycard = createDayCardFor(i * numberOfColumns + j);
                daycard.addMouseListener(daycard);
                calendarBodyPanel.add(daycard);
                _dayCardList.add(daycard);
            }
        }

        this.dateFrom = from;
        this.dateTo = to;
    }

    public void loadCalendar(CustomDate from, CustomDate to)
    {
        for(DayCard<T> card: _dayCardList)
        {
            card.clear(true);
        }

        this.dateFrom = from;
        this.dateTo = to;

        this._calendar.set(from.getYear(), from.getMonth(), 1);

        int dayOfWeek=this._calendar.get(java.util.Calendar.DAY_OF_WEEK);
        int daysInMonth=this._calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);

        for(int i=dayOfWeek,day=1;day<=daysInMonth;day++,i++){
            //this._dayCardList.get(i).setLabel(Integer.toString(day));
            this._dayCardList.get(i-1).initialise(Integer.toString(day), this);
        }

        loadEvents();
    }

    public void loadEvents() {
        if (_model != null) {
            for (T event : _model.getEventInstances(
                    getDateFrom(), getDateTo())) {
                DayCard<T> daycard = getDayCard(event);
                if (daycard != null) {
                    daycard.addEvent(event);
                }
            }
            repaint();
        }
    }

    public abstract DayCard<T> createDayCardFor(int index);

    /**
     * This method is called whenever there is any chnage in the model.
     * The calendar observes the model.
     * @param o The model that is changed.
     * @param arg The update argument.
     */
    public void update(Observable o, Object arg) {

        if(o instanceof CalendarModel)
        {
            for (DayCard<T> card : _dayCardList) {
                card.clear(false);
            }
            loadEvents();
        }
        else
        {
            handleUnhandledUpdate(o, arg);
        }
    }

    /**
     * Returns the selected EventCard in the calendar
     * @return
     */
    public DayCard<T> getSelectedItem() {
        if (_selectedDayCard != null) {
            return _selectedDayCard;
        }
        return null;
    }

    public abstract DayCard<T> getDayCard(T event);

    public abstract void drawCalendarHeader(Graphics g);

    public abstract Rectangle getCalendarHeader();

    public abstract void drawCalendarFramework(Graphics g);

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //DRAW CALENDAR HEADER
        drawCalendarHeader(g);

        //DRAW CALENDAR FRAMEWORK IF NECESSARY
        drawCalendarFramework(g);

    }

    public void selectDayCard(DayCard daycard)
    {
        clearSelection();

        if (daycard != null) {
            daycard.setIsSelected(true);
            }

        this._selectedDayCard = daycard;
    }

    public void clearSelection()
    {
        if (this._selectedDayCard != null) {
                this._selectedDayCard.setIsSelected(false);
            }
        this._selectedDayCard = null;
    }

    public void handleUnhandledUpdate(Observable o, Object arg) {
        
    }
}
