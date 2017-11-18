package Actions;

import TodoManager.Settings;
import TodoManager.TaskUI;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import monthviewapplication.DayCard;
import monthviewapplication.ISelectedItemView;

/**
 * This action is responsible for opening window for creating a task.
 * @author Rupesh
 * @author Krishna
 */
public class EditAction<T extends Comparable> extends TodoManagerAction {

    private ISelectedItemView<T> _editTaskView;

    /**
     * Constructor. Creates an instance of CreateAction
     * @param name name of the action
     * @param icon icon of the action
     * @param mnemonic mnemonic of the action
     * @param toolTipText tool tip of the action
     * @param createTaskUI the UI for creating a task
     */
    public EditAction(String name, ImageIcon icon, int mnemonic,
                            String toolTipText, ISelectedItemView<T> editTaskView) {
        super(name, icon);
        putValue(Action.SHORT_DESCRIPTION, toolTipText);
        putValue(Action.MNEMONIC_KEY, mnemonic);
        this._editTaskView = editTaskView;
    }

    /**
     * When this method will be executed, it will show the UI inside
     * a dialog frame.
     * @param e event
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
         T itemtoremove = ((DayCard<T>)_editTaskView.getSelectedItem()).getSelectedItem().getCalendarEvent();
                 //_editTaskView.getSelectedItem();

        if (itemtoremove != null)
        {
            //changed to display new additem frame
            Settings.showDialog(Settings.getEditTaskView(itemtoremove));
        }
        else
        {
            Settings.displayMessage("No event selected to edit.");
        }
    }
}
