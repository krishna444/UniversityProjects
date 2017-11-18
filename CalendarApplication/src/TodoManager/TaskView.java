package TodoManager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.Box;
import monthviewapplication.AWTUtilities;
import monthviewapplication.CalendarDialog;
import monthviewapplication.CustomDateTimeField;

/**
 *It is redesign of the previously build
 * @author krishna
 */
public class TaskView extends JPanel {
    /*
     * properties variables
    */
    private CalendarEvent task;
    private boolean isEditing=false;
    private JDialog jDialog;
    CalendarDialog calendarDialog;

    /*
     * Required Controls
     */
    JTextField titleTextField;
    CustomDateTimeField beginDateTextField;
    CustomDateTimeField endDateTextField;
    JComboBox priorityComboBox;
    JComboBox categoryComboBox;
    JTextArea descriptionTextArea;
    JButton submitButton;
    JButton cancelButton;
    JPanel buttonPanel;



    /**
     * Constructor for New Task Mode
     */
    public TaskView() {
        this(null);
    }

    /**
     * Constructor for Editing Mode
     * @param task
     */
    public TaskView(CalendarEvent task){
        super();
        this.task=task;
        this.isEditing=!(this.task==null);
        this.initialiseComponents();
        this.setBounds(200, 100, 500, 200);
    }

    private void initialiseComponents(){
        this.setLayout(new BorderLayout(25, 5));


        this.calendarDialog=new CalendarDialog(jDialog,Calendar.getInstance());

        //Top contents
        JPanel topPanel=new JPanel(new BorderLayout(25,20));
        JLabel titleLabel=new JLabel("Title:");
        this.titleTextField=new JTextField(5);
        topPanel.add(titleLabel,BorderLayout.WEST);
        topPanel.add(this.titleTextField,BorderLayout.CENTER);

        //Middle contents
        JPanel middleYPanel=new JPanel();
        middleYPanel.setLayout(new BoxLayout(middleYPanel, BoxLayout.Y_AXIS));

        JPanel middleX1Panel=new JPanel();
        middleX1Panel.setLayout(new BoxLayout(middleX1Panel, BoxLayout.X_AXIS));
        JLabel beginDateLabel=new JLabel("Begin Date:");
        this.beginDateTextField =new CustomDateTimeField(jDialog);
        middleX1Panel.add(beginDateLabel);
        middleX1Panel.add(this.beginDateTextField);
        
        JLabel endDateLabel=new JLabel("End Date:");
        this.endDateTextField=new CustomDateTimeField(jDialog);
        middleX1Panel.add(endDateLabel);
        middleX1Panel.add(this.endDateTextField);

        middleYPanel.add(middleX1Panel);
        middleYPanel.add(Box.createVerticalStrut(10));

        JPanel middleX2Panel=new JPanel();
        middleX2Panel.setLayout(new BoxLayout(middleX2Panel, BoxLayout.LINE_AXIS));
        JLabel priorityLabel=new JLabel("Priority:");
        this.priorityComboBox=new JComboBox(CalendarEvent.getPriorityList());
        middleX2Panel.add(priorityLabel);
        middleX2Panel.add(this.priorityComboBox);
        JLabel categoryLabel=new JLabel("Category:");
        this.categoryComboBox=new JComboBox(CalendarEvent.getCategoryList());
        middleX2Panel.add(categoryLabel);
        middleX2Panel.add(this.categoryComboBox);

        middleYPanel.add(middleX2Panel);
        middleYPanel.add(Box.createVerticalStrut(10));
        
        JPanel middleX3Panel=new JPanel();
        middleX3Panel.setLayout(new BoxLayout(middleX3Panel, BoxLayout.LINE_AXIS));
        JLabel descriptionLabel=new JLabel("Description:");
        this.descriptionTextArea=new JTextArea(4,4);
        middleX3Panel.add(descriptionLabel);
        middleX3Panel.add(this.descriptionTextArea);

        middleYPanel.add(middleX3Panel);
        middleYPanel.add(Box.createVerticalStrut(10));

        JPanel bottomPanel=new JPanel(new BorderLayout(4, 4));
        this.submitButton=new JButton("Submit");        
        buttonPanel = new JPanel();
        buttonPanel.add(submitButton);

        bottomPanel.add(buttonPanel, BorderLayout.EAST);
        
        this.add(topPanel,BorderLayout.NORTH);

        JPanel positionPanel=new JPanel(new BorderLayout());
        positionPanel.add(middleYPanel,BorderLayout.NORTH);
        this.add(positionPanel,BorderLayout.CENTER);
        this.add(bottomPanel,BorderLayout.SOUTH);

        this.setBorder(BorderFactory.createTitledBorder("Add/Edit Event"));

        this.loadValues();        
    }


    /**
     * Loads the values in the components depending upon edit mode or new mode
     */
    private void loadValues(){
        //Edit mode
        if(this.task!=null){
           this.titleTextField.setText(this.task.getTitle());
           //load priority
           if(!this.task.getPriority().equalsIgnoreCase("")){
               this.priorityComboBox.setSelectedItem(this.task.getPriority());
           }
           else{
               this.priorityComboBox.setSelectedIndex(-1);
           }
           //load category
           if(!this.task.getCategory().equalsIgnoreCase("")){
            	this.categoryComboBox.setSelectedItem(this.task.getCategory());
            }
            else{
               this.categoryComboBox.setSelectedIndex(-1);
            }
           //load dates
           DateController dateController=new DateController();
           this.beginDateTextField.setCalendar(dateController.extractCalendar(this.task.getBeginDate()));
           this.endDateTextField.setCalendar(dateController.extractCalendar(this.task.getEndDate()));
           
           
           this.descriptionTextArea.setText(this.task.getDescription());
        }
        else{
            /**
             * A new task will be created.
             * Initialize date and time with current time
             */
            this.priorityComboBox.setSelectedIndex(-1);
            this.categoryComboBox.setSelectedIndex(-1);
        }
    }


    /**
     * Getter method of category from the UI
     * @return category category that the user has selected
     */
    public String getCategory(){
        return (String) this.categoryComboBox.getSelectedItem();
    }

    /**
     * Getter method of description from the UI
     * @return description description that the user has typed
     */
    public String getDescription(){
        return this.descriptionTextArea.getText();
    }

    /**
     * Getter method of priority
     * @return priority priority that the user has selected
     */
    public String getPriority(){
        return (String) this.priorityComboBox.getSelectedItem();
    }

    /**
     * Getter method of title
     * @return title title that the user has typed
     */
    public String getTitle(){
        return this.titleTextField.getText();
    }

    /**
     * Setter of action of submit button
     * @param a action to be set
     */
    public void setSubmitAction(javax.swing.Action a) {
        this.submitButton.setAction(a);
    }

    public void setCancelAction(javax.swing.Action a){
        this.cancelButton.setAction(a);
        System.out.println("Pressed");
    }

    public Calendar getBeginCalendar(){
        return this.beginDateTextField.getCalendar();
    }

    public Calendar getEndCalendar(){
        return this.endDateTextField.getCalendar();
    }



    /**
     * Is this UI editing an existing Task object?
     * @return editing true when editing an existing Task object and false
     * when creating new Task object
     */
    public boolean isEditting() {
        return this.isEditing;
    }

    /**
     * Getter method of the Task object that is being edited
     * @return Task task object. If the UI is for creating a new Task object
     * then null is returned.
     */
    public CalendarEvent getEdittedTask() {
        return this.task;
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
        AWTUtilities.setWindowOpacity(jDialog, 0.94f);
    }
}


