package model.facades;

public interface FileHandlerFacade {

    /**
     * Returns the names of all the files in the system.
     * Preferably the files are sent in order of when they where made with the newest file first in the array.
     * @return
     */
    String[] getAllFileNames ();

    /**
     * Loads the system with the file with this name.
     * If the file doesn't exist right now don't do anything.
     * @param fileName The name of the file we want to load
     */
    void loadFile(String fileName);

    /**
     * Creates a new empty file and loads it
     */
    void newFile();
}
