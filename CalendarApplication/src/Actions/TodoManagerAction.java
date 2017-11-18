package Actions;

import UndoRedo.ICommand;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

/**
 * TodoManagerAction is an abstract class that forms a basis for possible
 * new actions that can be used in TodoManager application
 * @author volkan cambazoglu
 * @author Rupesh
 */
public abstract class TodoManagerAction extends AbstractAction
{
    
	/**
	 * Default Constructor. Creates an instance of the TodoManagerAction class.
	 */
    public TodoManagerAction()
    {
        this("", null);
    }

    /**
     * Constructor. Creates an instance of the TodoManagerAction class.
     * @param name name of the action
     * @param icon icon of the action
     */
    public TodoManagerAction(String name, ImageIcon icon)
    {
        super(name, icon);
    }

    /**
     * When this action is performed, do what is necessary.
     * @param e event
     */
    public void actionPerformed(ActionEvent e) { }
}
