package sample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import sample.classes.Course;
import sample.classes.Lecturer;

public class lecDashController extends Controller implements Initializable {

    private Lecturer lectDetails;

    @FXML
    private TextField name, nric, staffID;

    @FXML
    private JFXButton editInfoButton, saveButton, contactButton;

    @FXML
    private ChoiceBox<String> emp_status, position, school, campus;

    @FXML
    private Text Message;

    static String id;

    public void LogOut(MouseEvent event) throws IOException {
        switchTo(event, "logout.fxml");
    }

    public void StuList(MouseEvent event) throws IOException {
        stuListController.lecID = Integer.toString(lectDetails.getID());
        switchTo(event, "stuList.fxml");
    }

    public void ContactUs(MouseEvent event) throws IOException {
        switchTo(event, "lecReport.fxml");
    }

    public void openBrowser() throws URISyntaxException, IOException {
        openLink();
    }

    public static void getID(String text) {
        id = text;
    }

    private void defaultInfo() {
        name.setText(lectDetails.getName());
        nric.setText(Long.toString(lectDetails.getNRIC()));
        emp_status.setValue(lectDetails.getStatus());
        position.setValue(lectDetails.getPosition());
        school.setValue(lectDetails.getSchool());
        campus.setValue(lectDetails.getCampus());
    }

    public void editInfo() {
        Message.setFill(Color.RED);
        name.setEditable(true);
        nric.setEditable(true);

        name.setStyle("-fx-border-color: #eb7231");
        nric.setStyle("-fx-border-color: #eb7231");

        name.setDisable(false);
        nric.setDisable(false);
        emp_status.setDisable(false);
        position.setDisable(false);
        school.setDisable(false);
        campus.setDisable(false);

        editInfoButton.setDisable(true);
        saveButton.setDisable(false);

        Message.setText("");
    }

    public void saveInfo() {
        Message.setFill(Color.GREEN);
        Message.setText("Save successful!");

        editInfoButton.setDisable(false);
        saveButton.setDisable(true);

        name.setEditable(false);
        nric.setEditable(false);
        name.setDisable(true);
        nric.setDisable(true);
        emp_status.setDisable(true);
        position.setDisable(true);
        school.setDisable(true);
        campus.setDisable(true);

        name.setStyle("-fx-border-color: default");
        nric.setStyle("-fx-border-color: default");

        lectDetails.setName(name.getText());
        lectDetails.setSchool(school.getValue());
        lectDetails.setPosition(position.getValue());
        lectDetails.setNRIC(Long.parseLong(nric.getText()));
        lectDetails.setCampus(campus.getValue());
        lectDetails.setStatus(emp_status.getValue());

        try {
            LecturerDAO.updateLecturerDetails(lectDetails);
        } catch (SQLException exc) {
            return;
        }
    }

    private void ChoiceBoxItem() {
        // Status will remain the same as it is due to it being a subset of the data in the table
        emp_status.getItems().addAll("Active", "Research", "Further Study");

        try {
            position.getItems().addAll(CourseDAO.getChoiceBoxItems(ChoiceBoxItems.POSITION));
            school.getItems().addAll(CourseDAO.getChoiceBoxItems(ChoiceBoxItems.SCHOOL));
            campus.getItems().addAll(CourseDAO.getChoiceBoxItems(ChoiceBoxItems.CAMPUS));
        } catch (SQLException exc) {
            System.out.println("Error occurred while getting ChoiceBox items");
        }
    }

    public void validateNRIC() {
        Message.setFill(Color.RED);
        try {
            Long.parseLong(nric.getText());
            if (nric.getLength() != 12) {
                Message.setText("Please enter a NRIC number with 12 digits!");
                saveButton.setDisable(true);
                name.setDisable(true);
            } else {
                Message.setText("");
                saveButton.setDisable(false);
                name.setDisable(false);
            }
        } catch (NumberFormatException e) {
            if (nric.getText().isBlank()) {
                Message.setText("");
                saveButton.setDisable(false);
                name.setDisable(false);
            } else {
                Message.setText("NRIC must be numbers only!");
                saveButton.setDisable(true);
                name.setDisable(true);
            }
        }
    }

    public void validateName(KeyEvent e) {
        Message.setFill(Color.RED);
        if (name.getText().isEmpty()) {
            Message.setText("");
            saveButton.setDisable(false);
            nric.setDisable(false);
        } else if (!"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ/ \b".contains(e.getCharacter())) {
            Message.setText("Your name should only contains alphabets or slashes! Try again!");
            saveButton.setDisable(true);
            nric.setDisable(true);
        } else if (!name.getText().isEmpty()) {
            Message.setText("");
            saveButton.setDisable(false);
            nric.setDisable(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        staffID.setText(id);
        try {
            lectDetails = LecturerDAO.getLecturerDetails(Integer.parseInt(id));
        } catch (SQLException exc) {
            System.out.println("Error instantiating the lecturer dashboard");
        }
        defaultInfo();
        ChoiceBoxItem();
        editInfoButton.setDisable(false);
        saveButton.setDisable(true);
    }
}