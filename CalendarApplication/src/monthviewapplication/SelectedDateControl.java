package monthviewapplication;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.JComponent;
import java.util.Calendar;
import java.util.Locale;

/**
 * This is the custom date control which is
 * @author krishna
 */
public class SelectedDateControl extends JComponent{
    private Calendar calendar;
    private float angleToRotate=-1;
    private float scaleX=0.0f;
    private float scaleY=0.0f;


    public SelectedDateControl() {
        this.angleToRotate=30;
        this.calendar=Calendar.getInstance();
        this.scaleX=5.0f;
        this.scaleY=4.0f;
        setOpaque(false);
    }
    public Calendar getCalendar(){
        return this.calendar;
    }
    public void setCalendar(Calendar calendar){
        this.calendar=calendar;
    }
    @Override
    public void paintComponent(Graphics g){
       Graphics2D g2d=(Graphics2D)g.create();
       Composite composite=AlphaComposite.getInstance(AlphaComposite.SRC,0.5f);
       g2d.setComposite(composite);
       
       AffineTransform affineTransform=new AffineTransform();
       affineTransform.setToRotation(-this.angleToRotate*Math.PI/180);
       affineTransform.scale(this.scaleX, this.scaleY);
       affineTransform.translate(20, 150);
       g2d.setTransform(affineTransform);

       String year=Integer.toString(this.calendar.get(Calendar.YEAR));
       String month=this.calendar.getDisplayName(Calendar.MONTH,Calendar.LONG,Locale.ENGLISH);
       g2d.setColor(Color.WHITE);
       g2d.drawString(month+" "+year, 0,0);
//       FontRenderContext fontRendererContext=g2d.getFontRenderContext();
//        Font f=new Font("Times",Font.BOLD,40);
//        TextLayout textLayout=new TextLayout(month+" "+year, f, fontRendererContext);
//        g2d.setColor(Color.LIGHT_GRAY);
//        textLayout.draw(g2d, 360f, 300f);
       g2d.dispose();
    }

    
}
