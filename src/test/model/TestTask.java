package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTask {

    Task task;

    @BeforeEach
    void runBefore () {
        String description = "Register for the course. ## cpsc210; tomorrow; important; urgent; in progress";
        task = new Task(description);
    }

}