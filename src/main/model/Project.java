package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;

import java.util.*;

// Represents a Project, a collection of zero or more Tasks
// Class Invariant: no duplicated task; order of tasks is preserved
//TODO:
public class Project extends Todo implements Iterable<Todo>, Observer{
    private List<Todo> tasks;
    
    // MODIFIES: this
    // EFFECTS: constructs a project with the given description
    //     the constructed project shall have no tasks.
    //  throws EmptyStringException if description is null or empty
    public Project(String description) {
        super(description);
        tasks = new ArrayList<>();
    }
    
    // MODIFIES: this
    // EFFECTS: task is added to this project (if it was not already part of it)
    //   throws NullArgumentException when task is null
    //TODO:
    public void add(Todo task) {
        if (task == this) {
            return;
        }
        if (!contains(task)) {
            task.addObserver(this);
            etcHours += task.etcHours;
            tasks.add(task);
        }
    }
    
    // MODIFIES: this
    // EFFECTS: removes task from this project
    //   throws NullArgumentException when task is null
    public void remove(Todo task) {
        if (contains(task)) {
            tasks.remove(task);
        }
    }
    
    // EFFECTS: returns the description of this project
    public String getDescription() {
        return description;
    }

    @Override
    //TODO
    public int getEstimatedTimeToComplete() {
        return etcHours;
    }

    // EFFECTS: returns an unmodifiable list of tasks in this project.
    @Deprecated
    public List<Task> getTasks() {
        throw new UnsupportedOperationException();
    }

    // EFFECTS: returns an integer between 0 and 100 which represents
    //     the percentage of completion (rounded down to the nearest integer).
    //     the value returned is the average of the percentage of completion of
    //     all the tasks and sub-projects in this project.
    public int getProgress() {
        int progress = 0;
        for (Todo todo: tasks) {
            progress += todo.getProgress();
        }
        return (int)Math.floor(progress * 1.0 / tasks.size());
    }

    // EFFECTS: returns the number of tasks (and sub-projects) in this project
    public int getNumberOfTasks() {
        return tasks.size();
    }

    // EFFECTS: returns true if every task (and sub-project) in this project is completed, and false otherwise
    //     If this project has no tasks (or sub-projects), return false.
    public boolean isCompleted() {
        return getNumberOfTasks() != 0 && getProgress() == 100;
    }
    
    // EFFECTS: returns true if this project contains the task
    //   throws NullArgumentException when task is null
    public boolean contains(Todo task) {
        if (task == null) {
            throw new NullArgumentException("Illegal argument: task is null");
        }
        return tasks.contains(task);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(description, project.description);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    public Iterator<Todo> iterator() {
        return new ProjectIterator();
    }

    //TODO
    @Override
    public void update(Observable o, Object arg) {
        // project may have super-project, so need to notify its super-project
        etcHours += (int)arg;
        setChanged();
        notifyObservers(arg);
    }


    class ProjectIterator implements Iterator<Todo> {
        private int cursor = 0;
        private int priorityLevel = 1;
        private int numOfElementsRemaining = tasks.size();

        @Override
        public boolean hasNext() {
            return numOfElementsRemaining != 0;
        }

        @Override
        public Todo next() {
            while (hasNext()) {
                Priority priority = new Priority(priorityLevel);
                while (cursor < tasks.size()) {
                    Todo todo = tasks.get(cursor);
                    ++ cursor;
                    if (todo.priority.equals(priority)) {
                        numOfElementsRemaining --;
                        return todo;
                    }
                }
                cursor = 0;
                ++ priorityLevel;
            }
            return null;
        }
    }
}