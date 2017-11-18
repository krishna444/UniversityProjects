package monthviewapplication;

import TodoManager.Settings;
import java.awt.Component;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This is the transparency selector component which can be used 
 * as glass pane for main frame
 * @author krishna
 */
public class TransparencySelector extends JDialog {
    MainFrame parent=(MainFrame)Settings.getMainGUI();
    /**
     * Constructor
     */
    public TransparencySelector() {        
        super((MainFrame)Settings.getMainGUI(), "Set Transparency");
        JSlider transparencySlider=new JSlider(0, 50,0);
        transparencySlider.setValue(Settings.getTransparencyValue());
        //Turn on labels at major tick marks.
        transparencySlider.setMajorTickSpacing(10);
        transparencySlider.setMinorTickSpacing(1);
        transparencySlider.setPaintTicks(true);
        transparencySlider.setPaintLabels(true);
        
        transparencySlider.addChangeListener(new SliderListener(parent));
        getContentPane().add(transparencySlider);
        setLocation(parent.getLocation().x+20,parent.getLocation().y+90);
        setSize(500,100);
        setResizable(false);
        setDefaultCloseOperation(HIDE_ON_CLOSE);        
    }
    
    /**
     * Shows window
     */
    public void showWindow(){
        this.setVisible(true);
    }
    /**
     * Hides window
     */
    public void hideWindow(){
        this.setVisible(false);
    }    
    
}
class SliderListener implements ChangeListener {

    MainFrame fram;

    public SliderListener(MainFrame frame) {
        fram = frame;
    }

    @Override
    
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        float transparency=(float)(100 - slider.getValue()) / 100f;
        AWTUtilities.setWindowOpacity(fram, transparency);
        Settings.setTransparencyValue(slider.getValue());
        //this.fram.progressLabel.setText(slider.getValue()+"%");
    }
}
