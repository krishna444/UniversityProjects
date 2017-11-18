package TodoManager;



import TodoManager.CustomDate;
import TodoManager.DateController;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 * DateUI is the class that consists of all input fields for getting Date
 * @author volkan cambazoglu
 */
public class DateUI extends JPanel {

    /**
     * Drop down boxes to select beginning date and time of a task
     */
    private JComboBox month;
    private JComboBox day;
    private JComboBox year;
    private JComboBox hour;
    private JComboBox minute;

    /**
     * Controller between DateUI (view) and Date (model) classes
     */
    private DateController dateController;

    /**
     * Default Constructor of DateUI
     * Initializes DateUI with the current time
     */
    public DateUI(){
        super();
        initDateUI();
        initWithCurrentTime();
        loadDateUI();
    }

    /**
     * Constructor of DateUI
     * Initializes DateUI with the provided Date object
     */
    public DateUI(CustomDate date){
        super();
        initDateUI();
        initWithTaskTime(date);
        loadDateUI();
    }

    /**
     * Initialize UI components
     */
    private void initDateUI(){
        setLayout(new GridLayout(1, 5, 10, 0));

        dateController = new DateController();

        year = new JComboBox(dateController.getYears());
        month = new JComboBox(dateController.getMonths());
        hour = new JComboBox(dateController.getHours());
        minute = new JComboBox(dateController.getMinutes());
    }

    /**
     * Add all of the initialized components to the UI
     */
    private void loadDateUI(){
        add(year);
        add(month);
        add(day);
        add(hour);
        add(minute);
    }

    /**
     * A new task will be created.
     * Initialize date and time with current date and time
     */
    private void initWithCurrentTime(){
    	String thisYear = dateController.getThisYear();
        String thisMonth = dateController.getThisMonth();
        String thisDay = dateController.getThisDay();
        String thisHour = dateController.getThisHour();
        String thisMinute = dateController.getThisMinute();

        year.setSelectedItem(thisYear);
        month.setSelectedItem(thisMonth);
        day = new JComboBox(dateController.getDays(Integer.parseInt(thisMonth),
                                                   Integer.parseInt(thisYear)));
        day.setSelectedItem(thisDay);
        hour.setSelectedItem(thisHour);
        minute.setSelectedItem(thisMinute);
	}

    /**
     * An existing task will be editted.
     * Initialize date and time with provided date and time
     * @param date date object to be used for initializing the UI
     */
    private void initWithTaskTime(CustomDate date){
        String thisYear = "" + date.getYear();
        String thisMonth = "" + date.getMonth();

        year.setSelectedItem(date.getStringOfYear());
        month.setSelectedItem(date.getStringOfMonth());
        day = new JComboBox(dateController.getDays(Integer.parseInt(thisMonth),
                                                   Integer.parseInt(thisYear)));
        day.setSelectedItem(date.getStringOfDay());
        hour.setSelectedItem(date.getStringOfHour());
        minute.setSelectedItem(date.getStringOfMinute());
    }

    /**
     * Getter method of month
     * @return month month that the user has selected
     */
    public String getMonth() {
        return (String) month.getSelectedItem();
    }

    /**
     * Getter method of day
     * @return day day that the user has selected
     */
    public String getDay() {
        return (String) day.getSelectedItem();
    }

    /**
     * Getter method of year
     * @return year year that the user has selected
     */
    public String getYear() {
        return (String) year.getSelectedItem();
    }

    /**
     * Getter method of hour
     * @return hour hour that the user has selected
     */
    public String getHour() {
        return (String) hour.getSelectedItem();
    }

    /**
     * Getter method of minute
     * @return minute minute that the user has selected
     */
    public String getMinute() {
        return (String) minute.getSelectedItem();
    }
}
