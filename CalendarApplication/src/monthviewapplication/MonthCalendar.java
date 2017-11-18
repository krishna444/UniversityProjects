package monthviewapplication;

import TodoManager.CalendarEvent;
import TodoManager.CustomDate;
import TodoManager.Settings;
import UndoRedo.RemoveCommand;
import UndoRedo.UndoRedoManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class MonthCalendar extends Calendar<CalendarEvent> {

    /**
     * The week days name
     */
    final static String[] weekdays = {"Sunday", "Monday", "Tuesday",
        "Wednesday", "Thursday", "Friday", "Saturday"};
    /**
     * The months initials
     */
    final static String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May",
        "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    
    private int headerHeight = 25;

    private int dayTextHeight = 10;

    public MonthCalendar(CalendarModel<CalendarEvent> model) {

        super(model, 6, 7,
                new CustomDate(Integer.toString(new Date().getYear()) + "." +
                Integer.toString(new Date().getMonth()) + "." + "1,00:00"),
                new CustomDate(Integer.toString(new Date().getYear()) + "." +
                Integer.toString(new Date().getMonth()) + "." + "31,00:00"));
        
        this.setLeftMargin(0);

        this.calendarHeaderPanel.setLayout(new GridLayout(1, 7));

        for(String day : weekdays)
        {
            JLabel label = new JLabel(day);
            label.setBorder(BorderFactory.createEtchedBorder());
            this.calendarHeaderPanel.add(label);
        }

        //DateFormat dateFormat1 = new SimpleDateFormat("yyyy.MM.1,00:00");

        //DateFormat dateFormat2 = new SimpleDateFormat("yyyy.MM.31,00:00");

        java.util.Calendar calendar=java.util.Calendar.getInstance();


        int year = calendar.get(java.util.Calendar.YEAR);
        int month = calendar.get(java.util.Calendar.MONTH);
        loadCalendar(new CustomDate(year + "." + month + "." + "1,00:00"),
                                    new CustomDate(year + "." + month + "." + "31,23:59"));



        //for testing purpose
        //String custom1=dateFormat1.format(calendar.getTime());
        //String custom2=dateFormat2.format(calendar.getTime());

        //loadCalendar(new CustomDate(dateFormat1.format(calendar.getTime())),
          //      new CustomDate(dateFormat2.format(calendar.getTime())));
    }

    @Override
    public void drawCalendarHeader(Graphics g)
    {
        int startx = getCalendarHeader().x;

        int starty = getCalendarHeader().y;

        g.setColor(Color.yellow);

        g.fillRect(startx, starty, this.getWidth(), headerHeight);

        for (String day : weekdays) {

            g.setColor(Color.blue);

            g.drawString(day, startx, g.getFontMetrics().getAscent());
            
            startx += this.getWidth();
        }
    }

    @Override
    public void handleUnhandledUpdate(Observable o, Object arg)
    {
        String actioncommand = arg.toString().toLowerCase();
                if (actioncommand.equalsIgnoreCase("toolbar_undo")) {
                    UndoRedoManager.getInstance().undo(1);
                } else if (actioncommand.equalsIgnoreCase("toolbar_redo")) {
                    UndoRedoManager.getInstance().redo(1);
                } else if (actioncommand.equalsIgnoreCase("toolbar_remove")) {
                    if (this.getSelectedItem() != null) {
                        if (this.getSelectedItem().getSelectedItem() != null) {

                            RemoveCommand<CalendarEvent> removecmd =
                                    new RemoveCommand<CalendarEvent>(
                                    Settings.getRepository(),
                                    (CalendarEvent) this.getSelectedItem().getSelectedItem().
                                    getCalendarEvent());

                            removecmd.execute();

                            UndoRedoManager.getInstance().addUndoableCommand(removecmd);
                        }
                    }
                }
                else if(actioncommand.equalsIgnoreCase("toolbar_edit")){
                    if(this.getSelectedItem()!=null){
                        
                        Settings.showDialog(Settings.getEditTaskView(this.getSelectedItem().getSelectedItem().getCalendarEvent()));

                    }
                    else
                        JOptionPane.showMessageDialog(new JFrame(), "No event selected", "Dialog",JOptionPane.ERROR_MESSAGE);
                }
    }

    @Override
    public Rectangle getCalendarHeader() {
        return new Rectangle(0, 0, this.getWidth(), headerHeight);
    }
    
    
    public List<DayCard<CalendarEvent>> getDayCardList(){
        return this._dayCardList;
    }
    
    @Override
    public void drawCalendarFramework(Graphics g) {
    }

    @Override
    public DayCard<CalendarEvent> getDayCard(CalendarEvent event) {
        int dayOfWeek=this._calendar.get(java.util.Calendar.DAY_OF_WEEK);
        return (DayCard<CalendarEvent>)this.calendarBodyPanel.
                getComponent(dayOfWeek + event.getBeginDate().getDay() - 1);
    }

    @Override
    public DayCard<CalendarEvent> createDayCardFor(int index) {
        DayCard<CalendarEvent> card = new MonthDayCard("", this);
        card.setPreferredSize(new Dimension(this.getWidth()/7,this.getHeight()/7));
        return card;
    }
}