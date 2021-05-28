package model.facades;

/**
 * An exercise is a set of different tasks that the user can do to learn.
 */
public interface ExerciseFacade {

    /**
     * Returns the name of this exercise
     * @return
     */
    String getName();

    /**
     * Starts this exercise
     */
    void startExercise();
}
