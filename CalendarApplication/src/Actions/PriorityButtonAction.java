
package Actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import monthviewapplication.DayCard;
import monthviewapplication.MonthCalendar;
import java.awt.Color;
import javax.swing.BorderFactory;
import monthviewapplication.CalendarEventCard;

/**
 * It is the action handler when we click on the priority buttons
 * @author krishna
 */
public class PriorityButtonAction extends AbstractAction{
    private MonthCalendar monthCalendar;
    public PriorityButtonAction(MonthCalendar monthCalendar) {
        this.monthCalendar=monthCalendar;
    }
    
    public void actionPerformed(ActionEvent e){
        JButton button=(JButton)e.getSource();
        String actionCommand=button.getActionCommand();
        System.out.println("ActionCommand:"+actionCommand);
        if(actionCommand.equals("High")){
            for(DayCard card:this.monthCalendar.getDayCardList()){
                card.stopAnimation();
                int size=card.getAddedEvents().size();
                for(int i=0;i<size;i++){
                    if(((CalendarEventCard)card.getAddedEvents().get(i)).getEvent().getPriority().equals("High")) {
                        //Animate here
                        card.startAnimation();
                    }
                }
                
                
            }
        }
        else if(actionCommand.equals("Medium")){
            for(DayCard card:this.monthCalendar.getDayCardList()){
                card.stopAnimation();
                int size=card.getAddedEvents().size();
                for(int i=0;i<size;i++){
                    if(((CalendarEventCard)card.getAddedEvents().get(i)).getEvent().getPriority().equals("Medium")){
                        //Animate here
                        card.startAnimation();
                    }
                }
                
                
            }
            
        }
        else if(actionCommand.equals("Low")){
            for(DayCard card:this.monthCalendar.getDayCardList()){
                card.stopAnimation();
                int size=card.getAddedEvents().size();
                for(int i=0;i<size;i++){
                    if(((CalendarEventCard)card.getAddedEvents().get(i)).getEvent().getPriority().equals("Low")){
                        //Animate here
                        card.startAnimation();
                    }
                }
            }           
        }
    }
}
