package Actions;

import monthviewapplication.ISelectedItemView;
import DataStore.Repository;
import TodoManager.CalendarEvent;
import TodoManager.Settings;
import UndoRedo.RemoveCommand;
import UndoRedo.UndoRedoManager;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import monthviewapplication.DayCard;
import monthviewapplication.EventCard;

/**
 * This action is meant for removing the selected task from the repository
 * @author volkan cambazoglu
 * @author Rupesh
 */
public class RemoveAction<T extends Comparable> extends TodoManagerAction {

    private Repository<T> _repository;
    private ISelectedItemView<T> _removeView;

    /**
     * Constructor. Creates an instance of the RemoveAction class.
     * @param name name of the action
     * @param icon icon of the action
     * @param description description of the action
     * @param mnemonic mnemonic of the action
     * @param table table model
     * @param repo repository
     */
    public RemoveAction(String name, ImageIcon icon, String description,
            int mnemonic, Repository<T> repo, ISelectedItemView<T> view) {
        super(name, icon);
        putValue(Action.SHORT_DESCRIPTION, description);
        putValue(Action.MNEMONIC_KEY, mnemonic);
        _repository = repo;
        _removeView = view;
    }

    /**
     * When a task will be removed, this method will be executed to handle
     * remove operation.
     * @param e event
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        T itemtoremove = ((DayCard<T>)_removeView.getSelectedItem()).getSelectedItem().getCalendarEvent();

        if (itemtoremove != null) {
//            if (JOptionPane.showConfirmDialog(Settings.getMainGUI(),
//                    Settings.getCurrentResourceBundle().getString("Message.DeleteConfirmMsg"),
//                    Settings.getCurrentResourceBundle().getString("Message.DeleteConfirmTitle"),
//                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                RemoveCommand<T> removecmd = new RemoveCommand<T>(_repository, itemtoremove);

                removecmd.execute();

                UndoRedoManager.getInstance().addUndoableCommand(removecmd);

//                Settings.displayMessage(Settings.getCurrentResourceBundle().getString("Message.TaskRemoved"));
//            }
        }
        else {
            Settings.displayMessage("No event selected to delete.");
        }
    }
}
