package sample;

import java.io.BufferedWriter;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class lecDashController extends Controller implements Initializable {

  @FXML
  private TextField name, nric, staffID;

  @FXML
  private JFXButton editInfoButton, saveButton;

  @FXML
  private ChoiceBox<String> emp_status, position, school, campus;

  @FXML
  private ImageView profilePic;
 
  @FXML
  private Text errorMessage;
  
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

  public static void getID(String text)
  {
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
  }

  public void saveInfo() {
    name.setEditable(false);
    downloadController.inputLectName(name.getText());
    nric.setEditable(false);

    name.setStyle("-fx-border-color: default");
    nric.setStyle("-fx-border-color: default");

    if(nric.getLength() !=12)
    {
      errorMessage.setText("Please enter a NRIC number with 12 digits!");
    }

    else{
    try{
      File stuinfoFile = new File("data/Lecturer Dashboard/"+ id +".txt");
      stuinfoFile.createNewFile();
      BufferedWriter writer=new BufferedWriter(new FileWriter("data/Lecturer Dashboard/"+ id +".txt"));
      writer.write(name.getText());
      writer.write("\n" + nric.getText());
      writer.write("\n" + id);
      writer.write("\n" + emp_status.getSelectionModel().getSelectedItem());
      writer.write("\n" + position.getSelectionModel().getSelectedItem());
      writer.write("\n" + school.getSelectionModel().getSelectedItem());
      writer.write("\n" + campus.getSelectionModel().getSelectedItem());
      writer.close();
    }
    catch(IOException e)
    {
      System.out.println("An error occured.");
    }
    name.setDisable(true);
    nric.setDisable(true);
    emp_status.setDisable(true);
    position.setDisable(true);
    school.setDisable(true);
    campus.setDisable(true);
  }
}

  public void displayLecName()
  {
    try {
      File lecinfoFile = new File("data/Lecturer Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(lecinfoFile);
      String lecName;
      lecName = Files.readAllLines(Paths.get("data/Lecturer Dashboard/" + id + ".txt")).get(0);
      name.setText(lecName);
      sc.close();
      name.setEditable(false);
      name.setDisable(true);
      } catch (IOException e) {
        
        e.printStackTrace();
      }
  }

  public void displayLecNRIC()
  {
    try {
      File lecinfoFile = new File("data/Lecturer Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(lecinfoFile);
      String lecNric;
      lecNric = Files.readAllLines(Paths.get("data/Lecturer Dashboard/" + id + ".txt")).get(1);
      nric.setText(lecNric);
      sc.close();
      } catch (IOException e) {
        
        e.printStackTrace();
      }
  }

  

  public void displayEmpStatus()
  {
    try {
      File lecinfoFile = new File("data/Lecturer Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(lecinfoFile);
      String status;
      status = Files.readAllLines(Paths.get("data/Lecturer Dashboard/" + id + ".txt")).get(3);
      emp_status.setValue(status);
      sc.close();
      } catch (IOException e) {
        
        e.printStackTrace();
      }
  }

  public void displayPosition()
  {
    try {
      File lecinfoFile = new File("data/Lecturer Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(lecinfoFile);
      String lecPosition;
      lecPosition = Files.readAllLines(Paths.get("data/Lecturer Dashboard/" + id + ".txt")).get(4);
      position.setValue(lecPosition);
      sc.close();
      } catch (IOException e) {
        
        e.printStackTrace();
      }
  }

  public void displayLecSchool()
  {
    try {
      File lecinfoFile = new File("data/Lecturer Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(lecinfoFile);
      String lecSchool;
      lecSchool = Files.readAllLines(Paths.get("data/Lecturer Dashboard/" + id + ".txt")).get(5);
      school.setValue(lecSchool);
      sc.close();
      } catch (IOException e) {
        
        e.printStackTrace();
      }
  }

  public void displayLecCampus()
  {
    try {
      File lecinfoFile = new File("data/Lecturer Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(lecinfoFile);
      String lecCampus;
      lecCampus = Files.readAllLines(Paths.get("data/Lecturer Dashboard/" + id + ".txt")).get(6);
      campus.setValue(lecCampus);
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

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    staffID.setText(id);
    ChoiceBoxItem();
    defaultInfo();
   
  }

}