package model.facades;

import model.boxes.Diagram;

public interface TemplateFacade {
    /**
     * Should find the right template to add to the canvas.
     */
    public void addTemplate();

    /**
     * Should save the diagram as a template
     * @param boxes The boxes on the canvas to save (boxtype and positions)
     */
    public void saveTemplate(Diagram boxes);
}
