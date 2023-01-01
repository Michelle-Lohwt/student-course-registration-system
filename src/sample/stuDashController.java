package sample;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import sample.classes.Student;

public class stuDashController extends Controller implements Initializable {

  private Student studDetails;

  @FXML
  private TextField name, nric, matric, cgpa;

  @FXML
  private ChoiceBox<String> acd_status, sem_reg, year, school, campus, programme, major, minor;

  @FXML
  private JFXButton editInfoButton, saveButton;

  // TODO: Check if these two variables have any use
  Float floatCGPA = (float) -1;
  Boolean CGPAsuccess = true;

  @FXML
  private Text Messages;

  @FXML
  private Button closeButton;

  static String id;

  public static void getID(String text) {
    id = text;
  }

  public void LogOut(MouseEvent event) throws IOException {
    switchTo(event, "logout.fxml");
  }

  public void CourseRegistration(MouseEvent event) throws IOException {
    Messages.setFill(Color.RED);
    if (studDetails.getName().isEmpty()) {
      Messages.setText("Please save your name before proceed to course registration!");
    } else {
      stuReportController.inputName(name.getText());
      courseRegController.stuName = studDetails.getName();
      courseRegController.stuID = String.format("%s", studDetails.getID());
      switchTo(event, "courseReg.fxml");
    }
  }

  public void ContactUs(MouseEvent event) throws IOException {
    stuReportController.inputName(name.getText());
    switchTo(event, "stuReport.fxml");
  }

  public void openBrowser() throws URISyntaxException, IOException {
    openLink();
  }

  private void defaultInfo() {
    name.setText(studDetails.getName());
    nric.setText(String.format("%s", studDetails.getNRIC()));
    acd_status.setValue(studDetails.getStatus());
    sem_reg.setValue(studDetails.getSem());
    cgpa.setText(String.format("%s", studDetails.getCGPA()));
    year.setValue(String.format("%s", studDetails.getYear()));
    school.setValue(studDetails.getSchool());
    campus.setValue(studDetails.getCampus());
    programme.setValue(studDetails.getProgramme());
    major.setValue(studDetails.getMajor());
    minor.setValue(studDetails.getMinor());
  }

  public void editStuInfo() throws IOException {
    Messages.setFill(Color.RED);

    if (studDetails.getName() == null) {
      name.setEditable(true);
      name.setDisable(false);
      name.setStyle("-fx-border-color: #eb7231");
    } else {
      name.setEditable(false);
      name.setDisable(true);
      name.setStyle("-fx-border-color: default");
    }
    nric.setEditable(true);
    cgpa.setEditable(true);

    nric.setStyle("-fx-border-color: #eb7231");
    cgpa.setStyle("-fx-border-color: #eb7231");

    nric.setDisable(false);
    cgpa.setDisable(false);
    acd_status.setDisable(false);
    sem_reg.setDisable(false);
    year.setDisable(false);
    school.setDisable(false);
    campus.setDisable(false);
    programme.setDisable(false);
    major.setDisable(false);
    minor.setDisable(false);

    editInfoButton.setDisable(true);
    saveButton.setDisable(false);

    Messages.setText("");
  }

  public void saveInfo() {
    Messages.setFill(Color.GREEN);
    Messages.setText("Save successful!");

    editInfoButton.setDisable(false);
    saveButton.setDisable(true);

    name.setEditable(false);
    courseRegController.inputName(name.getText());
    nric.setEditable(false);
    cgpa.setEditable(false);

    name.setStyle("-fx-border-color: default");
    nric.setStyle("-fx-border-color: default");
    cgpa.setStyle("-fx-border-color: default");

    name.setDisable(true);
    nric.setDisable(true);
    cgpa.setDisable(true);
    acd_status.setDisable(true);
    sem_reg.setDisable(true);
    year.setDisable(true);
    school.setDisable(true);
    campus.setDisable(true);
    programme.setDisable(true);
    major.setDisable(true);
    minor.setDisable(true);

    try {
      studDetails.setName(name.getText());
      studDetails.setNRIC(Long.parseLong(nric.getText()));
      studDetails.setStatus(acd_status.getValue());
      studDetails.setSem(sem_reg.getValue());
      studDetails.setProgramme(programme.getValue());
      studDetails.setSchool(school.getValue());
      studDetails.setMajor(major.getValue());
      studDetails.setMinor(minor.getValue());
      studDetails.setCGPA(Float.parseFloat(cgpa.getText()));
      studDetails.setCampus(campus.getValue());
      studDetails.setYear(Integer.parseInt(year.getValue()));
      AppDAO.updateStudentDetails(studDetails);
    } catch (SQLException exc) {
      System.out.println("An error occurred.");
    }
  }

  private void ChoiceBoxItem() {
    try{
      // Note: Will leave the acd_status as it is here because they are diff
      // for both the lecturer and student
      acd_status.getItems().addAll("Active", "Probationary");
      sem_reg.getItems().addAll(AppDAO.getChoiceBoxItems(AppDAO.ChoiceBoxItems.SEMESTER));
      // Year can be edited from here itself as it is simply a list of numbers
      year.getItems().addAll("1", "2", "3", "4");
      school.getItems().addAll(AppDAO.getChoiceBoxItems(AppDAO.ChoiceBoxItems.SCHOOL));
      campus.getItems().addAll(AppDAO.getChoiceBoxItems(AppDAO.ChoiceBoxItems.CAMPUS));
      programme.getItems().addAll(AppDAO.getChoiceBoxItems(AppDAO.ChoiceBoxItems.PROGRAMME));
      major.getItems().addAll(AppDAO.getChoiceBoxItems(AppDAO.ChoiceBoxItems.MAJOR));
      minor.getItems().addAll(AppDAO.getChoiceBoxItems(AppDAO.ChoiceBoxItems.MINOR));
    } catch (SQLException exc) {
      exc.printStackTrace();
    }
  }

  public void validateName(KeyEvent e) {
    Messages.setFill(Color.RED);
    if (name.getText().isEmpty()) {
      Messages.setText("Name cannot be empty!");
      saveButton.setDisable(true);
      nric.setDisable(true);
      cgpa.setDisable(true);
      // TODO: Replace this line with a REGEX
    } else if (!"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ/ \b".contains(e.getCharacter())) {
      Messages.setText("Your name should only contains alphabets or slashes! Try again!");
      saveButton.setDisable(true);
      nric.setDisable(true);
      cgpa.setDisable(true);
    } else if (!name.getText().isEmpty()) {
      Messages.setText("");
      saveButton.setDisable(false);
      nric.setDisable(false);
      cgpa.setDisable(false);
    }
  }

  public void validateNRIC() {
    Messages.setFill(Color.RED);
    try {
      Long.parseLong(nric.getText());
      if (nric.getLength() != 12) {
        Messages.setText("Please enter a NRIC number with 12 digits!");
        saveButton.setDisable(true);
        name.setDisable(true);
        cgpa.setDisable(true);
      } else {
        Messages.setText("");
        saveButton.setDisable(false);
        name.setDisable(false);
        cgpa.setDisable(false);
      }
    } catch (NumberFormatException e) {
      if (nric.getText().isBlank()) {
        Messages.setText("");
        saveButton.setDisable(false);
        name.setDisable(false);
        cgpa.setDisable(false);
      } else {
        Messages.setText("NRIC must be numbers only!");
        saveButton.setDisable(true);
        name.setDisable(true);
        cgpa.setDisable(true);
      }
    }
  }

  public void validateCGPA(KeyEvent e) {
    Messages.setFill(Color.RED);
    if (!cgpa.getText().isEmpty()) {
      if (!"0123456789.\b".contains(e.getCharacter())) {
        Messages.setText("Please input valid number!");
        saveButton.setDisable(true);
        name.setDisable(true);
        nric.setDisable(true);
      } else {
        if (cgpa.getText().length() != 4) {
          Messages.setText("CGPA must be 2 decimal places!");
          saveButton.setDisable(true);
          name.setDisable(true);
          nric.setDisable(true);
        } else {
          if (Float.parseFloat(cgpa.getText()) > 4.00 || Float.parseFloat(cgpa.getText()) < 0.00) {
            Messages.setText("CGPA must be 0.00-4.00!");
            saveButton.setDisable(true);
            name.setDisable(true);
            nric.setDisable(true);
          } else {
            Messages.setText("");
            saveButton.setDisable(false);
            name.setDisable(false);
            nric.setDisable(false);
          }
        }
      }
    } else {
      Messages.setText("");
      saveButton.setDisable(false);
      name.setDisable(false);
      nric.setDisable(false);
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try{
      studDetails = AppDAO.getStudentDetails(Integer.parseInt(id));
    }
    catch (SQLException exc) {
      System.out.println("Exception at stuDashController initialize function!");
    }
    matric.setText(id);
    defaultInfo();
    ChoiceBoxItem();
    editInfoButton.setDisable(false);
    saveButton.setDisable(true);
  }
}