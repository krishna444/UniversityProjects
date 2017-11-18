package TodoManager;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * TaskUI class consists of the user interface for creating or editing a task
 * When TaskUI object is created, the user interface is presented.
 * A user can either create a new Task or edit an existing Task using this
 * interface.
 * @author volkan cambazoglu
 */
public class TaskUI extends JPanel {

    private JDialog jDialog;
    private boolean editting;
    private CalendarEvent edittedTask;

    /**
     * Set of JPanels
     * groupPanel consists of gridPanel and descriptionPanel.
     * gridPanel consists of leftPanel and rightPanel.
     * leftPanel consists of title, dates, priority and category labels.
     * rightPanel consists of title, dates, priority and category input areas.
     * descriptionPanel consists of descriptionLabel and descriptionPane.
     * buttonPanel consists of submit button.
     */
    private JPanel groupPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel buttonPanel;
    private JPanel descriptionPanel;
    private JPanel gridPanel;
    private DateUI beginDate;
    private DateUI endDate;
    private JPanel dateLabels;

    /**
     * Labels to indicate combo boxes for date selection.
     */
    private JLabel yearLabel;
    private JLabel monthLabel;
    private JLabel dayLabel;
    private JLabel hourLabel;
    private JLabel minuteLabel;

    /**
     * There are one label and one/several text field/combo box for each
     * attribute of Task object.
     * The user sees the labels and fills/selects corresponding input areas.
     */
    private JLabel titleLabel;
    private JTextField titleTextField;

    private JLabel beginDateLabel;
    private JLabel endDateLabel;

    private JLabel priorityLabel;
    private JComboBox priorityComboBox;

    private JLabel categoryLabel;
    private JComboBox categoryComboBox;

    private JLabel descriptionLabel;
    private JTextArea descriptionTextArea;
    private JScrollPane descriptionPane;

    private JButton submit;

    /**
     * Constructor of TaskUI
     * Used for creating a Task
     * User interface appears with empty text fields.
     */
    public TaskUI(){
        this(null);
    }

    /**
     * Constructor of TaskUI
     * Used for editing an existing Task
     * User interface appears with text fields that are filled with the
     * information of provided Task object.
     * @param task The task to be edited.
     */
    public TaskUI(CalendarEvent task){
        super();
        jDialog = null;
    	edittedTask = task;
        if(edittedTask==null){
            editting = false;
        }
        else{
            editting = true;
        }
        initializeComponents();
    }

    /**
     * Initializes components of the user interface
     */
    private void initializeComponents(){
    	/**
         * Initialize JPanels.
         */
        setLayout(new BorderLayout(20, 20));

        groupPanel = new JPanel();
        groupPanel.setLayout(new BorderLayout(20, 20));

        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(6, 0, 10, 20));

        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(6, 0, 0, 20));

        descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new GridLayout(1, 2, 20, 0));

        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(0, 2, 20, 0));

        buttonPanel = new JPanel();

        dateLabels = new JPanel();
        dateLabels.setLayout(new GridLayout(1, 5, 10, 0));

        /**
         * Initialize JLabels and corresponding input areas
         */
        titleLabel = new JLabel();
        titleLabel.setText(Settings.getCurrentResourceBundle().getString(
                                                              "TaskUI.Title"));
        titleTextField = new JTextField();

        yearLabel = new JLabel();
        yearLabel.setText(Settings.getCurrentResourceBundle().getString(
                                                              "TaskUI.Year"));
        monthLabel = new JLabel();
        monthLabel.setText(Settings.getCurrentResourceBundle().getString(
                                                              "TaskUI.Month"));
        dayLabel = new JLabel();
        dayLabel.setText(Settings.getCurrentResourceBundle().getString(
                                                              "TaskUI.Day"));
        hourLabel = new JLabel();
        hourLabel.setText(Settings.getCurrentResourceBundle().getString(
                                                              "TaskUI.Hour"));
        minuteLabel = new JLabel();
        minuteLabel.setText(Settings.getCurrentResourceBundle().getString(
                                                             "TaskUI.Minute"));

        beginDateLabel = new JLabel();
        beginDateLabel.setText(Settings.getCurrentResourceBundle().getString(
                                                          "TaskUI.BeginDate"));

        endDateLabel = new JLabel();
        endDateLabel.setText(Settings.getCurrentResourceBundle().getString(
                                                            "TaskUI.EndDate"));

        priorityLabel = new JLabel();
        priorityLabel.setText(Settings.getCurrentResourceBundle().getString(
                                                           "TaskUI.Priority"));
        priorityComboBox = new JComboBox(CalendarEvent.getPriorityList());

        categoryLabel = new JLabel();
        categoryLabel.setText(Settings.getCurrentResourceBundle().getString(
                                                           "TaskUI.Category"));
        categoryComboBox = new JComboBox(CalendarEvent.getCategoryList());

        descriptionLabel = new JLabel();
        descriptionLabel.setText(Settings.getCurrentResourceBundle().getString(
                                                        "TaskUI.Description"));
        descriptionTextArea = new JTextArea();
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);

        descriptionPane = new JScrollPane(descriptionTextArea);

        if(edittedTask != null){
            /**
             * If statement is executed only when a task object is present. In
             * other words when a task is edited, this part of the code is
             * executed.
             */
            titleTextField.setText(edittedTask.getTitle());
            beginDate = new DateUI(edittedTask.getBeginDate());
            endDate = new DateUI(edittedTask.getEndDate());
            if(!edittedTask.getPriority().equalsIgnoreCase("")){
            	priorityComboBox.setSelectedItem(edittedTask.getPriority());
            }
            else{
            	priorityComboBox.setSelectedIndex(-1);
            }
            if(!edittedTask.getCategory().equalsIgnoreCase("")){
            	categoryComboBox.setSelectedItem(edittedTask.getCategory());
            }
            else{
            	categoryComboBox.setSelectedIndex(-1);
            }
            descriptionTextArea.setText(edittedTask.getDescription());
        }
        else{
            /**
             * A new task will be created.
             * Initialize date and time with current time
             */
            beginDate = new DateUI();
            endDate = new DateUI();
            priorityComboBox.setSelectedIndex(-1);
            categoryComboBox.setSelectedIndex(-1);
        }

        /**
         * Add each initialized component to appropriate JPanel, as explained
         * above.
         */
        dateLabels.add(yearLabel);
        dateLabels.add(monthLabel);
        dateLabels.add(dayLabel);
        dateLabels.add(hourLabel);
        dateLabels.add(minuteLabel);

        leftPanel.add(titleLabel);
        leftPanel.add(new JLabel());
        leftPanel.add(beginDateLabel);
        leftPanel.add(endDateLabel);
        leftPanel.add(priorityLabel);
        leftPanel.add(categoryLabel);

        rightPanel.add(titleTextField);
        rightPanel.add(dateLabels);
        rightPanel.add(beginDate);
        rightPanel.add(endDate);
        rightPanel.add(priorityComboBox);
        rightPanel.add(categoryComboBox);

        descriptionPanel.add(descriptionLabel);
        descriptionPanel.add(descriptionPane);

        submit = new JButton();
        submit.setText("Submit");

        buttonPanel.add(submit);

        gridPanel.add(leftPanel);
        gridPanel.add(rightPanel);

        groupPanel.add(gridPanel, BorderLayout.PAGE_START);
        groupPanel.add(descriptionPanel, BorderLayout.CENTER);

        add(new JPanel(),BorderLayout.PAGE_START);
        add(new JPanel(),BorderLayout.LINE_START);
        add(new JPanel(),BorderLayout.LINE_END);
        add(groupPanel,BorderLayout.CENTER);
        add(buttonPanel,BorderLayout.PAGE_END);

        setBounds(200, 100, 800, 450);
    }

    /**
     * Getter method of category from the UI
     * @return category category that the user has selected
     */
    public String getCategory(){
        return (String) categoryComboBox.getSelectedItem();
    }

    /**
     * Getter method of description from the UI
     * @return description description that the user has typed
     */
    public String getDescription(){
        return descriptionTextArea.getText();
    }

    /**
     * Getter method of priority
     * @return priority priority that the user has selected
     */
    public String getPriority(){
        return (String) priorityComboBox.getSelectedItem();
    }

    /**
     * Getter method of title
     * @return title title that the user has typed
     */
    public String getTitle(){
        return titleTextField.getText();
    }

    /**
     * Is this UI editing an existing Task object?
     * @return editing true when editing an existing Task object and false
     * when creating new Task object
     */
    public boolean isEditting() {
        return editting;
    }

    /**
     * Getter method of the DateUI of BeginDate
     * @return DateUI of BeginDate
     */
    public DateUI getBeginDateUI() {
        return beginDate;
    }

    /**
     * Getter method of the DateUI of EndDate
     * @return DateUI of EndDate
     */
    public DateUI getEndDateUI() {
        return endDate;
    }

    /**
     * Getter method of the Task object that is being edited
     * @return Task task object. If the UI is for creating a new Task object
     * then null is returned.
     */
    public CalendarEvent getEdittedTask() {
        return edittedTask;
    }

    /**
     * Setter of action of submit button
     * @param a action to be set
     */
    public void setSubmitAction(Action a) {
        submit.setAction(a);
    }

    /**
     * Getter method of the parent dialog window
     * @return jDialog parent window
     */
    public JDialog getJDialog(){
        return jDialog;
    }

    /**
     * Setter method of the parent window
     * @param jDial parent window
     */
    public void setParentWindow(JDialog jDial){
        jDialog = jDial;
    }
}
