package utility;

import model.Task;
import parsers.TaskParser;
import persistence.Jsonifier;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

// File input/output operations
public class JsonFileIO {
    public static final File jsonDataFile = new File("./resources/json/tasks.json");
    
    // EFFECTS: attempts to read jsonDataFile and parse it
    //           returns a list of tasks from the content of jsonDataFile
    public static List<Task> read() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("./resources/json/tasks.json"));
            String inputString = "";
            for (String line: lines) {
                inputString += line;
            }
            TaskParser parser = new TaskParser();
            return parser.parse(inputString);
        } catch (IOException e) {
            return null;
        }
    }
    
    // EFFECTS: saves the tasks to jsonDataFile
    public static void write(List<Task> tasks) {
        try {
            PrintWriter writer = new PrintWriter("./resources/json/tasks.json","UTF-8");
            String outputString = Jsonifier.taskListToJson(tasks).toString(3);
            writer.println(outputString);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
