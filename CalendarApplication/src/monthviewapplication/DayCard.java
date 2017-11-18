package monthviewapplication;

import Actions.CreateAction;
import Actions.EditAction;
import TodoManager.Settings;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import monthviewapplication.EventCard;
import TodoManager.CalendarEvent;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.JLabel;
import javax.swing.JWindow;

/**
 *
 * @author Admin
 * @author Krishna
 */
public abstract class DayCard<T extends Comparable> extends JPanel
        implements ISelectedItemView<EventCard<T>>,
        MouseListener, MouseMotionListener {
    protected EventCard<T> _selectedEventCard;
    protected List<EventCard<T>> _addedEvents;
    protected String _label = "";
    private boolean _isSelected;
    protected Calendar<T> calendar;
    private Timer animationTimer;
    private Timer hoverTimer;
    private Color originalBkgColor=Color.PINK;
    private Color inactiveColor=Color.LIGHT_GRAY;
    JWindow toolTip;
    JLabel toolTipLabel;

    public String getLabel() {
        return _label;
    }

    public void showTooltip(String string, boolean b, Point location) {
        if(b)
        {
            Point p = this.getLocationOnScreen();
            toolTipLabel.setText(string);
            toolTip.setLocation(new Point(p.x + location.x, p.y + location.y+30));
        }
        toolTip.setVisible(b);
        toolTip.pack();
    }

    public void setLabel(String _label) {
        this._label = _label;
    }

    public DayCard(String label, Calendar<T> cal) {
         this.initialise(label, cal);
    }

    public void initialise(String label,Calendar<T> cal){
        calendar = cal;
        _addedEvents = new ArrayList<EventCard<T>>();
        this._label = label;
        this.setBorder(BorderFactory.createEtchedBorder());
        this.setBackground(this.originalBkgColor);
        this.animationTimer=new Timer(20, new DayCardAnimator());
        this.hoverTimer=new Timer(55, new DayCardHoverAnimator());
        this.addMouseMotionListener(this);


        toolTip = new JWindow(new Frame());
        toolTipLabel = new JLabel();
        toolTip.getContentPane().add(toolTipLabel);

    }

    public void addEvent(T event) {
        EventCard<T> card = createEventCard(event);
        _addedEvents.add(card);
    }

    public void removeEvent(T event) {
        EventCard<T> cardtoremove = null;
        for (EventCard<T> card : _addedEvents) {
            if (card.hasEvent(event)) {
                cardtoremove = card;
            }
        }

        if (cardtoremove != null) {
            if (_selectedEventCard == cardtoremove) {
                _selectedEventCard = null;
            }

            _addedEvents.remove(cardtoremove);

            recomputeEventPositionsOnItemChange();
        }
    }

    public abstract EventCard<T> createEventCard(T event);

    public abstract int getEventEntryHeight();
    
    public abstract List<EventCard<T>> getAddedEvents();

    public abstract void setEventEntryHeight(int height);

    public abstract void recomputeEventPositionsOnItemChange();

    public abstract void handleResize();

    /**
     * Returns the EventCard at the specified x,y location
     * @param x The x position
     * @param y The y position
     * @return The EventCard at the specified location. If there is no any EventCard
     * in the location provided then it returns null.
     */
    public EventCard<T> getEventCardAt(int x, int y) {
        for (EventCard<T> eventcard : _addedEvents) {
            if (eventcard.hitTest(x, y)) {
                return eventcard;
            }
        }
        return null;
    }

    /**
     * This selects the event at the provided location
     * @param x The x location for selection
     * @param y The y location for selection
     */
    public void selectEventCardAt(int x, int y) {
        selectEventCard(getEventCardAt(x, y));
    }

    public void selectEventCard(EventCard<T> eventcard) {
        clearSelection();

        if (eventcard != null) {
            eventcard.setIsSelected(true);
        }

        this._selectedEventCard = eventcard;
    }

    public void clearSelection() {
        if (this._selectedEventCard != null) {
            this._selectedEventCard.setIsSelected(false);
        }
        this._selectedEventCard = null;
    }

    public abstract boolean canDrawEvent(T event);

    /**
     * this is my method
     * @return
     */
    public EventCard<T> getSelectedItem() {
        return _selectedEventCard;
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            calendar.selectDayCard(this);
            this.selectEventCardAt(e.getX(), e.getY());
            this.getParent().repaint();
        } else if (e.getClickCount() == 2) {
            this.selectEventCardAt(e.getX(), e.getY());
            if (this._selectedEventCard != null) {
                EditAction ea = new EditAction("Edit", null, 0, "edit", this);
                ea.actionPerformed(null);
                this.getParent().repaint();
                //repaint();
            } else {
                CreateAction ca = new CreateAction("Create", null, 0, "create", Settings.getCreateTaskView());
                ca.actionPerformed(null);
            }
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    

    public abstract Rectangle getHeaderBounds();

    public abstract void setHeaderBounds(Rectangle r);

    public abstract void drawDayCard(Graphics g);

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        handleResize();
        //this.drawDayCard(g);
        if(this.getLabel().length()>0){
            //this.setBackground(originalBkgColor);  
             this.setBorder(BorderFactory.createEtchedBorder());
        }
        else{
            this.setBackground(inactiveColor);
             this.setBorder(null);
        }
        //draw header
        Font original=g.getFont();
        Font font=new Font("serif", Font.BOLD, 20);
        g.setFont(font);
        g.drawString(this._label, 2, getHeaderBounds().height);
        g.setFont(original);

        //draw events
        for (EventCard<T> card : this._addedEvents) {
            card.drawCard(g);
        }
    }

    void clear(boolean clearDayLabel) {
        _addedEvents.clear();
        this._selectedEventCard = null;
        if (clearDayLabel) {
            this._label = "";
        }
    }

    public void setIsSelected(boolean b) {
        this._isSelected = b;
        if (!b) {
            this.clearSelection();
        }
    }

    public boolean getIsSelected() {
        return this._isSelected;
    }
     /**
      * start animation
      */
     public void startAnimation(){
        this.animationTimer.start();
    }
      /**
      * Stop hover animation
      */
     public void stopAnimation(){
         animationTimer.stop();
         this.setBackground(this.originalBkgColor);
     }
     
     public void startHoverAnimation(){
         this.hoverTimer.start();
     }
    
     public void stopHoverAnimation(){
         this.hoverTimer.stop();
         this.setBackground(this.originalBkgColor);
     }
    
     /**
      * Day Card Animator
      */
    private class DayCardAnimator implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            Color color=getBackground();
            if(color.getRed()<252){
                setBackground(new Color(color.getRed()+2, color.getGreen(), color.getBlue()));            
            }
            if(color.getBlue()>2){
                setBackground(new Color(color.getRed(),color.getGreen(),color.getBlue()-2));
            }
            if(color.getBlue()<252){
                setBackground(new Color(color.getRed(),color.getGreen()+2,color.getBlue()));
            }
            repaint();
        }
    }
    
    private class DayCardHoverAnimator implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
           Color color=getBackground();
           int R=color.getRed();
           int G=color.getGreen();
           int B=color.getBlue();
           
           if(R<254)
              R=R+1;
           if(G<254)
               G=G+1;
           if(B<254)
               B=B+1;
           
          
           setBackground(new Color(R,G,B));
           repaint();
        }
    }
}
