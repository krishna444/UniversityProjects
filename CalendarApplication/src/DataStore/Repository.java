package DataStore;


/**
 * Generic Abstract Repository class.
 * This class is made generic so that it supports any kind of class T which is
 * determined in runtime. This helps to reuse this abstract class Repository in
 * many other applications as well. This class is made abstract class because we
 * can have different kinds of data storage like memory data store / xml data
 * store / database etc. So, this class abstracts those all classes of
 * repositories.
 * @author Rupesh
 */
public abstract class Repository<T>
{

    // Create the listener list
    protected javax.swing.event.EventListenerList listenerList =
        new javax.swing.event.EventListenerList();

    /**
     * THERE SHOULD BE A MECHANISM FOR ADDING NEW ITEMS INTO THE REPOSITORY.
     * THIS METHOD IS RESPONSIBLE FOR ADDING NEW ITEMS INTO DATA STORE
     * @param item
     */
    public abstract void addNewItem(T item);

    /**
     * THERE SHOULD BE A MECHANISM FOR UPDATING/EDITING AN ITEM
     * THIS METHOD IS RESPONSIBLE FOR UPDATING/EDITING AN ITEM
     * @param oldItem THE OLD ITEM THAT IS TO BE UPDATED
     * @param newItem THE NEW ITEM BY WHICH THE OLD ITEM IS TO BE REPLACED WITH
     */
    public abstract void updateItem(T oldItem, T newItem);
    
    /**
     * THERE SHOULD BE SOME MECHANISM FOR SELECTING ITEMS FROM THE DATA STORE
     * THERE CAN BE DIFFERENT CRITERIA(E.G. DATE/PRIORITY etc) FOR RETRIEVING
     * ITEMS FROM THE DATA STORE SO WE NEED A ITEMFETCHER TO FETCH A ITEM FROM
     * THE DATA STORE
     * @param query
     * @return
     */
    
    public abstract Iterable<T> getItem(ISearchQuery query);

    /**
     * THERE SHOULD BE SOME MECHANISM FOR REMOVING AN ITEM FROM THE DATASTORE
     * THIS METHOD IS RESPONSIBLE FOR REMOVING THE ITEMS FROM THE DATASTORE
     * THE METHOD TO REMOVE AN ARRAY OF ITEMS CAN BE
     * DECLARED IN THE CONCRETE CLASS BY MAKING CALLS TO THIS METHOD
     * @param item
     */
    public abstract void removeItem(T item);

    /**
     * FINALIZING FUNCTION. IN XML DATASTORE IT DUMPS THE MEMORY DOM IN THE FILE
     */
    public void Commit(){}

    // This methods allows classes to register for MyEvents
    public void addItemStoreChangedListener(RepositoryChangeListener<T> listener) {
        listenerList.add(RepositoryChangeListener.class, listener);
    }

       // This methods allows classes to unregister for MyEvents
    public void removeItemStoreChangedListener(RepositoryChangeListener<T> listener) {

        listenerList.remove(RepositoryChangeListener.class, listener);
    }

        // This private class is used to fire MyEvents
    void fireItemAdded(RepositoryEventObject evt) {
        Object[] listeners = listenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i=0; i<listeners.length; i+=2) {
            if (listeners[i]==RepositoryChangeListener.class) {
                ((RepositoryChangeListener)listeners[i+1]).itemAdded(evt);
            }
        }
    }
    // This private class is used to fire MyEvents
    void fireItemRemoved(RepositoryEventObject evt) {
        Object[] listeners = listenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i=0; i<listeners.length; i+=2) {
            if (listeners[i]==RepositoryChangeListener.class) {
                ((RepositoryChangeListener)listeners[i+1]).itemDeleted(evt);
            }
        }
    }

    // This private class is used to fire MyEvents
    void fireItemUpdated(RepositoryEventObject evt) {
        Object[] listeners = listenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i=0; i<listeners.length; i+=2) {
            if (listeners[i]==RepositoryChangeListener.class) {
                ((RepositoryChangeListener)listeners[i+1]).itemUpdated(evt);
            }
        }
    }
}