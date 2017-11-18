package DataStore;

import TodoManager.CalendarEvent;
import TodoManager.CustomDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Element;

/**
 * CalendarEventMarshaller class serves for marshalling/unmarshalling purpose of Task
 * object into/from XML file
 * @author Rupesh
 * @author volkan cambazoglu
 */
public class CalendarEventMarshaller implements IObjectMarshaller<CalendarEvent> {

    /**
     * THIS METHOD MARSHALLS THE OBJECT TASK INTO JDOM ELEMENT OBJECT
     * @param item task object to be marshalled
     * @return element JDOM element
     */
    public Element marshall(CalendarEvent item){
        Element elm = new Element("Event");
        elm.addContent(new Element("Title").addContent(item.getTitle()));
        elm.addContent(new Element("Category").addContent(
                                                  item.getCategory()));
        elm.addContent(new Element("Description").addContent(
                                                       item.getDescription()));
        elm.addContent(new Element("Priority").addContent(
                                                  item.getPriority()));
        elm.addContent(new Element("BeginDate").addContent(
                                              item.getBeginDate().toString()));
        elm.addContent(new Element("EndDate").addContent(
                                                item.getEndDate().toString()));
        elm.addContent(new Element("Status").addContent(
                								item.getStatus()));
        return elm;
    }

    /**
     * THIS METHOD UNMARSHALLS THE XML ELEMENT BACK TO THE TASK OBJECT
     * IT MUST THROW SOME EXCEPTION IF IT CANNOT CONVERT THE ELEMENT TO THE TASK
     * OBJECT OR MAY SAY THAT THE ELEMENT IS INVALID. MIGHT BE NEEDED TO 
     * VALIDATE AGAINS SOME DTD
     * @param element THIS IS THE JDOM ELEMENT
     * @return task unmarshalled task object
     */
    public CalendarEvent unmarshall(Element element){
        CalendarEvent task = new CalendarEvent(
                            new CustomDate(element.getChild("BeginDate").getValue()),
                            new CustomDate(element.getChild("EndDate").getValue()),
                            element.getChild("Title").getValue(),
                            element.getChild("Description").getValue(),
                            element.getChild("Category").getValue(),
                            element.getChild("Priority").getValue(),
                            element.getChild("Status").getValue());
        return task;
    }

    /**
     * Getter for root element
     * @return "Events" without quotes
     */
    public String getRootElement(){
        return "Events";
    }    

    /**
     * Compares two JDOM elements
     * @param t1 first element
     * @param t2 second element
     * @return true if yes, else false
     */
    public boolean JDOMElementComparer(Element t1, Element t2){
        if(t1.getChild("Title").getValue().equals(
                t2.getChild("Title").getValue()) &&
           t1.getChild("Description").getValue().equals(
                t2.getChild("Description").getValue()) &&
           t1.getChild("Category").getValue().equals(
                t2.getChild("Category").getValue()) &&
           t1.getChild("Priority").getValue().equals(
                t2.getChild("Priority").getValue()) &&
           t1.getChild("BeginDate").getValue().equals(
                t2.getChild("BeginDate").getValue()) &&
           t1.getChild("EndDate").getValue().equals(
                t2.getChild("EndDate").getValue())){

            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Update property of the item
     * @param item item to be updated
     * @param propertyName name of the property
     * @param value new value of the property
     * @return task updated task
     */
    public CalendarEvent updateItemProperty(CalendarEvent item, String propertyName, Object value) {
        CalendarEvent result = null;
        try {
            result = item.Clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(CalendarEventMarshaller.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(propertyName.equalsIgnoreCase("Priority")){
        	result.setPriority(Integer.parseInt(value.toString()));
        }

        return result;
    }
}
