package Controller.Requests;

/**
 * Interface for requests that can be undone
 *
 * @author Ian Randman
 */
public interface UndoableRequest extends Request {
    String undo();
}
