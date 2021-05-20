package model.facades;

import model.diagram.Diagram;

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
     * Forcibly changes the name of the active diagram and saves it
     * @param name The new name of the diagram
     */
    void saveAs(String name);

    void deleteFile(String name);

    /**
     * Creates a new empty file and loads it
     */
    void newFile();

    String[] getAllTemplateNames();
    /**
     * Should find the right template to add to the canvas.
     * @param name The name of the template to be added.
     */
    void loadTemplate(String name);

    /**
     * Should save the active diagram as a template
     * @param name The name that the diagram should be saved as.
     */
    void saveTemplate(String name);

    void deleteTemplate(String name);
}
