package monthviewapplication;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *It is the custom class used for putting date time values
 * @author krishna
 */
public class CustomDateTimeField extends JPanel {
    //Calendar
    private Calendar calendar;
       
    
    //Time Spinner
    private JSpinner.DateEditor dateEditor;
    private JTextField dateTextField;
    private JDialog parent;
    

    public CustomDateTimeField(JDialog parent) {        
        this(parent,Calendar.getInstance());
    }    
    
    public CustomDateTimeField(JDialog parent,int year, int month, int day, int hour, int minute){
        this.parent=parent;
        this.calendar=Calendar.getInstance();
        this.calendar.set(year, month, day, hour, minute);  
        this.initialiseComponents();
    }
    
    public CustomDateTimeField(JDialog parent,Calendar calendar){
        this.parent=parent;
        this.calendar=calendar;
        this.initialiseComponents();
    }
    
    
    private void initialiseComponents(){
        super.setLayout(new FlowLayout(FlowLayout.TRAILING, 3, 3));
        this.dateTextField=new JTextField();
        this.dateTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                int locationX=dateTextField.getLocationOnScreen().x;
                int locationY=dateTextField.getLocationOnScreen().y+dateTextField.getHeight();
                setCalendar(showCalendarSelectionDialog(locationX, locationY));                
            }
        });
        this.dateTextField.setEditable(true);
        this.add(this.dateTextField);
        final SpinnerDateModel spinnerModel=new SpinnerDateModel(this.calendar.getTime(), null, null, Calendar.HOUR_OF_DAY);
        JSpinner spinner=new JSpinner(spinnerModel);
        this.dateEditor=new JSpinner.DateEditor(spinner, "HH:mm");
        this.dateEditor.getTextField().setEnabled(true);
        spinner.setEditor(this.dateEditor);
        spinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                handleDateEditorChanged();
            }
        });
        this.add(spinner);
        this.setCalendar(this.calendar);
    }
    
    
    
    
    
    //Gets the year value
    public int getYear(){
        return this.calendar.get(Calendar.YEAR);
    }
    //Gets the month value
    public int getMonth(){
        return this.calendar.get(Calendar.MONTH);        
    }
    
    //Gets the day value
    public int getDay(){
        return this.calendar.get(Calendar.DAY_OF_MONTH);
    }
    //Gets the hour value
    public int getHour(){
        return this.calendar.get(Calendar.HOUR_OF_DAY);
    }
    //Gets the minute value
    public int getMinute(){
        return this.calendar.get(Calendar.MINUTE);
    }
    
    
    //Sets the year value
    public void setYear(int year){
       this.calendar.set(Calendar.YEAR,year);
       this.displayDateInfo();
    }
    //Sets the month value
    public void setMonth(int month){
        this.calendar.set(Calendar.MONTH,month);
        this.displayDateInfo();
    }
    
    //Sets the day value
    public void setDay(int day){
        this.calendar.set(Calendar.DAY_OF_MONTH,day);
        this.displayDateInfo();
    }
    //Sets the hour value
    public void setHour(int hour){
        this.calendar.set(Calendar.HOUR_OF_DAY, hour);
        this.displayDateInfo();
    }
    //Sets the minute value
    public void setMinute(int minute){
        this.calendar.set(Calendar.MINUTE,minute);
        this.displayDateInfo();
    }
    
    /**
     * Gets the calendar
     * @return calendar
     */
    public Calendar getCalendar(){
        return this.calendar;
    }
    /**
     * Sets the calendar
     * @param calendar 
     */
    public void setCalendar(Calendar calendar){
        this.calendar=calendar;
        this.displayDateInfo();
    }
    
    
    private void displayDateInfo(){
       this.dateTextField.setText(new SimpleDateFormat("yyyy/MM/dd").format(this.calendar.getTime()));      
       this.dateEditor.getTextField().setText(new SimpleDateFormat("HH:mm").format(this.calendar.getTime()));
    }

    private void handleDateEditorChanged(){
        String strHour=this.dateEditor.getTextField().getText().substring(0, 2);
        int hour=Integer.parseInt(this.dateEditor.getTextField().getText().substring(0, 2));
        this.calendar.set(Calendar.HOUR,hour);
        int minute=Integer.parseInt(this.dateEditor.getTextField().getText().substring(3, 5));
        this.calendar.set(Calendar.MINUTE,minute);
    }
    
    /**
     * Shows the calendar at the specified position
     * @param x X Position
     * @param y Y Position
     * @return Selected Calendar
     */
    private Calendar showCalendarSelectionDialog(int x,int y){
        CalendarDialog dialog=new CalendarDialog(this.parent,Calendar.getInstance());
        dialog.setModal(true);
        dialog.setAlwaysOnTop(true);
        dialog.setLocation(x, y);
        dialog.setVisible(true);
        return dialog.getCalendar();
    }

}
