
package TodoManager;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author krishna
 */
public class CustomPanel extends JPanel{
    String bgImageLocation="./resources/calendar_bg.jpg";

    public CustomPanel() {
        this.setOpaque(true);
    }


    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d=(Graphics2D)g;
        File imageFile=new File(bgImageLocation);
        Image image=null;
        try{
            image=ImageIO.read(imageFile);
        }catch(IOException ex){
            ex.printStackTrace();
        }
        g2d.drawImage(image, 0, 0,this.HEIGHT,this.WIDTH, this);
        g.setColor(Color.RED);
        g2d.fillRect(2, 2, this.HEIGHT-4, this.WIDTH-4);
    }

}
