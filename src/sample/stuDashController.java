package sample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.Scanner;

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

public class stuDashController extends Controller implements Initializable {

  @FXML
  private TextField name, nric, matric, cgpa;

  @FXML
  private ChoiceBox<String> acd_status, sem_reg, year, school, campus, programme, major, minor;

  @FXML
  private JFXButton editInfoButton, saveButton;

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
    File stuinfoFile = new File("data/Student Dashboard/" + id + ".txt");
    Scanner sc = new Scanner(stuinfoFile);
    String studentName;
    studentName = Files.readAllLines(Paths.get("data/Student Dashboard/" + id + ".txt")).get(0);

    if (studentName.isEmpty() == true) {
      Messages.setText("Please save your name before proceed to course registration!");
    } else {
      stuReportController.inputName(name.getText());
      switchTo(event, "courseReg.fxml");
    }
    sc.close();

  }

  public void ContactUs(MouseEvent event) throws IOException {
    stuReportController.inputName(name.getText());
    switchTo(event, "stuReport.fxml");
  }

  public void openBrowser() throws URISyntaxException, IOException {
    openLink();
  }

  private void defaultInfo() {
    displayName();
    displayNRIC();
    displayAcdStatus();
    displaySemRegistered();
    displayCGPA();
    displayYear();
    displaySchool();
    displayCampus();
    displayProgramme();
    displayMajor();
    displayMinor();
  }

  public void editStuInfo() throws IOException {
    Messages.setFill(Color.RED);
    File stuinfoFile = new File("data/Student Dashboard/" + id + ".txt");
    Scanner sc = new Scanner(stuinfoFile);
    String studentName;
    studentName = Files.readAllLines(Paths.get("data/Student Dashboard/" + id + ".txt")).get(0);
    sc.close();

    if (studentName.isEmpty() == true) {
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
      FileWriter writer = new FileWriter("data/Student Dashboard/" + id + ".txt");
      writer.write(name.getText());
      writer.write("\n" + nric.getText());
      writer.write("\n" + id);
      writer.write("\n" + acd_status.getSelectionModel().getSelectedItem());
      writer.write("\n" + sem_reg.getSelectionModel().getSelectedItem());
      writer.write("\n" + cgpa.getText());
      writer.write("\n" + year.getSelectionModel().getSelectedItem());
      writer.write("\n" + school.getSelectionModel().getSelectedItem());
      writer.write("\n" + campus.getSelectionModel().getSelectedItem());
      writer.write("\n" + programme.getSelectionModel().getSelectedItem());
      writer.write("\n" + major.getSelectionModel().getSelectedItem());
      writer.write("\n" + minor.getSelectionModel().getSelectedItem() + "\n");
      writer.close();
    } catch (IOException e) {
      System.out.println("An error occured.");
    }
  }

  public void displayName() {
    try {
      File stuinfoFile = new File("data/Student Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(stuinfoFile);
      String studentName;
      studentName = Files.readAllLines(Paths.get("data/Student Dashboard/" + id + ".txt")).get(0);
      name.setText(studentName);
      sc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void displayNRIC() {
    try {
      File stuinfoFile = new File("data/Student Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(stuinfoFile);
      String icnumber;
      icnumber = Files.readAllLines(Paths.get("data/Student Dashboard/" + id + ".txt")).get(1);
      nric.setText(icnumber);
      sc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void displayAcdStatus() {
    try {
      File stuinfoFile = new File("data/Student Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(stuinfoFile);
      String acdstatus;
      acdstatus = Files.readAllLines(Paths.get("data/Student Dashboard/" + id + ".txt")).get(3);
      if(acdstatus.contains("null")){
        acd_status.setValue("");
      } else {
        acd_status.setValue(acdstatus);
      }
      sc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void displaySemRegistered() {
    try {
      File stuinfoFile = new File("data/Student Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(stuinfoFile);
      String semregistered;
      semregistered = Files.readAllLines(Paths.get("data/Student Dashboard/" + id + ".txt")).get(4);
      if(semregistered.contains("null")){
        sem_reg.setValue("");
      } else {
        sem_reg.setValue(semregistered);
      }
      sc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void displayCGPA() {
    try {
      File stuinfoFile = new File("data/Student Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(stuinfoFile);
      String result;
      result = Files.readAllLines(Paths.get("data/Student Dashboard/" + id + ".txt")).get(5);
      cgpa.setText(result);
      sc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void displayYear() {
    try {
      File stuinfoFile = new File("data/Student Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(stuinfoFile);
      String acdYear;
      acdYear = Files.readAllLines(Paths.get("data/Student Dashboard/" + id + ".txt")).get(6);
      if(acdYear.contains("null")){
        year.setValue("");
      } else {
        year.setValue(acdYear);
      }
      sc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void displaySchool() {
    try {
      File stuinfoFile = new File("data/Student Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(stuinfoFile);
      String School;
      School = Files.readAllLines(Paths.get("data/Student Dashboard/" + id + ".txt")).get(7);
      if(School.contains("null")){
        school.setValue("");
      } else {
        school.setValue(School);
      }
      sc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void displayCampus() {
    try {
      File stuinfoFile = new File("data/Student Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(stuinfoFile);
      String Campuses;
      Campuses = Files.readAllLines(Paths.get("data/Student Dashboard/" + id + ".txt")).get(8);
      if(Campuses.contains("null")){
        campus.setValue("");
      } else {
        campus.setValue(Campuses);
      }
      sc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void displayProgramme() {
    try {
      File stuinfoFile = new File("data/Student Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(stuinfoFile);
      String Programmes;
      Programmes = Files.readAllLines(Paths.get("data/Student Dashboard/" + id + ".txt")).get(9);
      if(Programmes.contains("null")){
        programme.setValue("");
      } else {
        programme.setValue(Programmes);
      }
      sc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void displayMajor() {
    try {
      File stuinfoFile = new File("data/Student Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(stuinfoFile);
      String Majors;
      Majors = Files.readAllLines(Paths.get("data/Student Dashboard/" + id + ".txt")).get(10);
      if(Majors.contains("null")){
        major.setValue("");
      } else {
        major.setValue(Majors);
      }
      sc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void displayMinor() {
    try {
      File stuinfoFile = new File("data/Student Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(stuinfoFile);
      String Minors;
      Minors = Files.readAllLines(Paths.get("data/Student Dashboard/" + id + ".txt")).get(11);
      if(Minors.contains("null")){
        minor.setValue("");
      } else {
        minor.setValue(Minors);
      }
      sc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void ChoiceBoxItem() {
    acd_status.getItems().addAll("Active", "Probationary");
    sem_reg.getItems().addAll("17/1", "17/2", "18/1", "18/2", "19/1", "19/2", "20/1", "20/2", "21/1", "21/2", "22/1",
        "22/2");
    year.getItems().addAll("Year 1", "Year 2", "Year 3", "Year 4", "Others");
    school.getItems().addAll("School of Computer Science", "School of Mathematical Sciences", "School of Management");
    campus.getItems().addAll("Main Campus", "Health Campus", "Engineering Campus");
    programme.getItems().addAll("BSc Computer Science", "BSc Mathematics", "BSc Management");
    major.getItems().addAll("Software Engineering", "Intelligent Computing", "Computing Infrastructure");
    minor.getItems().addAll("Accounting", "Mathematics", "Electives");
  }

  public void validateName(KeyEvent e) {
    Messages.setFill(Color.RED);
    if (name.getText().isEmpty()) {
      Messages.setText("Name cannot be empty!");
      saveButton.setDisable(true);
      nric.setDisable(true);
      cgpa.setDisable(true);
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
    matric.setText(id);
    defaultInfo();
    ChoiceBoxItem();
    editInfoButton.setDisable(false);
    saveButton.setDisable(true);
  }
}