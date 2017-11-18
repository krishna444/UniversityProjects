package monthviewapplication;

import java.awt.Color;
import javax.swing.JLabel;

/**
 * Custom Label
 * @author krishna
 */
public class CustomLabel extends JLabel {

    public CustomLabel() {
        super();
    }
    public CustomLabel(String text){
        super(text);
        setBackground(Color.GRAY);
    }
    
    
}
