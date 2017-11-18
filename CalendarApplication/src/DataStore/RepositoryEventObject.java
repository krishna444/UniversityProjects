package DataStore;

import java.util.EventObject;

/**
 *
 * @author Rupesh
 */
public class RepositoryEventObject<T> extends EventObject
{
    private T _newEventItem;
    private T _oldEventItem;

    public RepositoryEventObject(Object source)
    {
        this(source, null);
    }

    public RepositoryEventObject(Object source, T item)
    {
        this(source, null, item);
    }

    public RepositoryEventObject(Object source, T oldItem, T newItem)
    {
        super(source);
        _newEventItem = newItem;
        _oldEventItem = oldItem;
    }

    public T getCurrentEventItem()
    {
        return _newEventItem;
    }

    public T getOldEventItem()
    {
        return _oldEventItem;
    }
}
