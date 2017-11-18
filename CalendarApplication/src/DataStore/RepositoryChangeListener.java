package DataStore;

import java.util.EventListener;

/**
 *
 * @author Rupesh
 */
public interface RepositoryChangeListener<T> extends EventListener
{
    public void itemAdded(RepositoryEventObject<T> item);
    public void itemUpdated(RepositoryEventObject<T> itemm);
    public void itemDeleted(RepositoryEventObject<T> item);
}
