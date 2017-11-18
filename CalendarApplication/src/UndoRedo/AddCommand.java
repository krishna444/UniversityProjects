

package UndoRedo;

import DataStore.Repository;

/**
 * This is a command to add item to the repository
 * @author Rupesh Acharya
 */
public class AddCommand<T> implements ICommand
{

    /**
     * The store that holds the items
     */
    private Repository<T> _repository;

    /**
     * The item event that is to be added to the repository
     */
    private T _addedEvent;

    /**
     * Constructor to create an object of this command
     * @param repository
     * @param event
     */
    public AddCommand(Repository<T> repository, T event)
    {
        this._repository = repository;
        this._addedEvent = event;
    }

    /**
     * Executes the command
     */
    public void execute()
    {
        _repository.addNewItem(_addedEvent);
        _repository.Commit();
    }

    /**
     * Reverts the action of the command execution
     */
    public void unExecute()
    {
        _repository.removeItem(_addedEvent);
        _repository.Commit();
    }
}
