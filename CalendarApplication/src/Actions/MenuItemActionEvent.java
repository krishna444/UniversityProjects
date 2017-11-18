
package Actions;

import TodoManager.Settings;
import UndoRedo.UndoRedoManager;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import monthviewapplication.AboutDialog;
import monthviewapplication.ToolBarView;
import monthviewapplication.TransparencySelector;

/**
 * This handles the menu related actions
 * @author krishna
 */
public class MenuItemActionEvent{
    abstract class DefaultMenuItemActionEvent extends AbstractAction {

        public DefaultMenuItemActionEvent(String name,String shortDescription,String mnemonicKey) {
            putValue(Action.NAME, name);
            putValue(Action.SHORT_DESCRIPTION, shortDescription);
            putValue(Action.MNEMONIC_KEY, new Integer(mnemonicKey.charAt(0)));
        }
    }

    //Create action instances

    public DefaultMenuItemActionEvent exitAction=new DefaultMenuItemActionEvent
            ("Exit", "Exits the application", "x") {

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    };

   public DefaultMenuItemActionEvent newEventAction=new DefaultMenuItemActionEvent
           ("Add new event", "Adds new event", "n") {

        public void actionPerformed(ActionEvent e) {
            Settings.showDialog(Settings.getCreateTaskView());
        }
    };


    public DefaultMenuItemActionEvent viewCategoriesAction=new DefaultMenuItemActionEvent
            ("View Categories", "view categories", "c") {

        public void actionPerformed(ActionEvent e) {
        }
    };

    public DefaultMenuItemActionEvent viewEventsAction=new DefaultMenuItemActionEvent
            ("Events", "View events", "e") {

        public void actionPerformed(ActionEvent e) {
            //ToolBarView toolbarView=new ToolBarView();

        }
    };

    public DefaultMenuItemActionEvent viewMonthAction=new DefaultMenuItemActionEvent
            ("Month", "View month", "m") {

        public void actionPerformed(ActionEvent e) {
        }
    };
    public DefaultMenuItemActionEvent viewDayAction=new DefaultMenuItemActionEvent
            ("Day", "View day", "d") {

        public void actionPerformed(ActionEvent e) {
        }
    };
     public DefaultMenuItemActionEvent settingsEventCategoryAction=new DefaultMenuItemActionEvent
             ("Event category", "Click here to set event category", "e") {

        public void actionPerformed(ActionEvent e) {
        }
    };
     
     public DefaultMenuItemActionEvent settingsTransparencyAction=new DefaultMenuItemActionEvent
             ("Transparency", "Sets the Transparency", "t") {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     TransparencySelector selector=new TransparencySelector();
                     selector.setModal(true);
                     selector.setVisible(true);
                     
                 }
             };
        
    public DefaultMenuItemActionEvent aboutAction=new DefaultMenuItemActionEvent
            ("About", "About calendar application", "a") {

        public void actionPerformed(ActionEvent e) {
            AboutDialog about=new AboutDialog();            
            about.setModal(true);
            about.setVisible(true);
        }
    };
    
    public DefaultMenuItemActionEvent howToAction=new DefaultMenuItemActionEvent
            ("How To", "Interactive animations on how to use the application", "h") {

        public void actionPerformed(ActionEvent e) {
        }
    };


    public DefaultMenuItemActionEvent modifyEventAction=new DefaultMenuItemActionEvent
            ("Modify Event", "Modify event", "m") {

        public void actionPerformed(ActionEvent e) {

            /*
             * pass the day/month event here
            Settings.showDialog(Settings.getEditTaskView(mc.getSelectedItem().getSelectedItem().getCalendarEvent()));
            TaskView edittaskview = new TaskView();
             
             */
        }
    };

    public DefaultMenuItemActionEvent undoAction=new DefaultMenuItemActionEvent
            ("Undo", "Undo the previously done action", "u") {

        public void actionPerformed(ActionEvent e) {
            UndoRedoManager.getInstance().undo(1);
        }
    };

     public DefaultMenuItemActionEvent redoAction=new DefaultMenuItemActionEvent
            ("Redo", "Redo the previously undone action", "r") {

        public void actionPerformed(ActionEvent e) {
            UndoRedoManager.getInstance().redo(1);
        }
    };


    public DefaultMenuItemActionEvent deleteAction = new DefaultMenuItemActionEvent("Delete", "Delete the selected event", "d") {

        public void actionPerformed(ActionEvent ae) {
            
        }
    };
}
