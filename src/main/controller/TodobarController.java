package controller;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.Task;
import ui.EditTask;
import ui.ListView;
import ui.PomoTodoApp;
import utility.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// Controller class for Todobar UI
public class TodobarController implements Initializable {
    private static final String todoOptionsPopUpFXML = "resources/fxml/TodoOptionsPopUp.fxml";
    private static final String todoActionsPopUpFXML = "resources/fxml/TodoActionsPopUp.fxml";
    private File todoActionsFxmlFile = new File(todoActionsPopUpFXML);
    private File todoOptionsPopUpFxmlFile = new File(todoOptionsPopUpFXML);


    @FXML
    private Label descriptionLabel;
    @FXML
    private JFXHamburger todoActionsPopUpBurger;
    @FXML
    private StackPane todoActionsPopUpContainer;
    @FXML
    private JFXRippler todoOptionsPopUpRippler;
    @FXML
    private StackPane todoOptionsPopUpBurger;

    private JFXPopup todobarPopUp;
    private JFXPopup viewPopUp;
    
    private Task task;
    
    // REQUIRES: task != null
    // MODIFIES: this
    // EFFECTS: sets the task in this Todobar
    //          updates the Todobar UI label to task's description
    public void setTask(Task task) {
        this.task = task;
        descriptionLabel.setText(task.getDescription());
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTodobarActionPopUp();
        loadTodobarPopUpActionListner();
        loadViewOptionsPopUp();
        loadViewOptionsPopUpActionListener();
    }

    private void loadTodobarActionPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoActionsFxmlFile.toURI().toURL());
            fxmlLoader.setController(new TodobarPopUpController());
            todobarPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadViewOptionsPopUp(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoOptionsPopUpFxmlFile.toURI().toURL());
            fxmlLoader.setController(new ViewOptionsPopUpController());
            viewPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTodobarPopUpActionListner() {
        todoOptionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                viewPopUp.show(todoOptionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.LEFT,
                        12,
                        15);
            }
        });
    }

    private void loadViewOptionsPopUpActionListener(){
        todoActionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                todobarPopUp.show(todoActionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.RIGHT,
                        -12,
                        15);
            }
        });
    }

    // Inner class: view selector pop up controller
    class ViewOptionsPopUpController {
        @FXML
        private JFXListView<?> optionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = optionPopUpList.getSelectionModel().getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    Logger.log("TodobarOptionsPopUpController", "edit task");
                    goToEditView();
                    break;
                case 1:
                    Logger.log("TodobarOptionsPopUpController", "delete task");
                    PomoTodoApp.getTasks().remove(task);
                    returnToListView();
                    break;
                default:
                    Logger.log("TodobarOptionsPopUpController", "No action is implemented for the selected option");
            }
            viewPopUp.hide();
        }
    }

    // Inner class: option pop up controller
    class TodobarPopUpController {
        @FXML
        private JFXListView<?> actionPopUpList;

        @FXML
        private void submit() {
            int selectedIndex = actionPopUpList.getSelectionModel().getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    Logger.log("TodobarActionsPopUpController", "To Do Selected");
                    break;
                case 1:
                    Logger.log("TodobarActionsPopUpController", "Up Next Selected");
                    break;
                case 2:
                    Logger.log("TodobarActionsPopUpController", "In Progress Selected");
                    break;
                case 3:
                    Logger.log("TodobarActionsPopUpController", "Done Selected");
                    break;
                default:
                    Logger.log("TodobarActionsPopUpController", "Pomodoro Selected");
            }
            todobarPopUp.hide();
        }
    }

    // EFFECTS: return to the list view UI
    private void returnToListView() {
        Logger.log("TodobarController", "Return to the list view UI.");
        PomoTodoApp.setScene(new ListView(PomoTodoApp.getTasks()));
    }

    private void goToEditView() {
        Logger.log("TodobarController", "go to the edit view UI.");
        PomoTodoApp.setScene(new EditTask(task));
    }
}
