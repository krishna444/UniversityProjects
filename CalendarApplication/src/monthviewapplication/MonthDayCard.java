package monthviewapplication;

import TodoManager.CalendarEvent;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.Timer;

/**
 *
 * @author Admin
 * @author Krishna
 */
public class MonthDayCard extends DayCard<CalendarEvent> implements MouseMotionListener{

    private EventCard lastcard;

    Timer timer;

    //Timer to animate
    Timer animationTimer;
    
    public MonthDayCard(String label, Calendar<CalendarEvent> cal) {
        super(label, cal);
        this.addMouseMotionListener(this);
        timer = new Timer(200,
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                       getParent().repaint();
                    }
                });
        
    }

    public void mouseMoved(MouseEvent e) {
        EventCard card = getEventCardAt(e.getX(), e.getY());
        boolean showtooltip = true;

        if (lastcard != null) {
            if(card != null && card.getEvent().equals(lastcard.getEvent()))
                showtooltip = false;
            lastcard.leftoffset = 0;
            lastcard = null;
        }
        if (card != null) {
            Point p = card.getBounds().getLocation();
            card.leftoffset = 10;
            lastcard = card;
            if(showtooltip)
                showTooltip(card.getEvent().toString(),true, p);
            
        }
        else
        {
            showTooltip("",false,null);
        }
    }

        public void mouseDragged(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

     public void mouseEntered(MouseEvent e) {
         timer.start();
        if (lastcard != null) {
            lastcard.leftoffset = 0;
            lastcard = null;
        }
        if(!getLabel().equals("")){
            Rectangle rectangle=getBounds();
            setBounds(rectangle.x+1, rectangle.y+1, rectangle.width, rectangle.height);
        }
        this.startHoverAnimation();
    }

    @Override
        public void mouseExited(MouseEvent e) {

            if (lastcard != null) {
            lastcard.leftoffset = 0;
            lastcard = null;
        }
            
        if(!getLabel().equals("")){
            Rectangle rectangle=getBounds();
            setBounds(rectangle.x-1, rectangle.y-1, rectangle.width, rectangle.height);            
        }
        timer.stop();
        this.stopHoverAnimation();
        showTooltip("", false, null);
    }

    private int eventEntryHeight = 20;
    private int headerHeight = 20;
    private Rectangle headerRect = null;

    @Override
    public EventCard<CalendarEvent> createEventCard(CalendarEvent event) {

        int x = 0;

        int y = headerHeight + this._addedEvents.size() * eventEntryHeight;

        CalendarEventCard card = new CalendarEventCard(event, new Rectangle(x, y, this.getBounds().width, eventEntryHeight));

        return card;
    }

    @Override
    public int getEventEntryHeight() {
        return eventEntryHeight;
    }

    @Override
    public void setEventEntryHeight(int height) {
        this.eventEntryHeight = height;
    }

    @Override
    public void recomputeEventPositionsOnItemChange() {
        recomputeAllEventsPosition();
    }

    private void recomputeAllEventsPosition() {
        int length = this._addedEvents.size();

        for (int i = 0; i < length; i++) {
            EventCard<CalendarEvent> card = this._addedEvents.get(i);
            card.setBounds(new Rectangle(card.getBounds().x, card.getBounds().y,
                    this.getBounds().width, eventEntryHeight));
        }
    }

    @Override
    public void handleResize() {
        recomputeAllEventsPosition();
    }

    @Override
    public boolean canDrawEvent(CalendarEvent event) {
        return true;
    }

    @Override
    public Rectangle getHeaderBounds() {
        if (headerRect == null) {
            headerRect = new Rectangle(0, 0, this.getWidth(), headerHeight);
        }

        return headerRect;
    }
    
    /**
     * Gets the list of added events
     */
    @Override
    public List<EventCard<CalendarEvent>> getAddedEvents(){
        return this._addedEvents;
    }

    @Override
    public void setHeaderBounds(Rectangle r) {
        headerRect = r;
    }

    @Override
    public void drawDayCard(Graphics g) {
//        Graphics tempG = g.create();
//        tempG.setColor(new Color(125, 178, 0, 123));
//        
//        setBackground(Color.GREEN);

    }


}


