package monthviewapplication;

import TodoManager.CalendarEvent;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Admin
 * @author Krishna
 */
public class CalendarEventCard extends EventCard<CalendarEvent>
{
    private int margin = 3;

    public CalendarEventCard(CalendarEvent e, Rectangle rect)
    {
        super(e, rect);
    }

    @Override
    public void drawCard(Graphics g) {
        int type = AlphaComposite.SRC_OVER;

        AlphaComposite composite =
          AlphaComposite.getInstance(type, alpha);

        int arclen = _rectangle.height - 2 * margin;

        g.setColor(getEventFillColor());

        int x = _rectangle.x + margin +leftoffset;
        int y = _rectangle.y + margin;
        int width = _rectangle.width - 2 * margin - leftoffset - 1;
        int height = _rectangle.height - 2;

        g.fillRoundRect(x,
                y,
                width,
                height,
                arclen,
                arclen);

        g.setColor(getEventBorderColor());

        g.drawRoundRect(x,
                y,
                width,
                height,
                arclen,
                arclen);

        g.setClip(_rectangle.x + 1, _rectangle.y + 1, _rectangle.width - 2, _rectangle.height - 3);

        g.setColor(getEventStringColor());

        int labelWidth = g.getFontMetrics().stringWidth(event.getTitle());

        int xpos =  (int)(_rectangle.width - labelWidth ) /2;
        int ypos = (int)(_rectangle.height + g.getFontMetrics().getAscent()) /2;

        g.drawString(event.getTitle(), _rectangle.x + xpos, _rectangle.y + ypos);

        g.setClip(null);

        if(this.getIsSelected())
            selectEventCard(g);
    }

    @Override
    public void selectEventCard(Graphics g) {
        g.setColor(getSelectionColor());

        int arclen = _rectangle.height - 2 * margin;

        int x = _rectangle.x + margin +leftoffset;
        int y = _rectangle.y + margin;
        int width = _rectangle.width - 2 * margin - leftoffset - 1;
        int height = _rectangle.height - 2;

        g.drawRoundRect(x,
                y,
                width,
                height,
                arclen,
                arclen);
        g.fillRoundRect(x, y, width, height, arclen, arclen);
        
        g.setColor(getEventStringColor());

        int labelWidth = g.getFontMetrics().stringWidth(event.getTitle());

        int xpos =  (int)(_rectangle.width - labelWidth ) /2;
        int ypos = (int)(_rectangle.height + g.getFontMetrics().getAscent()) /2;

        g.drawString(event.getTitle(), _rectangle.x + xpos, _rectangle.y + ypos);

    }
    
    /**
     * Gets the event associated with the event card
     * @return Calendar Event
     */
    @Override
    public CalendarEvent getEvent(){
        return this.event;
    }
}
