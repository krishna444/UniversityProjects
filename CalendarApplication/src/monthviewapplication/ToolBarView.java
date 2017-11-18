package monthviewapplication;

import Actions.CreateAction;
import Actions.MenuItemActionEvent;
import Actions.RemoveAction;
import Actions.UndoRedoAction;
import TodoManager.CalendarEvent;
import TodoManager.CustomDate;
import TodoManager.Settings;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.border.Border;

/**
 *
 * @author Suman
 */
public class ToolBarView extends Observable implements ActionListener
{

    private JToolBar toolbar;
    private JToolBar exittoolbar;

    /**	Add Task Button */
    private JButton createEvent;
    private JButton undoEvent;
    private JButton redoEvent;
    private JButton removeEvent;
    private JButton dayView;
    private JButton monthView;
    private JButton exitApp;
    private JButton editEvent;
    UndoRedoAction redoAction;
    UndoRedoAction undoAction;
    Calendar mvc;
    DayCalendar dvc;
    
    public ToolBarView(){

        toolbar = new JToolBar();

        exittoolbar = new JToolBar();

        exittoolbar.setLayout(new BorderLayout());

        exittoolbar.setMargin(new Insets(10,10,10,10));

        toolbar.setMargin(new Insets(15, toolbar.getInsets().left, toolbar.getInsets().bottom, toolbar.getInsets().right));

        //initailize the components
        createEvent = buttonSetUp(createEvent,"Create Event", "resources/add.png");
        createEvent.setActionCommand("Create");
        createEvent.addActionListener(this);
        
        undoEvent = buttonSetUp(undoEvent, "Undo", "resources/undo.png");
        undoEvent.setActionCommand("Undo");
        undoEvent.addActionListener(this);

        redoEvent = buttonSetUp(redoEvent, "Redo", "resources/redo.png");
        redoEvent.setActionCommand("Redo");
        redoEvent.addActionListener(this);

        editEvent = buttonSetUp(editEvent, "Edit", "resources/edit.png");
        editEvent.setActionCommand("Edit");
        editEvent.addActionListener(this);

        removeEvent = buttonSetUp(removeEvent,"Delete Event", "resources/delete.png");
        removeEvent.setActionCommand("Remove");
        removeEvent.addActionListener(this);

        monthView = buttonSetUp(monthView, "Month View", "resources/month.png");
        monthView.setActionCommand("MonthView");
        monthView.addActionListener(this);

        dayView = buttonSetUp(dayView, "Day View", "resources/day.png");
        dayView.setActionCommand("DayView");
        dayView.addActionListener(this);

        
        exitApp = buttonSetUp(exitApp, "Exit", "resources/exit.png");
        exitApp.setActionCommand("Exit");

        MenuItemActionEvent menuEvent = new MenuItemActionEvent();
        exitApp.addActionListener(menuEvent.exitAction);
        
        //layout the components

        toolbar.add(createEvent);
        toolbar.addSeparator(new Dimension(30,55));
        toolbar.add(editEvent);
        toolbar.add(removeEvent);
        toolbar.add(undoEvent);
        toolbar.add(redoEvent);
        toolbar.add(editEvent);
        toolbar.add(removeEvent);
	toolbar.addSeparator(new Dimension(30,55));
        
        
        toolbar.add(dayView);
        toolbar.add(monthView);
       

        toolbar.add(Box.createHorizontalGlue());

        toolbar.add(exitApp);
    }

    /**
	 * Initializes the button with its name and
	 * image. It can then be added to a panel.
	 *
	 * @param 	button		button to initialize
	 * @param 	title		button name
	 * @param 	iconpath	button Image path
	 * @return	back the same button
	 */
    public JButton buttonSetUp(JButton button, String title, String iconpath) {
        button = new JButton(new ImageIcon(iconpath));
        button.setVerticalTextPosition(AbstractButton.BOTTOM);
        button.setHorizontalTextPosition(AbstractButton.CENTER);
        button.setBorderPainted(false);
        button.setToolTipText(title);
        return button;
    }

    /**
     * Gets the toolbar panel in order to
     * allow for putting in frame.
     *
     * @return	toolbar panel
     */
    public JToolBar getToolbar() {
        return toolbar;
    }

    public void actionPerformed(ActionEvent e) {
        this.setChanged();
        notifyObservers("TOOLBAR_" + e.getActionCommand());
    }
}
