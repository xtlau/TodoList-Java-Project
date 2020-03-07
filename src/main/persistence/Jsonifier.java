package persistence;


import model.DueDate;
import model.Priority;
import model.Tag;
import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

// Converts model elements to JSON objects
public class Jsonifier {

    // EFFECTS: returns JSON representation of tag
    public static JSONObject tagToJson(Tag tag) {
        JSONObject tagJson = new JSONObject();
        tagJson.put("name", tag.getName());
        return tagJson;
    }

    // EFFECTS: returns JSON representation of priority
    public static JSONObject priorityToJson(Priority priority) {
        JSONObject priorityJson = new JSONObject();
        priorityJson.put("important", priority.isImportant());
        priorityJson.put("urgent", priority.isUrgent());
        return priorityJson;
    }

    // EFFECTS: returns JSON respresentation of dueDate
    public static JSONObject dueDateToJson(DueDate dueDate) {
        JSONObject dueDateJson = new JSONObject();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dueDate.getDate());
        dueDateJson.put("year", calendar.get(Calendar.YEAR));
        dueDateJson.put("month", calendar.get(Calendar.MONTH));
        dueDateJson.put("day", calendar.get(Calendar.DAY_OF_MONTH));
        dueDateJson.put("hour", calendar.get(Calendar.HOUR_OF_DAY));
        dueDateJson.put("minute", calendar.get(Calendar.MINUTE));
        return dueDateJson;
    }

    // EFFECTS: returns JSON representation of task
    public static JSONObject taskToJson(Task task) {
        JSONObject taskJson = new JSONObject();
        taskJson.put("description", task.getDescription());
        JSONArray tagsArray = new JSONArray();
        for (Tag tag: task.getTags()) {
            tagsArray.put(tagToJson(tag));
        }
        taskJson.put("tags", tagsArray);
        if (task.getDueDate() == null) {
            taskJson.put("due-date", JSONObject.NULL);
        } else {
            taskJson.put("due-date", dueDateToJson(task.getDueDate()));
        }
        taskJson.put("priority", priorityToJson(task.getPriority()));
        taskJson.put("status", task.getStatus());
        return taskJson;
    }

    // EFFECTS: returns JSON array representing list of tasks
    public static JSONArray taskListToJson(List<Task> tasks) {
        JSONArray taskListJson = new JSONArray();
        for (Task task: tasks) {
            taskListJson.put(taskToJson(task));
        }
        return taskListJson;
    }
}
