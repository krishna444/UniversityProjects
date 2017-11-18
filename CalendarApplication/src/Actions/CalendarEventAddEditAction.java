package Actions;


import DataStore.Repository;
import TodoManager.CalendarEvent;
import TodoManager.CustomDate;
import TodoManager.DateController;
import TodoManager.Settings;
import TodoManager.TaskUI;
import UndoRedo.AddCommand;
import UndoRedo.EditCommand;
import UndoRedo.UndoRedoManager;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.ImageIcon;

/**
 * This action is meant for adding a task create by a user in the repository.
 */
public class CalendarEventAddEditAction<T> extends TodoManagerAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Repository<CalendarEvent> repository;
    private TaskUI userInterface;

    /**
     * This creates an action for creating a task and adding it to the
     * repository
     * @param name The name of the action
     * @param icon The icon for the action
     * @param toolTipText The tool tip that should be displayed for the component
     * for which the action is associated.
     * @param tableModel The model that is shown in the table.
     * @param taskUI The UI used for capturing the user's information
     * @param repository The instance to communicate with the data store.
     */
    public CalendarEventAddEditAction(String name, ImageIcon icon, String toolTipText,
                    TaskUI taskUI,Repository<CalendarEvent> repo){
        super(name, icon);
        putValue(Action.SHORT_DESCRIPTION, toolTipText);
        //putValue(Action.MNEMONIC_KEY, mnemonic);
        userInterface = taskUI;
        repository = repo;
    }




    /**
     * When this action is performed it will extract information about a task 
     * object from the UI it belongs to. According to the extracted information
     * either a new task will be created or an existing task will be edited. 
     * @param e event
     */
    @Override
    public void actionPerformed(ActionEvent e){
        /**
         * Text fields are retrieved from the user interface
         */
        String title = userInterface.getTitle();
        String priority = userInterface.getPriority();
        String category = userInterface.getCategory();
        String description = userInterface.getDescription();

        DateController dateController = new DateController();
        CustomDate beginDate = dateController.extractDate(userInterface.getBeginDateUI());
        CustomDate endDate = dateController.extractDate(userInterface.getEndDateUI());

        /**
         * If all of the fields are non-empty, then create a Task object and
         * add it to the repository of the application
         */
        if(!title.equals(""))
        {
        	
        	if(priority == null)
                {
        		priority = "";
        	}
        	if(category == null)
                {
        		category = "";
        	}

        	if((DateController.compareDates(endDate, beginDate) != -1))
                {
	            CalendarEvent task = null;
                
	            if(!userInterface.isEditting() &&
	               (DateController.compareDates(beginDate, new CustomDate()) != -1))
                    {
                            // Create new task
		            task = new CalendarEvent(beginDate, endDate, title, description,
	                           category, priority, CalendarEvent.STRINGOFPENDING);

                            AddCommand<CalendarEvent> addcmd = new AddCommand<CalendarEvent>
                                    (repository, task);

                            addcmd.execute();

                            UndoRedoManager.getInstance().addUndoableCommand(addcmd);

	                userInterface.getJDialog().setVisible(false);
	
//	                Settings.displayMessage(
//	                		Settings.getCurrentResourceBundle().getString(
//	                				"Message.TaskAdded"));
	            }
	            else if(!userInterface.isEditting())
                    {
	            	Settings.displayMessage(
	            		Settings.getCurrentResourceBundle().getString(
	            				"Message.EarlyBeginDate"));
	            }
	            else if(userInterface.isEditting() &&
	 	               (DateController.compareDates(endDate, new CustomDate()) != -1)){
	            	// Edit existing task
                        // Create new task
		            task = new CalendarEvent(beginDate, endDate, title, description,
	                           category, priority, userInterface.getEdittedTask().getStatus());

                            EditCommand<CalendarEvent> editcmd = new EditCommand<CalendarEvent>
                                    (repository, userInterface.getEdittedTask(), task);

                            editcmd.execute();

                            UndoRedoManager.getInstance().addUndoableCommand(editcmd);
	                
	                userInterface.getJDialog().setVisible(false);
	
//	                Settings.displayMessage(
//	                	Settings.getCurrentResourceBundle().getString(
//	                			"Message.TaskEdited"));
	            }
	            else
                    {
	            	Settings.displayMessage(
	            		Settings.getCurrentResourceBundle().getString(
                			"Message.EarlyEndDate"));
	            }
        	}
        	else
                {
        		Settings.displayMessage(
    				Settings.getCurrentResourceBundle().getString(
                			"Message.EarlyEndBeginDate"));
        	}
        }
        else
        {
        	Settings.displayMessage(
    			Settings.getCurrentResourceBundle().getString(
                		"Message.AllFields"));
        }
    }
}
