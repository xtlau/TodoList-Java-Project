package parsers;

import model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

// Represents Task parser
public class TaskParser {

    // EFFECTS: iterates over every JSONObject in the JSONArray represented by the input
    // string and parses it as a task; each parsed task is added to the list of tasks.
    // Any task that cannot be parsed due to malformed JSON data is not added to the
    // list of tasks.
    // Note: input is a string representation of a JSONArray
    public List<Task> parse(String input) {
        JSONArray taskArray = new JSONArray(input);
        List<Task> tasks = new ArrayList<>();
        for (Object object: taskArray) {
            Task task = parseTask((JSONObject) object);
            if (task != null) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    // parse single Task JSONObject
    private Task parseTask(JSONObject taskJson) {
        try {
            String description = taskJson.getString("description");
            Set<Tag> tagSet = parseTags(taskJson.getJSONArray("tags"));
            DueDate dueDate = null;
            if (!taskJson.isNull("due-date")) {
                dueDate = parseDueDate(taskJson.getJSONObject("due-date"));
            }
            Priority priority = parsePriority(taskJson.getJSONObject("priority"));
            Status status = parseStatus(taskJson.getString("status"));
            Task task = new Task(description);
            for (Tag tag: tagSet) {
                task.addTag(tag);
            }
            task.setDueDate(dueDate);
            task.setPriority(priority);
            task.setStatus(status);
            return task;
        } catch (RuntimeException e) {
            return null;
        }
    }

    private Set<Tag> parseTags(JSONArray tagArray) {
        Set<Tag> tagSet = new HashSet<>();
        for (Object object: tagArray) {
            JSONObject tagObject = (JSONObject) object;
            String name = tagObject.getString("name");
            Tag tag = new Tag(name);
            tagSet.add(tag);
        }
        if (tagSet.size() == 0)
            throw new RuntimeException();
        return tagSet;
    }

    private DueDate parseDueDate(JSONObject dueDateObject) {
        int year = dueDateObject.getInt("year");
        int month = dueDateObject.getInt("month");
        int day = dueDateObject.getInt("day");
        int hour = dueDateObject.getInt("hour");
        int minute = dueDateObject.getInt("minute");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        DueDate dueDate = new DueDate(calendar.getTime());
        return dueDate;
    }

    private Priority parsePriority(JSONObject priorityObject) {
        boolean important = priorityObject.getBoolean("important");
        boolean urgent = priorityObject.getBoolean("urgent");
        Priority priority = new Priority();
        priority.setImportant(important);
        priority.setUrgent(urgent);
        return priority;
    }

    private Status parseStatus(String statusString) {
        switch (statusString) {
            case "IN_PROGRESS":
                return Status.IN_PROGRESS;
            case "UP_NEXT":
                return Status.UP_NEXT;
            case "TODO":
                return Status.TODO;
            case "DONE":
                return Status.DONE;
            default:
                return null;
        }
    }

}
