package model.facades;

public interface MethodFacade extends AttributeFacade{

    /**
     * Returns all of the parameters of this method
     * @return
     */
    String[] getParameters();

    /**
     * Sets all of the parameters of the method.
     * If the method already has parameters those are first cleared.
     * @param parameters
     */
    void setParameters(String[] parameters);
}
