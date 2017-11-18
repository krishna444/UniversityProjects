
package Actions;

import UndoRedo.UndoRedoManager;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

/**
 *
 * @author Admin
 */
public class UndoRedoAction<T> extends TodoManagerAction
{

    public UndoRedoAction(String text, ImageIcon object, int vkY) {
        super(text,object);
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equalsIgnoreCase("undo"))
        {
            UndoRedoManager.getInstance().undo(1);
        }
        else if(e.getActionCommand().equalsIgnoreCase("redo"))
        {
            UndoRedoManager.getInstance().redo(1);
        }
    }
}
