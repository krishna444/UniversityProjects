package DataStore;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jaxen.JaxenException;
import org.jaxen.jdom.JDOMXPath;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 * CLASS RESPONSIBLE FOR HANDLING XML DATA STORE
 * THIS CLASS ASSUMES THAT THERE WILL BE A XML FILE SOMEWHERE IN DISK
 * SO IT NEEDS TO USE THAT XML FILE FOR READING AND WRITING THE DATA
 * IT USES JDOM OBJECT FOR THE MANIPULATION AND LATER IT UPDATES THE
 * CHANGES MADE TO DOM TO DISK UPON THE ISSUE OF COMMIT COMMAND
 * @author Rupesh
 */
public class xmlDataRepository<T> extends Repository<T> {

    private String XmlFilePath;
    private boolean MemoryAndDataStoreSynchronized;
    private Document DOMDocument;
    private IObjectMarshaller<T> ItemMarshaller;

    /**
     * Constructor. creates an instance of xmlDataRepository class.
     * @param filepath file path of the xml
     * @param marshaller marshaller that marshalls the xml file
     * @throws JDOMException
     * @throws java.io.IOException
     */
    public xmlDataRepository(String filepath, IObjectMarshaller<T> marshaller)
            throws JDOMException, java.io.IOException {

        //NEED TO THROW FILE NOT FOUND EXCEPTION
        XmlFilePath = filepath;
        //THIS VARIABLE IS USED TO TRACK WHETHER THE DOM IN MEMORY HAS CHANGED
        //OR NOT. OR IN OTHERWORDS TO CHECK IF BOTH THE DISK XML FILE AND DOM
        //ARE IN SYNC OR NOT.
        MemoryAndDataStoreSynchronized = true;

        File xmlfile = new File(filepath);

        //IF THE XML FILE EXISTS IN THE LOCATION PROVIDED THEN BUILD THE DOM
        //BY PARSING THAT XML FILE OTHERWISE CREATE AN EMPTY DOM.
        if (xmlfile.exists()) {
            DOMDocument = new SAXBuilder().build(filepath);
        } else {
            DOMDocument = new Document();
            Element root = new Element(marshaller.getRootElement());
            DOMDocument.addContent(root);
            MemoryAndDataStoreSynchronized = false;
        }
        ItemMarshaller = marshaller;
    }

    /**
     * THERE SHOULD BE A MECHANISM FOR ADDING NEW ITEMS INTO THE REPOSITORY.
     * THIS METHOD IS RESPONSIBLE FOR ADDING NEW ITEMS INTO DATA STORE
     * @param item ITEM TO ADD IN THE DOM
     */
    public void addNewItem(T item) {
        DOMDocument.getRootElement().addContent(ItemMarshaller.marshall(item));
        MemoryAndDataStoreSynchronized = false;
        fireItemAdded(new RepositoryEventObject(this,item));
    }

    /**
     * THERE SHOULD BE A MECHANISM FOR UPDATING/EDITING AN ITEM
     * THIS METHOD IS RESPONSIBLE FOR UPDATING/EDITING AN ITEM
     * @param item
     */
    public void updateItem(T oldItem, T newItem) {
        Element itemelement = ItemMarshaller.marshall(oldItem);
        Element iteratedelement;
        for (Iterator i = DOMDocument.getRootElement().getChildren().iterator();
                i.hasNext();)
        {
            iteratedelement = (Element) i.next();

            if (ItemMarshaller.JDOMElementComparer(iteratedelement, itemelement)) {
                int index = DOMDocument.getRootElement().indexOf(iteratedelement);
                iteratedelement.detach();
                DOMDocument.getRootElement().addContent(index, ItemMarshaller.marshall(newItem));
                MemoryAndDataStoreSynchronized = false;
                fireItemUpdated(new RepositoryEventObject(this, oldItem, newItem));
                break;
            }
        }
    }

    /**
     * THERE SHOULD BE SOME MECHANISM FOR SELECTING ITEMS FROM THE DATA STORE
     * THERE CAN BE DIFFERENT CRITERIA(E.G. DATE/PRIORITY etc) FOR RETRIEVING
     * ITEMS FROM THE DATA STORE
     * SO WE NEED A ITEMFETCHER TO FETCH A ITEM FROM THE DATA STORE
     * @param query
     * @return
     */
    public Iterable<T> getItem(ISearchQuery query) {

        List<T> items = new ArrayList<T>();

        org.jaxen.jdom.JDOMXPath path = null;

        try {
            path = new JDOMXPath(query.getFinalQuery());
        } catch (JaxenException ex) {
            Logger.getLogger(xmlDataRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            for (Object obj : path.selectNodes(DOMDocument)) {
                items.add((T) ItemMarshaller.unmarshall((Element) obj));
            }
        } catch (JaxenException ex) {
            Logger.getLogger(xmlDataRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return items;
    }

    /**
     * THERE SHOULD BE SOME MECHANISM FOR REMOVING AN ITEM FROM THE DATASTORE
     * THIS METHOD IS RESPONSIBLE FOR REMOVING THE ITEMS FROM THE DATASTORE
     * THE METHOD TO REMOVE AN ARRAY OF ITEMS CAN BE
     * DECLARED IN THE CONCRETE CLASS BY MAKING CALLS TO THIS METHOD
     * @param item
     */
    public void removeItem(T item) {
        Element itemelement = ItemMarshaller.marshall(item);
        for (Object child : DOMDocument.getRootElement().getChildren())
        {
            if (ItemMarshaller.JDOMElementComparer((Element) child, itemelement))
            {
                boolean success = DOMDocument.getRootElement().removeContent(
                        (Element) child);
                MemoryAndDataStoreSynchronized = false;
                fireItemRemoved(new RepositoryEventObject(this,item));
                break;
            }
        }
        
    }

    /**
     * Commits the changes to the XML file
     */
    @Override
    public void Commit() {
        //NEED TO WRITE THE FUNCTION TO DUMP THE XML TO THE DISK IN THE SPECIFIED
        //FILEPATH
        try {
            if (!MemoryAndDataStoreSynchronized) {
                File datafile = new File(XmlFilePath);
                if (!datafile.getParentFile().exists()) {
                    datafile.getParentFile().mkdirs();
                }
                org.jdom.output.XMLOutputter out = new XMLOutputter();
                out.output(DOMDocument, new FileOutputStream(XmlFilePath));
            }
        } catch (Exception e) {
        }
    }

    /**
     * Updates property of the item
     * @param item item to be updated
     * @param propertyName name of the property
     * @param value new value of the property
     * @return item updated item
     */
    public T updateItemProperty(T item, String propertyName, Object value) {
        T newItem = ItemMarshaller.updateItemProperty(item, propertyName, value);
        updateItem(item, newItem);
        MemoryAndDataStoreSynchronized = false;
        return newItem;
    }
}
