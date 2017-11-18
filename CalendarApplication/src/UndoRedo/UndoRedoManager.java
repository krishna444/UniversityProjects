
package UndoRedo;

import java.util.Stack;

/**
 * This class stores the command history so that later it can be undo/redo.
 * This class supports multilevel undo/redo.
 * This is a singleton class so the command history is global
 * @author Admin
 */
public class UndoRedoManager
{
    /**
     * The stack where the executed commands are stored
     */
    private Stack<ICommand> _undoCommands;

    /**
     * The stack where the unexecuted commands are stored
     */
    private Stack<ICommand> _redoCommands;

    /**
     * The variable to hold the single instance of this undo/redo manager.
     */
    private static UndoRedoManager _undoRedoManager;

    /**
     * Constructor to create the undo/redo manager class.
     */
    private UndoRedoManager()
    {
        _undoCommands = new Stack<ICommand>();
        _redoCommands = new Stack<ICommand>();
    }

    /**
     * This method returns the existing instance of undo/redo manager class.
     * If it is not instantiated earlier then a new instance of this class is
     * returned otherwise the existing instance of this class is returned.
     * @return Returns the existing/new instance of this class
     */
    public static UndoRedoManager getInstance()
    {
        if(_undoRedoManager == null)
        {
            _undoRedoManager = new UndoRedoManager();
        }

        return _undoRedoManager;
    }

    /**
     * Issue a redo command which  redoes the last series of undo command executes.
     * @param commandCount The number of redo operation to carryout level.
     */
    public void redo(int commandCount)
    {
        for(int i = 0; i < commandCount; i++)
        {
            if(_redoCommands.isEmpty())
            {
                break;
            }

            ICommand command = _redoCommands.pop();
            command.execute();
            _undoCommands.push(command);
        }
    }


    /**
     * Issue a undo command which undoes the series of command executed.
     * @param commandCount The number of undo operation to carryout.
     */
    public void undo(int commandCount)
    {
        for(int i = 0; i < commandCount; i++)
        {
            if(_undoCommands.isEmpty())
            {
                break;
            }

            ICommand command = _undoCommands.pop();
            command.unExecute();
            _redoCommands.push(command);
        }
    }

    /**
     * An interface to add the undoable command to the history
     * @param command The command to put in the command execution history
     */
    public void addUndoableCommand(ICommand command)
    {
        _undoCommands.add(command);
        _redoCommands.clear();
    }

}