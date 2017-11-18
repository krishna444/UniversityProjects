package monthviewapplication;

import Actions.LookAndFeelActionListener;
import TodoManager.Settings;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Observable;
import javax.swing.JFrame;
import Actions.MenuItemActionEvent;
import Actions.PriorityButtonAction;
import TodoManager.CalendarEvent;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JButton;
import TodoManager.CustomDate;
import TodoManager.TaskView;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Properties;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowStateListener;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JLayeredPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.OverlayLayout;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

/**
 * Main Frame
 * @author krishna
 */
public class MainFrame extends JFrame implements Observer {

    Rectangle position = null;
    JButton updateButton;
    JComboBox yearComboBox;
    JComboBox monthComboBox;
    monthviewapplication.MonthCalendar mvc;
    DayCalendar dvc;
    MiniCalendar miniCal;
    ToolBarView toolbar;
    private static final int MAX_DELAY = 10;
    private ProgressPane progressPane;
    private DateDisplayControl dateDisplayControl;
    private SelectedDateControl selectedDateControl;
    private UIManager.LookAndFeelInfo looks[] = UIManager.getInstalledLookAndFeels();

    //GlassPane m_glassPane;
    ToggleButtonMenuItem howToMenuItem;

    public MainFrame(Properties pref) {
        super("Calendar");
        this.setMinimumSize(new Dimension(950, 700));
        setIconImage(Toolkit.getDefaultToolkit().getImage("resources/calendar_16x16.png"));

        progressPane = new ProgressPane();
        setGlassPane(progressPane);


        this.dateDisplayControl=new DateDisplayControl(this.getSize());
        this.selectedDateControl=new SelectedDateControl();

        
        JLayeredPane layeredPane=getRootPane().getLayeredPane();
        //layeredPane.setLayout(new OverlayLayout(layeredPane));
        this.dateDisplayControl.setBounds(this.getBounds());
        layeredPane.add(this.dateDisplayControl,(int)(JLayeredPane.DEFAULT_LAYER+415),14);

        JLayeredPane layeredPane1=getRootPane().getLayeredPane();
        //layeredPane1.setLayout(new OverlayLayout(layeredPane1));
        this.selectedDateControl.setBounds(this.getBounds());
        layeredPane1.add(this.selectedDateControl,(int)(JLayeredPane.DEFAULT_LAYER+450),10);       

        //this.createMenu().set
        this.setJMenuBar(this.createMenu());


        toolbar = new ToolBarView();

        miniCal = new MiniCalendar();
        this.position = new Rectangle(340, 100, 600, 600);

        updateButton = new JButton("Update");
        

        CalendarDataModel model = new CalendarDataModel(Settings.getRepository());

        mvc = new MonthCalendar(model);
        toolbar.addObserver(mvc);

        dvc = new DayCalendar(model);
        toolbar.addObserver(dvc);

        //to make create event action listen to the notification
        toolbar.addObserver(this);

        miniCal.addChangeTypeListener(
                new MiniCalendar.ChangedEventListener() {

                    public void calendarChanged(MiniCalendar.ChangeTypeEventObject changeType) {
                        if (changeType.type == MiniCalendarChangeType.Day) {
//                            getGlassPane().setVisible(true);
//                            startOpeningThread();
//                            invalidate();

                            Calendar cal = miniCal.getCalendar();
                            int year = cal.get(Calendar.YEAR);
                            int month = cal.get(Calendar.MONTH) ;
                            mvc.loadCalendar(new CustomDate(year + "." + month + "." + "1,00:00"),
                                    new CustomDate(year + "." + month + "." + "31,23:59"));

                            selectedDateControl.setCalendar((Calendar)cal.clone());
                        }
                    }
                });

        model.addObserver(mvc);
        model.addObserver(dvc);

        this.getContentPane().setLayout(new BorderLayout());


        this.getContentPane().add(toolbar.getToolbar(), BorderLayout.NORTH);
        this.getContentPane().add(mvc, BorderLayout.CENTER);

        this.getContentPane().add(this.createSidePanel(), BorderLayout.WEST);

        //setting glassPane
        //m_glassPane = new GlassPane();
        //m_glassPane.setGlassPane(this);        

        this.setLocation(
                (int) Double.parseDouble(
                pref.getProperty("WindowPositionX", Integer.toString(this.position.x))),
                (int) Double.parseDouble(
                pref.getProperty("WindowPositionY", Integer.toString(this.position.y))));

        this.setSize(
                Integer.parseInt(
                pref.getProperty("WindowWidth", Integer.toString(this.position.width))),
                Integer.parseInt(
                pref.getProperty("WindowHeight", Integer.toString(this.position.height))));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void startOpeningThread() {
        Thread opener = new Thread(new Runnable() {

            public void run() {
                int i = 0;
                do {
                    try {
                        Thread.sleep(10 + (int) (Math.random() * MAX_DELAY));
                    } catch (InterruptedException ex) {
                    }
                    i += (int) (Math.random() * 5);
                    progressPane.setProgress(i);
                } while (i < 100);
                progressPane.setVisible(false);
                selectedDateControl.repaint();
                dateDisplayControl.repaint();
                progressPane.setProgress(0);
            }
        });
        opener.start();
    }

    /**
     * the function listens to the action performed by the toolbar
     * @param o
     * @param arg
     */
    public void update(Observable o, Object arg) {

        String actioncommand = arg.toString().toLowerCase();
        if (actioncommand.equalsIgnoreCase("toolbar_create")) {
            Settings.showDialog(Settings.getCreateTaskView());

        } else if (actioncommand.equalsIgnoreCase("toolbar_dayview")) {
            this.getContentPane().add(dvc, BorderLayout.CENTER);

            mvc.setVisible(false);
            //glasspanetest();
            dvc.setVisible(true);
            
        } else if (actioncommand.equalsIgnoreCase("toolbar_monthview")) {
            //glasspanetest();
            mvc.setVisible(true);
            dvc.setVisible(false);
        } else if (actioncommand.equalsIgnoreCase("toolbar_edit")) {
        }

    }

    private void glasspanetest() {
        getGlassPane().setVisible(true);
        Thread downloader = new Thread(new Runnable() {

            public void run() {

                int i = 0;
                do {
                    try {
                        Thread.sleep(10 + (int) (Math.random() * MAX_DELAY));
                    } catch (InterruptedException ex) {
                        // who cares here?
                    }
                    i += (int) (Math.random() * 15);
                    progressPane.setProgress(i);
                } while (i < 100);
                progressPane.setVisible(false);
                progressPane.setProgress(0);
            }
        });
        downloader.start();
    }

    /**
     * Creates menubar that is require for mainform
     * @param mainFrame
     */
    private JMenuBar createMenu() {

        //create menuitems
        MenuItemActionEvent menuEvent = new MenuItemActionEvent();

        JMenuBar menuBar = new JMenuBar();

        //FILE menu
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        //EDIT menu
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);


        //Settings Menu
        JMenu settingsMenu = new JMenu("Settings");
        menuBar.add(settingsMenu);
        //Help Menu
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        ToggleButtonMenuItem createNewEventItem = new ToggleButtonMenuItem("New", "./resources/add_menu.png");
        fileMenu.add(createNewEventItem);

        //fileMenu.
        createNewEventItem.addActionListener(menuEvent.newEventAction);

        ToggleButtonMenuItem exitEventItem = new ToggleButtonMenuItem("Exit", "./resources/exit_menu.png");

        fileMenu.add(exitEventItem);

        exitEventItem.addActionListener(menuEvent.exitAction);

        ToggleButtonMenuItem modifyEventMenuItem = new ToggleButtonMenuItem("Edit", "./resources/edit_menu.png");
        //modifyEventMenuItem.addActionListener(menuEvent.modifyEventAction);
        modifyEventMenuItem.setActionCommand("Edit");
        modifyEventMenuItem.addActionListener(toolbar);
        editMenu.add(modifyEventMenuItem);

        ToggleButtonMenuItem deleteEventMenuItem = new ToggleButtonMenuItem("Delete", "./resources/delete_menu.png");
        deleteEventMenuItem.setActionCommand("Remove");
        deleteEventMenuItem.addActionListener(toolbar);
        editMenu.add(deleteEventMenuItem);
        //deleteEventMenuItem.addActionListener(menuEvent.deleteAction);
        //Separator
        editMenu.addSeparator();

        ToggleButtonMenuItem undoMenuItem = new ToggleButtonMenuItem("Undo", "./resources/undo_menu.png");
        editMenu.add(undoMenuItem);
        undoMenuItem.addActionListener(menuEvent.undoAction);

        ToggleButtonMenuItem redoMenuItem = new ToggleButtonMenuItem("Redo", "./resources/redo_menu.png");
        editMenu.add(redoMenuItem);
        redoMenuItem.addActionListener(menuEvent.redoAction);

        ToggleButtonMenuItem eventCategoryMenuItem = new ToggleButtonMenuItem("EventCategory");
        settingsMenu.add(eventCategoryMenuItem);
        eventCategoryMenuItem.addActionListener(menuEvent.settingsEventCategoryAction);

        JMenu lookAndFeelMenu = new JMenu("Look & Feel");
        settingsMenu.add(lookAndFeelMenu);
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_L);
        ButtonGroup group = new ButtonGroup();
        if (this.looks != null) {
            for (int i = 0; i < this.looks.length; i++) {
                UIManager.LookAndFeelInfo look = this.looks[i];
                JRadioButtonMenuItem looknFeelMenuItem = new JRadioButtonMenuItem(look.getName(), true);
                looknFeelMenuItem.setToolTipText(look.getName());
                looknFeelMenuItem.addActionListener(new LookAndFeelActionListener(look));
                lookAndFeelMenu.add(looknFeelMenuItem);
                group.add(looknFeelMenuItem);
            }
        }
        settingsMenu.addSeparator();
        ToggleButtonMenuItem transparencyMenuItem = new ToggleButtonMenuItem("Transparency");
        transparencyMenuItem.setAction(menuEvent.settingsTransparencyAction);
        settingsMenu.add(transparencyMenuItem);


        final ToggleButtonMenuItem aboutMenuItem = new ToggleButtonMenuItem("About");
        helpMenu.add(aboutMenuItem);
        aboutMenuItem.addActionListener(menuEvent.aboutAction);

        howToMenuItem = new ToggleButtonMenuItem("How To");
        helpMenu.add(howToMenuItem);
        howToMenuItem.addActionListener(new CalendarUpdate());

        return menuBar;
    }

    /**
     * Creates the side panel where user is allowed to choose an option
     * @return side panel
     */
    private JPanel createSidePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        //Subpanel which lies on the panel created above
        JPanel subPanel = new JPanel();

        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
        subPanel.setBorder(BorderFactory.createTitledBorder("MiniCalendar:"));
        panel.add(subPanel, BorderLayout.NORTH);

        //miniCal=new MiniCalendar();

        subPanel.add(miniCal);

        subPanel.add(Box.createVerticalStrut(10));
        panel.add(this.createTaskDivisionPanel(), BorderLayout.CENTER);
        return panel;
    }

    /**
     * This is event type selection frame
     * @return JPanel
     */
    private JPanel createTaskDivisionPanel() {
        CalendarPanel panel = new CalendarPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel priorityPanel = new JPanel();
        priorityPanel.setLayout(new BoxLayout(priorityPanel, BoxLayout.LINE_AXIS));
        JButton highButton = new JButton("High");
        highButton.setActionCommand("High");
        highButton.setForeground(Color.red);
        highButton.setBorderPainted(false);
        highButton.setToolTipText("Select high priority tasks");
        highButton.addActionListener(new PriorityButtonAction(this.mvc));
        JButton mediumButton = new JButton("Medium");
        mediumButton.setActionCommand("Medium");
        mediumButton.setForeground(Color.black);
        mediumButton.setBorderPainted(false);
        mediumButton.setToolTipText("Select medium priority tasks");
        mediumButton.addActionListener(new PriorityButtonAction(this.mvc));
        JButton lowButton = new JButton("Low");
        lowButton.setActionCommand("Low");
        lowButton.setForeground(Color.blue);
        lowButton.setBorderPainted(false);
        lowButton.setToolTipText("Select low priority tasks");
        lowButton.addActionListener(new PriorityButtonAction(this.mvc));
        priorityPanel.add(highButton);
        priorityPanel.add(mediumButton);
        priorityPanel.add(lowButton);
        priorityPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(2),
                "Priority", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, Font.getFont("Serif"), Color.DARK_GRAY));
        priorityPanel.setOpaque(false);


        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.LINE_AXIS));
        Object[] items = CalendarEvent.getCategoryList();
        JComboBox categoryComboBox = new JComboBox(items);
        categoryComboBox.setToolTipText("Select evenets based upon category");
        categoryPanel.add(categoryComboBox);
        categoryPanel.setMaximumSize(new Dimension(220, 12));
        categoryPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(2), "Category", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, Font.getFont("Serif"), Color.LIGHT_GRAY));
        categoryPanel.setOpaque(false);

        panel.add(priorityPanel);
        panel.add(Box.createVerticalGlue());
        panel.add(categoryPanel);


        panel.setBorder(BorderFactory.createTitledBorder("Select Event"));
        return panel;
    }

    class CalendarUpdate implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(howToMenuItem)) {
                //toggle drawing
                //m_glassPane.setDrawing(true);
                //m_glassPane.setHandleMouseEvents(true);
            }

        }
    }
}
