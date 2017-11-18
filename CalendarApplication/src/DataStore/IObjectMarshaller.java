package DataStore;

/**
 * THIS IS A GENERIC INTERFACE THAT SIGNIFIES THAT AN OBJECT IMPLEMENTING THIS
 * CAN BE MARSHALLED TO AN XML DOM ELEMENT
 * THIS INTERFACE IS JDOM SPECIFIC
 * SO NEED TO BE CHANGED IF WE MAKE A DECISION NOT TO USE JDOM
 * SO BETTER TO REWRITE IT
 * @author Rupesh
 */
public interface IObjectMarshaller<T>
{
    /**
     * PROVIDES THE ROOT ELEMENT OF THE OBJECT TO BE MARSHALLED
     */
    public String getRootElement();

    /**
     * MARSHALLS THE OBJECT INTO JDOM ELEMENT
     * @param item
     * @return
     */
    public org.jdom.Element marshall(T item);

    /**
     * UNMARSHALLS THE JDOM ELEMENT TO THE OBJECT BACK
     * @param element
     * @return
     */
    public T unmarshall(org.jdom.Element element);

    /**
     * Update property of the item
     * @param item item to be updated
     * @param propertyName name of the property
     * @param value new value of the property
     * @return item updated item
     */
    public T updateItemProperty(T item, String propertyName, Object value);

    /**
     * COMPARES THE JDOM NODE AND THE GENERIC OBJECT T IF THEY ARE EQUIVALENT
     * OR THE JDOM ELEMENT IS OF THE PROVIDED OBJECT T
     * @param t1
     * @param t2
     * @return
     */
    public boolean JDOMElementComparer(org.jdom.Element t1, org.jdom.Element t2);
}
