package UndoRedo;

/**
 * An abstraction of a command which can be executed and unexecuted.
 * @author Rupesh Acharya
 * 
 */
public interface ICommand
{
    /**
     * Method to execute the command
     */
    public void execute();

    /**
     * Method to revert the action of the command execution
     */
    public void unExecute();
}
