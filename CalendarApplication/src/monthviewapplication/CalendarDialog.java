package monthviewapplication;

import javax.swing.JDialog;
import java.util.Calendar;
import monthviewapplication.MiniCalendar.ChangeTypeEventObject;

/**
 * This dialog is to display the calendar 
 * @author krishna
 */
public class CalendarDialog extends JDialog {
    MiniCalendar miniCalendar;
    Calendar calendar;
    public CalendarDialog(JDialog parent,Calendar calendar) { 
        super(parent);
        this.calendar=calendar;
        this.miniCalendar=new MiniCalendar();
        this.miniCalendar.addChangeTypeListener(new MiniCalendar.ChangedEventListener() {

            public void calendarChanged(ChangeTypeEventObject changeType) {
                if(MiniCalendarChangeType.Day==changeType.type){
                    setCalendar();
                }
            }
        });
        this.setContentPane(this.miniCalendar);
        this.setSize(290,200);
        this.setUndecorated(true);
    }
    /**
     * Sets the calendar selected from minicalendar
     */
    private void setCalendar(){
        this.calendar=this.miniCalendar.getCalendar();
        this.dispose();
    }
    /**
     * Gets the calendar selected by the dialog
     * @return calendar
     */
    public Calendar getCalendar(){
        return this.calendar;
    }
    
}
