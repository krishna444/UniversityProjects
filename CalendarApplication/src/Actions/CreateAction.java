package Actions;

import TodoManager.Settings;
import TodoManager.TaskUI;
import TodoManager.TaskView;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This action is responsible for opening window for creating a task.
 * @author Rupesh
 * @author volkan cambazoglu
 */
public class CreateAction<T> extends TodoManagerAction {
	
    private TaskView createTaskView;

    /**
     * Constructor. Creates an instance of CreateAction
     * @param name name of the action
     * @param icon icon of the action
     * @param mnemonic mnemonic of the action
     * @param toolTipText tool tip of the action
     * @param createTaskUI the UI for creating a task
     */
    public CreateAction(String name, ImageIcon icon, int mnemonic,
                            String toolTipText, TaskView createTaskView) {
        super(name, icon);
        putValue(Action.SHORT_DESCRIPTION, toolTipText);
        putValue(Action.MNEMONIC_KEY, mnemonic);
        this.createTaskView = createTaskView;
    }

    /**
     * When this method will be executed, it will show the UI inside 
     * a dialog frame.
     * @param e event
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
         Settings.showDialog(Settings.getCreateTaskView());
    }
}
