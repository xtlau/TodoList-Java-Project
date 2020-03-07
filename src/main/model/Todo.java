package model;

import model.exceptions.EmptyStringException;

import java.util.Observable;

public abstract class Todo extends Observable{
        protected String description;
    protected int progress;
    protected int etcHours;  // Estimated Time To Complete
    protected Priority priority;

    // MODIFIES: this
    // EFFECTS: sets the "description" using the given description
    //          sets "progress" and "estimated time to complete" to zero
    // throws EmptyStringException if description is null or empty
    public Todo(String description) {
        // implement the constructor according to its specification.
        if (description == null || description.length() == 0) {
            throw new EmptyStringException("Cannot construct a project with no description");
        }
        this.description = description;
        progress = 0;
        etcHours = 0;
        priority = new Priority(4);
    }

    // EFFECTS: returns the description
    public String getDescription() {
        return description;
    }

    // EFFECTS: return a non-negative integer as the Estimated Time To Complete
    // Note: Estimated time to complete is a value that is expressed in
    //       hours of work required to complete a task or project.
    public abstract int getEstimatedTimeToComplete();

    // EFFECTS: returns an integer between 0 and 100 which represents
    //     the percentage of completion (rounded down to the closest integer).
    public abstract int getProgress();
}