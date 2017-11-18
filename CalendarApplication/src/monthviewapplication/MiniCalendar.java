package monthviewapplication;

import TodoManager.CustomPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.EventObject;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.util.EventListener;
import javax.swing.event.EventListenerList;
import javax.swing.Box;
import javax.swing.ImageIcon;

/**
 * This is mini calendar control
 * Note: it is month view only
 * @author krishna
 */
public class MiniCalendar extends CustomPanel {
    public final static String[] months = {
        "January" , "February" , "March",
        "April" , "May" , "June",
        "July" , "August" , "September",
        "October" , "November" , "December"
    };
    /** days in each month */
    public final static int dom[] = {
        31, 28, 31, /* jan, feb, mar */
        30, 31, 30, /* apr, may, jun */
        31, 31, 30, /* jul, aug, sep */
        31, 30, 31 /* oct, nov, dec */
    };

    private MiniButton[] dayButtons;
    private Calendar calendar;
    private EventListenerList eventList;

    /**
     * The default constructor
     * which initialises the mini calendar based upon the current date
     */
    public MiniCalendar(){
        this(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH));

    }

    /**
     * Constructor
     * If year and month is previously defined, then this would be a good
     * way to initialise constructor
     * @param year year
     * @param month month
     */
    public MiniCalendar(int year, int month) {
        super.setLayout(new BorderLayout());
        this.dayButtons=new MiniButton[42];
        this.calendar=Calendar.getInstance();
        this.calendar.set(year, month, 1);
        this.eventList=new EventListenerList();

        JPanel headingPanel=new JPanel(new GridLayout(2,1));
        MiniCalendarHeading heading=new MiniCalendarHeading(month,year);
        heading.addCalValueChangedEventListener(new MiniCalendarHeading.CalValueChagedEventListener() {

            public void CalValueChanged(MiniCalendarHeading.NewCalValueEventObject obj) {
                refreshCalendar(obj.year, obj.month);
                fireEvent(MiniCalendarChangeType.Month);
            }
        });
        headingPanel.add(heading,0);
        headingPanel.add(new DayHeading(),1);
        this.add(headingPanel,BorderLayout.NORTH);

        JPanel daysPanel=new JPanel(new GridLayout(0, 7, 2, 2));
        for(int i=0;i<42;i++){
            final int j=i;
            this.dayButtons[i]=new MiniButton(-1);
            this.dayButtons[i].addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    calendar.set(Calendar.DAY_OF_MONTH, dayButtons[j].getDay());
                    fireEvent(MiniCalendarChangeType.Day);
                }
            });
            daysPanel.add(this.dayButtons[i]);
        }
        this.add(daysPanel,BorderLayout.CENTER);
        super.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.refreshCalendar(year, month);
    }

    /**
     * Gets the calendar set by user
     * @return
     */
    public Calendar getCalendar(){
        return this.calendar;
    }



    /**
     * It is the main method that is responsible for refreshing the
     * selected year and month of the calendar
     * @param year year
     * @param month month
     */
    private void refreshCalendar(int year,int month){
        this.calendar.set(year, month, 1);
        int dayOfWeek=this.calendar.get(Calendar.DAY_OF_WEEK);
        int daysInMonth=this.calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        //Reseting the days button
        for(int i=0;i<42;i++){
            this.dayButtons[i].setDay(-1);
        }
        for(int i=dayOfWeek,day=1;day<=daysInMonth;day++,i++){
            this.dayButtons[i-1].setDay(day);//day of week 1 should be button of index 0
        }
    }

    //Event Handling Section to notify others whether there is day changed or month is changed
    /**
     * Adds Event Listener
     * @param listener
     */
    public void addChangeTypeListener(ChangedEventListener listener){
        this.eventList.add(ChangedEventListener.class, listener);
    }

    /**
     * Removes event listener
     * @param listener
     */
    public void removeChangeTypeListener(ChangedEventListener listener){
        this.eventList.remove(ChangedEventListener.class, listener);
    }

    private void fireEvent(MiniCalendarChangeType type){
        Object[] eventList=this.eventList.getListenerList();
        for(int i=0;i<eventList.length;i+=2){
            if(eventList[i]==ChangedEventListener.class){
                ((ChangedEventListener)eventList[i+1]).calendarChanged(new ChangeTypeEventObject(this, type));
            }
        }
    }


    /**
     * Event Object
     */
    class ChangeTypeEventObject extends EventObject{
        MiniCalendarChangeType type;
        public ChangeTypeEventObject(Object source,MiniCalendarChangeType type) {
            super(source);
            this.type=type;

        }
    }

    /**
     * Interface to implement the event handler
     */
    interface ChangedEventListener extends EventListener{
        public void calendarChanged(ChangeTypeEventObject changeType);
    }



}

/**
 * Mini calendar change type
 * @author krishna
 */
enum MiniCalendarChangeType{
    Month,
    Day
}



/**
 * Custom button to display date
 */
class MiniButton extends JButton{
    //day
    private int day;
    //button height
    private int height=20;
    //buton width
    private int width=20;
    //day font
    private Font dayFont;
    //day font metrics
    private FontMetrics dayFontMetrics;
    //Gap Settings
    private int nx=5;
    private int ny=5;

    //Constructor that uses day value to initialise
    MiniButton(int day) {
      this.day=day;
      Dimension dim=new Dimension(this.height,this.width);
      setSize(dim);
      setMinimumSize(dim);
      setPreferredSize(dim);
      this.setEnabled(this.day);

      this.dayFont=new Font("Serif", Font.BOLD, 18);
      this.dayFontMetrics=getFontMetrics(dayFont);
    }

    /**
     * Sets the day
     * @param day value of day
     */
    public void setDay(int day){
        this.day=day;
        this.setEnabled(this.day);
        this.repaint();
    }
    /**
     * Gets the day
     */
    public int getDay(){
        return this.day;
    }

    /**
     * Sets enabling or disabling of button
     * @param day day of the month
     */
    private void setEnabled(int day){
        this.setEnabled(day>0?true:false);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d=(Graphics2D)g;

        //g2d.drawRect(0, 0, width-2, height-2);
        if(this.day>0){
            g2d.setColor(new Color(150,30,175,150));
            int dayWidth=this.dayFontMetrics.stringWidth(Integer.toString(this.day));
            g2d.drawString(Integer.toString(day),nx+(this.width-dayWidth)/2,ny+10);
        }
        else{
            //do nothing
            g2d.setColor(Color.GRAY);
            //g2d.fillRect(0, 0, width, height);
         }

    }

}

/*
 * This is custom control to display day heading
 */
class DayHeading extends JPanel{
    String[] daysOfWeek={"SUN","MON","TUES","WED","THU","FRI","SAT"};
    public DayHeading() {
        setLayout(new GridLayout(0,7,2,2));
        for(String day:daysOfWeek){
            add(new CustomDay(day));
        }
    }

    /**
     * Custom day
     */  
    private class CustomDay extends JLabel{
        //day
        String day;
        //Font
        Font dayFont=null;
        FontMetrics dayMetrics=null;

        /*
         * Constructor
         */
        public CustomDay(String day) {
            super(day);
            this.day=day;
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
       }
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2d=(Graphics2D)g.create();
        }
    }
}

/**
 *
 * @author krishna
 */
class MiniCalendarHeading extends JPanel{
    //Month Property
    static int month;
    //Year Property
    static int year;

   //Left year arrow button
    NavigatorButton leftYearButton;
    //Left month arrow button
    NavigatorButton leftMonthButton;
    //right year arrow button
    NavigatorButton rightYearButton;
    //right year arrow button
    NavigatorButton rightMonthButton;
    //month
    JLabel monthLabel;
    //year
    JLabel yearLabel;

    //right arrow button
    String rightArrowLocation;
    String rightDoubleArrowLocation;
    String leftArrowLocation;
    String leftDoubleArrowLocation;



    EventListenerList eventList;



    public MiniCalendarHeading(int month,int year) {
        //Assign or create data
        MiniCalendarHeading.month=month;
        MiniCalendarHeading.year=year;
        this.eventList=new EventListenerList();
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        //Left Year Button
        this.leftYearButton=new NavigatorButton("./resources/previous.png");
        this.leftYearButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                MiniCalendarHeading.year=MiniCalendarHeading.year-1;
                fireEvent(new NewCalValueEventObject(this,MiniCalendarHeading.year, MiniCalendarHeading.month));
            }
        });
        this.add(this.leftYearButton);

        //Left Month Button
        this.leftMonthButton=new NavigatorButton("./resources/left.png");
        this.leftMonthButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
               MiniCalendarHeading.month=MiniCalendarHeading.month-1;
               fireEvent(new NewCalValueEventObject(this,MiniCalendarHeading.year, MiniCalendarHeading.month));
            }
        });

        this.add(this.leftMonthButton);

        this.monthLabel=new JLabel("September");
        Color c=new Color(200, 0, 232, 187);
        this.monthLabel.setForeground(c);
        this.add(this.monthLabel);



        this.add(Box.createHorizontalStrut(3));

        this.yearLabel=new JLabel("2011");
        this.yearLabel.setForeground(new Color(0, 200, 245, 225));
        this.add(yearLabel);

        this.add(Box.createHorizontalGlue());

        this.rightMonthButton=new NavigatorButton("./resources/right.png");
        //this.rightMonthButton.setBackground(new Color(12, 12, 12, 0));
        //this.rightMonthButton.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        this.rightMonthButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                MiniCalendarHeading.month=MiniCalendarHeading.month+1;
                fireEvent(new NewCalValueEventObject(this,MiniCalendarHeading.year, MiniCalendarHeading.month));
            }
        });
        this.add(this.rightMonthButton);

        this.rightYearButton=new NavigatorButton("./resources/next.png");
        this.rightYearButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                MiniCalendarHeading.year=MiniCalendarHeading.year+1;
                fireEvent(new NewCalValueEventObject(this,MiniCalendarHeading.year, MiniCalendarHeading.month));
            }
        });
        this.add(this.rightYearButton);

        this.displayCalendarInfo();
    }

    /**
     * This displays the date and monthvalues
     */
    private void displayCalendarInfo(){
        Calendar cal=Calendar.getInstance();
        cal.set(MiniCalendarHeading.year, MiniCalendarHeading.month, 1);
        this.monthLabel.setText(new SimpleDateFormat("MMMMM").format(cal.getTime()));
        this.yearLabel.setText(new SimpleDateFormat("yyyy").format(cal.getTime()));
    }


    //Event Processing
    public void addCalValueChangedEventListener(CalValueChagedEventListener eventListener){
        this.listenerList.add(CalValueChagedEventListener.class,eventListener);
    }

    public void removeCalValueChangedEventListener(CalValueChagedEventListener eventListener){
        this.listenerList.remove(CalValueChagedEventListener.class, eventListener);
    }

    private void fireEvent(NewCalValueEventObject object){
        Object[] listeners=listenerList.getListenerList();
        for(int i=0;i<listeners.length;i+=2){
            if(listeners[i]==CalValueChagedEventListener.class){
                ((CalValueChagedEventListener)listeners[i+1]).CalValueChanged(object);
                displayCalendarInfo();
            }
        }
    }

    /**
     * The instance of this class will contain the informatin about the currently
     * selected year and month
     */
    class NewCalValueEventObject extends EventObject{
        int year;
        int month;
        public NewCalValueEventObject(Object source,int year,int month) {
            super(source);
            this.year=year;
            this.month=month;
        }

        public int getYear(){
            return this.year;
        }
        public int getMonth(){
            return this.month;
        }
    }

    /**
     * Interface for event handling
     */
    interface CalValueChagedEventListener extends EventListener{
        public void CalValueChanged(NewCalValueEventObject obj);
    }





}

class NavigatorButton extends JButton{
    ImageIcon icon;

    public NavigatorButton(String fileLocation) {
        this.icon=new ImageIcon(fileLocation);
        this.setSize(40,20);
        this.setPreferredSize(new Dimension(40,20));
        this.setMinimumSize(new Dimension(40, 20));
        this.setIcon(icon);
        this.setRolloverEnabled(true);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.GRAY);

    }
}




