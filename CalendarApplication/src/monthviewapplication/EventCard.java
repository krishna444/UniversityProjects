
package monthviewapplication;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Admin
 * @author Krishna
 */
public abstract class EventCard<T extends Comparable> {

    public static int EVENT_CARD = 0;

    public static int HEADER_EVENT_CARD = 1;

    private boolean _isSelected;

    public float alpha = 0.75f;

    public int leftoffset =0;

    public boolean getIsSelected() {
        return _isSelected;
    }

    public void setIsSelected(boolean _isSelected) {
        this._isSelected = _isSelected;
    }

    T event;

    Rectangle _rectangle;

    private Color _eventFillColor = Color.green;
    
    private Color _selectionColor = Color.RED;

    public Color getSelectionColor() {
        return _selectionColor;
    }

    public void setSelectionColor(Color _selectionColor) {
        this._selectionColor = _selectionColor;
    }

    private int cardType = 0;

    public Color getEventBorderColor() {
        return _eventBorderColor;
    }

    public void setEventBorderColor(Color _eventBorderColor) {
        this._eventBorderColor = _eventBorderColor;
    }

    public Color getEventFillColor() {
        return _eventFillColor;
    }
    
    
    public abstract T getEvent();

    public void setEventFillColor(Color _eventFillColor) {
        this._eventFillColor = _eventFillColor;
    }

    public Color getEventStringColor() {
        return _eventStringColor;
    }

    public void setEventStringColor(Color _eventStringColor) {
        this._eventStringColor = _eventStringColor;
    }

    private Color _eventBorderColor = Color.black;

    private Color _eventStringColor = Color.blue;

    public EventCard(T event, Rectangle rect) {
        this.event = event;
        this._rectangle = rect;
    }

    public void setCardType(int type)
    {
        this.cardType = type;
    }

    public int getCardType()
    {
        return this.cardType;
    }

    public boolean hasEvent(T evnt)
    {
        return event.equals(evnt);
    }

    public T getCalendarEvent() {
        return this.event;
    }

    public Rectangle getBounds() {
        return this._rectangle;
    }

    public void setBounds(Rectangle r) {
        _rectangle = r;
    }

    /**
     * Check whether the specified position lies in the event rectangle or not.
     * @param x The x position to test.
     * @param y The y position to test.
     * @return True if the specified x,y lies in the event rectangle otherwise false.
     */
    public boolean hitTest(int x, int y) {
        if (x >= _rectangle.x && x <= _rectangle.x + _rectangle.width
                && y >= _rectangle.y && y <= _rectangle.y + _rectangle.height) {
            return true;
        }

        return false;
    }

    public abstract void drawCard(Graphics g);

    public abstract void selectEventCard(Graphics g);

}
