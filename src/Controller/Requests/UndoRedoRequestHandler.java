package Controller.Requests;

import Controller.Server.Server;

import java.util.Stack;

/**
 * @author Ian Randman
 * @author Matt Antantis
 */
public class UndoRedoRequestHandler {

    private final static String ERROR_MESSAGE = "error,no request available";

    private Stack<UndoableRequest> undoStack;
    private Stack<UndoableRequest> redoStack;

    /**
     * The constructor for the UndoRedoRequestHandler
     */
    public UndoRedoRequestHandler() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    /**
     * Adds a request to the stack of undoable requests
     *
     * @param request the command to add to the undo stack
     */
    public void addRequest(UndoableRequest request) {
        undoStack.push(request);
    }

    /**
     * Undoes the most recent command in the undo stack
     * and adds it to the redo stack
     *
     * @return a message containing the status of the undo action
     */
    public String undo() {
        if (!undoStack.empty()) {
            UndoableRequest request = undoStack.pop();
            redoStack.push(request);
            return Server.UNDO_REQUEST + "," + request.undo();
        }

        return ERROR_MESSAGE;
    }

    /**
     * Performs the most recent command in the redo stack
     * and adds it to the undo stack
     *
     * @return a message containing the status of the redo action
     */
    public String redo() {
        if (!redoStack.empty()) {
            UndoableRequest request = redoStack.pop();
            undoStack.push(request);
            return Server.REDO_REQUEST + "," + request.execute();
        }

        return ERROR_MESSAGE;
    }
}
