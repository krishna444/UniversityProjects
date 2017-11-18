package monthviewapplication;

//import statements
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
* GlassPane.java
* This class demonstrates the use of GlassPane
*
* @author suman
*/
public class GlassPane extends JPanel implements MouseListener
{
    //RootPaneContainer interface
    private RootPaneContainer m_rootPane = null;

    //Stores the previous Glass Pane Component
    private Component m_prevGlassPane = null;
    private boolean m_handleMouseEvents = false;
    private boolean m_drawing = false;
    private JButton b_next;
    private JButton b_previous;
    private JButton b_exit;
    
    private String[] messageArray={
        "Create An Event : A new event can be created by two ways. Clicking on the 'Create Event' tool on the toolbar or File->New submenu",
        "Update An Event : Select the event by clicking on it and then click on the 'Edit' tool on toolbar. Alternatively, there is Edit->Edit submenu",
        "Delete An Event : Select the evnet to be deleted and then click on the 'Delete' tool on the toolbar. Alternatively, ther is Edit->Delete submenu",
        "Undo/Redo : Click on the 'Undo' tool on the toolbar, the last deleted event is revealed. Click on the 'Redo' tool on the toolbar, the last undo action is reverted",
        "Month View : Click on the 'Month View' tool on the toolbar to change into month view",
        "Day View : Click on the 'Day View' tool on the toolbar to change into day view",
        "Event Description : Hover the mouse over the event to display the detail of the event",
        "High/Medium/Low priority Events : Click on the High/Medium/Low on the left bar to highlight respective day cards in the month veiw"
    };
    private String message = messageArray[0];
    private int clickCount=0;
    /**
    * Constructor
    *
    * @param title
    * @exception
    */
    public GlassPane()
    {
    }


    private void init(){
        this.setLayout(new BorderLayout());
        ToolBarView tb=new ToolBarView();
        b_next = tb.buttonSetUp(b_next, "", "./resources/next.png");
        b_previous = tb.buttonSetUp(b_previous, "", "./resources/previous.png");
        b_exit = tb.buttonSetUp(b_exit, "", "./resources/exit_menu.png");

        JPanel navigatePanel = new JPanel(new BorderLayout());
        navigatePanel.add(b_previous, BorderLayout.WEST);
        navigatePanel.add(Box.createHorizontalGlue());
        navigatePanel.add(b_next, BorderLayout.EAST);
        
        this.add(Box.createHorizontalGlue());
        this.add(b_exit, BorderLayout.NORTH);

        b_exit.addActionListener(actionListener);
        b_next.addActionListener(actionListener);
        b_previous.addActionListener(actionListener);

        this.add(navigatePanel,BorderLayout.SOUTH);
        
        

        this.setBounds(new Rectangle(10, 10, 600, 600));
        setPreferredSize(new Dimension(200,200));
    }

    ActionListener actionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {

            if(ae.getSource().equals(b_next)){
               navigateGlassPane("next");
            }

            else if(ae.getSource().equals(b_previous)){
                navigateGlassPane("previous");
            }

            else if(ae.getSource().equals(b_exit))
            {
                removeGlassPane();
            }

        }

    };

    private void navigateGlassPane(String _navigate){

        if(_navigate.equalsIgnoreCase("next"))
            setClickCount(clickCount+1);

        else if(_navigate.equalsIgnoreCase("previous"))
            setClickCount(clickCount-1);

        setMessage(messageArray[getClickCount()]);
        repaint();
    }

    private void setClickCount(int _clickCount){

        if(_clickCount<0)
            clickCount = 0;

        else if(_clickCount>=messageArray.length)
            clickCount = messageArray.length-1;

        else
            clickCount = _clickCount;
    }

    private int getClickCount(){
        return clickCount;
    }

    //Mouse Events
    public void mousePressed(MouseEvent e)
    {
        //sound beep
        //Toolkit.getDefaultToolkit().beep();
    }

    public void mouseReleased(MouseEvent e)
    {
    }

    public void mouseClicked(MouseEvent e)//this rotates the events display
    {
          
        if(getClickCount()>=messageArray.length-1){
            setClickCount(0);
            setMessage(messageArray[getClickCount()]);
            repaint();
        }
        else
            navigateGlassPane("next");

    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

    public void setMessage(String _message){
        message = _message;
    }

    /**
    * Set the glassPane
    */
    public void setGlassPane(RootPaneContainer rootPane)
    {
        m_rootPane = rootPane;

        //store the current glass pane
        m_prevGlassPane = m_rootPane.getGlassPane();

        init();
        //set this as new glass pane
        m_rootPane.setGlassPane(this);
        //set opaque to false, i.e. make transparent
        setOpaque(false);
    }

    /**
    * remove the glassPane
    */
    public void removeGlassPane()
    {
        //set the glass pane visible false
        setVisible(false);
        
        //reset the previous glass pane
        //m_rootPane.setGlassPane(m_prevGlassPane);
    }

    /**
    * Set the handleMoveEvents
    * This will add the mouseListener and all the event will be
    * trapped my the glass pane
    */
    public void setHandleMouseEvents(boolean handleMouseEvents)
    {
        if (m_handleMouseEvents == handleMouseEvents)
        {
            //ignore if the state is same
            return;
        }

        m_handleMouseEvents = handleMouseEvents;

        if (m_handleMouseEvents)
        {
            //add this as mouse event listener and trap all the
            //mouse events
            addMouseListener(this);
        }
        else
        {

            //remove mouse events listener
            removeMouseListener(this);
        }

        //This is important otherwise the glass pane will no catch events
        setVisible(true);
    }

    /**
    * setDrawing
    *
    */
    public void setDrawing(boolean drawing)
    {
        m_drawing = drawing;

        //This is important otherwise the glass pane will not be visible
        setVisible(true);
        //Call repaint
        repaint();
    }

    /**
    * get the drawing state
    */
    public boolean getDrawing()
    {
        return m_drawing;
    }

    /**
    * Handling Paint
    */
    public void paint(Graphics g)
    {
        super.paint(g);

        if (m_drawing)
        {
            g.setColor( new Color(50, 50, 50, 128) );
            
            g.fillRoundRect(this.getParent().getX(), this.getParent().getY(),this.getWidth() , this.getHeight(), 40, 40);
            
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.setColor(Color.ORANGE);

            g.drawString(message, 110, 210);
        }
    }
}