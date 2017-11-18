package monthviewapplication;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author krishna
 */
public class CalendarPanel extends JPanel {

    private String bgImageLocation = "./resources/calendar_bg.jpg";
    private BufferedImage image = null;

    public CalendarPanel() {
        this.setOpaque(false);
//        File imageFile=new File(bgImageLocation);
        try {
            this.image = ImageIO.read(new File(bgImageLocation).toURI().toURL());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.image, 0, 0,null);
    }
}
