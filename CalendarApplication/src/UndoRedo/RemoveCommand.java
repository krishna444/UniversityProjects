
package UndoRedo;

import DataStore.Repository;

/**
 * The command to remove an item from the repository
 * @author Rupesh Acharya
 */
public class RemoveCommand<T> implements ICommand
{
    /**
     * The repository to hold the items
     */
    private Repository<T> _repository;

    /**
     * The item that is to be replaced from the repository
     */
    private T _removedEvent;

    /**
     * Constructor to create a remove command.
     * @param repository The repository from where the item needs to be removed
     * @param event The item that needs to be removed
     */
    public RemoveCommand(Repository<T> repository, T event)
    {
        this._repository = repository;
        this._removedEvent = event;
    }

    /**
     * This executes the remove command
     */
    public void execute()
    {
        _repository.removeItem(_removedEvent);
        _repository.Commit();
    }

    /**
     * This reverts the changes made by the execution of this command
     */
    public void unExecute()
    {
        _repository.addNewItem(_removedEvent);
        _repository.Commit();
    }
}
