package Model.Managers;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Abstract class designed for creating
 * and maintaining a collection of objects
 *
 * @author Matt Antantis
 */
abstract class Manager {

    Scanner reader;

    /**
     * Builds the collection of objects based off the implementation
     *
     * @throws FileNotFoundException if the file with the data is not found
     */
    private void buildList() throws FileNotFoundException {
    }

}
