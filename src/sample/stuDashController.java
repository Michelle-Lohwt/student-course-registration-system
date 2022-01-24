package sample;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class stuDashController extends Controller implements Initializable {

  @FXML
  private ImageView profilePic;

  @FXML
  private TextField name, nric, matric, cgpa;

  @FXML
  private ChoiceBox<String> acd_status, sem_reg, year, school, campus, programme, major, minor;

  @FXML
  private JFXButton editInfoButton, saveButton;

  public void LogOut(MouseEvent event) throws IOException {
    switchTo(event, "logout.fxml");
  }

  public void CourseRegistration(MouseEvent event) throws IOException {
    switchTo(event, "courseReg.fxml");
  }

  public void ContactUs(MouseEvent event) throws IOException {
    switchTo(event, "stuReport.fxml");
  }

  public void openBrowser() throws URISyntaxException, IOException {
    openLink();
  }

  private void defaultInfo() throws IOException{
    matric.setText("12345");
    acd_status.setValue("Active");

   

}

  public void editStuInfo() {
    name.setEditable(true);
    nric.setEditable(true);
    cgpa.setEditable(true);

    name.setStyle("-fx-border-color: #eb7231");
    nric.setStyle("-fx-border-color: #eb7231");
    cgpa.setStyle("-fx-border-color: #eb7231");

    name.setDisable(false);
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
  }

  public void saveInfo() throws IOException{
    name.setEditable(false);
    nric.setEditable(false);
    cgpa.setEditable(false);

    name.setStyle("-fx-border-color: default");
    nric.setStyle("-fx-border-color: default");
    cgpa.setStyle("-fx-border-color: default");

    try{
      File stuinfoFile = new File("data/Student Dashboard/"+name.getText()+".txt");
      stuinfoFile.createNewFile();
      BufferedWriter writer=new BufferedWriter(new FileWriter("data/Student Dashboard/"+name.getText()+".txt"));
      writer.write(name.getText());
      writer.write("\n" + nric.getText());
      writer.write("\n" + acd_status.getSelectionModel().getSelectedItem());
      writer.write("\n" + sem_reg.getSelectionModel().getSelectedItem());
      writer.write("\n" + cgpa.getText());
      writer.write("\n" + year.getSelectionModel().getSelectedItem());
      writer.write("\n" + school.getSelectionModel().getSelectedItem());
      writer.write("\n" + campus.getSelectionModel().getSelectedItem());
      writer.write("\n" + programme.getSelectionModel().getSelectedItem());
      writer.write("\n" + major.getSelectionModel().getSelectedItem());
      writer.write("\n" + minor.getSelectionModel().getSelectedItem());
      writer.close();
      
    }catch(IOException e)
    {
      System.out.println("An error occured.");
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

  public void display() throws IOException{
    File stuinfoFile = new File("data/Student Dashboard/"+name.getText()+".txt");
    Scanner sc = new Scanner(stuinfoFile);
    String name= sc.nextLine();
    String ic = sc.nextLine();
    while(sc.hasNextLine())
    {
      System.out.println(name);
      System.out.println(ic);
    }
    sc.close();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources){
    ChoiceBoxItem();
    try {
      defaultInfo();
      display();
    } catch (IOException e) {
      
      e.printStackTrace();
    }
  }
}

