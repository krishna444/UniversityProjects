package UndoRedo;

import DataStore.Repository;

/**
 * This is the command to edit an item in the repository
 * @author Rupesh Acharya
 */
public class EditCommand<T> implements ICommand
{
    /**
     * The store that holds the items
     */
    private Repository<T> _repository;

    /**
     * The old item that is to be updated.
     */
    private T _editedOldEvent;

    /**
     * The item by which the old item needs to be replaced.
     */
    private T _editedByNewEvent;

    /**
     * Constructor to create an edit command
     * @param repository The store where the item needs to be updated
     * @param oldEvent The item to be edited
     * @param newEvent The item to be updated by
     */
    public EditCommand(Repository<T> repository, T oldEvent, T newEvent)
    {
        this._repository = repository;
        this._editedOldEvent = oldEvent;
        this._editedByNewEvent = newEvent;
    }

    /**
     * Executes the edit command
     */
    public void execute()
    {
        _repository.updateItem(_editedOldEvent, _editedByNewEvent);
        _repository.Commit();
    }

    /**
     * Reverts the action done by the execute command
     */
    public void unExecute()
    {
        _repository.updateItem(_editedByNewEvent, _editedOldEvent);
        _repository.Commit();
    }
}
