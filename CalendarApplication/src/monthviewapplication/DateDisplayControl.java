package monthviewapplication;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.Font;
import java.awt.font.TextLayout;

/**
 *
 * @author krishna
 */
public class DateDisplayControl extends JComponent implements ActionListener, Observer {

    private Timer tickTimer = null;
    private Animator animator = null;
    Calendar calendar = null;
    //Bound
    private int width = 0;
    private int height = 0;

    public DateDisplayControl(Dimension bound) {
        this.tickTimer = new Timer(999, this);
        this.animator = new Animator(bound);
        this.animator.addObserver(this);
        this.startTickTimer();
        setOpaque(false);

    }

    /**
     * Starts timer
     */
    public void startTickTimer() {
        this.tickTimer.start();
    }

    /**
     * Stops timer
     */
    public void stopTickTimer() {
        this.tickTimer.stop();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        FontMetrics fontMetrics = g.getFontMetrics();
        Graphics2D g2d = (Graphics2D) g.create();



        //Get calendar values
        this.calendar = Calendar.getInstance();
        String year = Integer.toString(this.calendar.get(Calendar.YEAR));
        String month = Integer.toString(this.calendar.get(Calendar.MONTH) + 1);
        month = month.length() == 1 ? "0" + month : month;
        String day = Integer.toString(this.calendar.get(Calendar.DAY_OF_MONTH));
        day = day.length() == 1 ? "0" + day : day;

        String hour = Integer.toString(this.calendar.get(Calendar.HOUR));
        hour = hour.length() == 1 ? "0" + hour : hour;
        String minute = Integer.toString(this.calendar.get(Calendar.MINUTE));
        minute = minute.length() == 1 ? "0" + minute : minute;
        String second = Integer.toString(this.calendar.get(Calendar.SECOND));
        second = second.length() == 1 ? "0" + second : second;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String dateString = dateFormat.format(calendar.getTime());
        int dateWidth = fontMetrics.stringWidth(dateString);
        int yearWidth = fontMetrics.stringWidth(year);
        int monthWidth = fontMetrics.stringWidth(month);
        int dayWidth = fontMetrics.stringWidth(day);
        int hourWidth = fontMetrics.stringWidth(hour);
        int minuteWidth = fontMetrics.stringWidth(minute);
        int secondWidth = fontMetrics.stringWidth(second);

        int fontHeight = fontMetrics.getHeight();

        FontRenderContext fontRendererContext=g2d.getFontRenderContext();
        Font f=new Font("Times",Font.BOLD,20);
        TextLayout textLayout=new TextLayout(dateString, f, fontRendererContext);
        g2d.setColor(Color.red);
        textLayout.draw(g2d, 0f, 18f);
        g2d.dispose();
    }

    public void actionPerformed(ActionEvent e) {
        //this.repaint();
    }

    public void update(Observable observable, Object object) {
        Point location = (Point) object;
        this.setLocation(location);
    }

    class Animator extends Observable implements ActionListener {

        private Timer animationTimer = null;
        private int dx = 1;
        private int dy = 1;
        private Point location = new Point();
        private Dimension size = new Dimension();

        public Animator() {
            this(new Dimension(50, 50));
        }

        public Animator(Dimension dimension) {
            this(new Point(0, 0),dimension);
        }
        public Animator(Point location, Dimension dimension){
            this.location=location;
            this.size=dimension;
            this.animationTimer = new Timer(25, this);
            this.startAnimationTimer();
        }

        public void setLocation(int x, int y) {
            this.location.x = x;
            this.location.y = y;
        }

        public void startAnimationTimer() {
            this.animationTimer.start();
        }

        public void stopAnimationTimer() {
            this.animationTimer.stop();
        }

        public void actionPerformed(ActionEvent e) {
            this.dx = 1;
            this.dy = 15;
            if ((this.location.x + dx) < this.size.width && (this.location.y + dy) < this.size.height) {
                this.location.setLocation(this.location.x + dx, this.location.y);
            }
            if ((this.location.x + dx) >= this.size.width) {
                this.location.setLocation(0, this.location.y + this.dy);
            }
            if ((this.location.y + dy) >= this.size.height) {
                this.location.setLocation(this.location.x + dx, 0);
            }            
            this.informLocationChanged();
        }

        private void informLocationChanged() {
            this.setChanged();
            this.notifyObservers(this.location);
        }
    }
}
