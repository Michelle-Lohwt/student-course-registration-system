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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
 
public class lecDashController extends Controller implements Initializable {
 
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
    switchTo(event, "stuList.fxml");
  }
 
  public void ContactUs(MouseEvent event) throws IOException {
    switchTo(event, "lecReport.fxml");
  }
 
  public void openBrowser() throws URISyntaxException, IOException {
    openLink();
  }
 
  public static void getID(String text) {
    id=text;
  }
 
  private void defaultInfo() {
    displayLecName();
    displayLecNRIC();
    displayEmpStatus();
    displayPosition();
    displayLecSchool();
    displayLecCampus();
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
        
    try{
      FileWriter writer = new FileWriter("data/Lecturer Dashboard/" + id + ".txt");
      writer.write(name.getText());
      writer.write("\n" + nric.getText());
      writer.write("\n" + id);
      writer.write("\n" + emp_status.getSelectionModel().getSelectedItem());
      writer.write("\n" + position.getSelectionModel().getSelectedItem());
      writer.write("\n" + school.getSelectionModel().getSelectedItem());
      writer.write("\n" + campus.getSelectionModel().getSelectedItem() + "\n");
      writer.close();
    } catch(IOException e) {
      System.out.println("An error occured.");
    }
  }
 
  public void displayLecName()  {
    try {
      Scanner sc = new Scanner(new File("data/Lecturer Dashboard/" + id + ".txt"));
      name.setText(Files.readAllLines(Paths.get("data/Lecturer Dashboard/" + id + ".txt")).get(0));
      sc.close();
      name.setEditable(false);
      name.setDisable(true);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
 
  public void displayLecNRIC()  {
    try {
      Scanner sc = new Scanner(new File("data/Lecturer Dashboard/" + id + ".txt"));
      nric.setText(Files.readAllLines(Paths.get("data/Lecturer Dashboard/" + id + ".txt")).get(1));
      sc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
 
  public void displayEmpStatus()  {
    try {
      Scanner sc = new Scanner(new File("data/Lecturer Dashboard/" + id + ".txt"));
      String status = Files.readAllLines(Paths.get("data/Lecturer Dashboard/" + id + ".txt")).get(3);
      if(status.contains("null")){
        emp_status.setValue("");
      } else {
        emp_status.setValue(status);
      }
      sc.close();
    } catch (IOException e) {     
      e.printStackTrace();
    }
  }
 
  public void displayPosition()  {
    try {
      Scanner sc = new Scanner(new File("data/Lecturer Dashboard/" + id + ".txt"));
      String lec_position = Files.readAllLines(Paths.get("data/Lecturer Dashboard/" + id + ".txt")).get(4);
      if(lec_position.contains("null")){
        position.setValue("");
      } else {
        position.setValue(lec_position);
      }
      sc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
 
  public void displayLecSchool()  {
    try {
      Scanner sc = new Scanner(new File("data/Lecturer Dashboard/" + id + ".txt"));
      String lec_school = Files.readAllLines(Paths.get("data/Lecturer Dashboard/" + id + ".txt")).get(5);
      if(lec_school.contains("null")){
        school.setValue("");
      } else {
        school.setValue(lec_school);
      }
      sc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
 
  public void displayLecCampus()  {
    try {
      Scanner sc = new Scanner(new File("data/Lecturer Dashboard/" + id + ".txt"));
      String lec_campus = Files.readAllLines(Paths.get("data/Lecturer Dashboard/" + id + ".txt")).get(6);
      if(lec_campus.contains("null")){
        campus.setValue("");
      } else {
        campus.setValue(lec_campus);
      }
      sc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
 
  private void ChoiceBoxItem() {
    emp_status.getItems().addAll("Active", "Research", "Further Study");
    position.getItems().addAll("Lecturer", "Senior Lecturer", "Professor", "Associate Professor",
        "Honorary Associate Professor", "Honorary Professor");
    school.getItems().addAll("School of Computer Science", "School of Mathematical Sciences", "School of Management");
    campus.getItems().addAll("Main Campus", "Health Campus", "Engineering Campus");
  }

  public void validateNRIC(){
    Message.setFill(Color.RED);
    try {
      Long.parseLong(nric.getText());
      if(nric.getLength() !=12) {
        Message.setText("Please enter a NRIC number with 12 digits!");
        saveButton.setDisable(true);
        name.setDisable(true);
      } else{
        Message.setText("");
        saveButton.setDisable(false);
        name.setDisable(false);
      }
    } catch (NumberFormatException e) {
      if (nric.getText().isBlank()){
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
    defaultInfo();
    ChoiceBoxItem();
    editInfoButton.setDisable(false);
    saveButton.setDisable(true);
  }
}