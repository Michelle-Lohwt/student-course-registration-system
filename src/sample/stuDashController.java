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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import javafx.stage.Stage;


public class stuDashController extends Controller implements Initializable {

  @FXML
  private ImageView profilePic;

  @FXML
  private TextField name, nric, matric, cgpa;

  @FXML
  private ChoiceBox<String> acd_status, sem_reg, year, school, campus, programme, major, minor;

  @FXML
  private JFXButton editInfoButton, saveButton,contactButton;

  @FXML
  private Text Messages;
  
  @FXML
  private Button closeButton;
  
  static String id;

  public void LogOut(MouseEvent event) throws IOException {
    switchTo(event, "logout.fxml");
  }

  public void CourseRegistration(MouseEvent event) throws IOException {
    if(name.getText().isEmpty())
    {
      Messages.setText("Please save all the infos before proceed to course registration!");
    }

    else if(!name.getText().isEmpty())
  {
  switchTo(event, "courseReg.fxml");
  }
}
  
  
  public void ContactUs(MouseEvent event) throws IOException {
    switchTo(event, "stuReport.fxml");

  }
  public void CallUs() {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CallUs.fxml"));
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      Image icon = new Image("sample/images/usm-ringlogo.png");
      stage.getIcons().add(icon);
      stage.setTitle("Contact Us");
      stage.setResizable(false);
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
      System.out.println("Can't load new window");
    }
  }

  public void openBrowser() throws URISyntaxException, IOException {
    openLink();
  }

  public static void getID(String text)
  {
    id=text;
  }

  private void defaultInfo()
  {
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

  
  public void saveInfo() {
    name.setEditable(false);
    downloadController.inputName(name.getText());
    nric.setEditable(false);
    cgpa.setEditable(false);
    
    
    name.setStyle("-fx-border-color: default");
    nric.setStyle("-fx-border-color: default");
    cgpa.setStyle("-fx-border-color: default");

    //If want to do CGPA validation then have to convert string which is cgpa.getText() to int,
    // and this is the way I found online, have tried several times
    //but cannot
    /**String cgpaInteger=cgpa.getText();
    int cgpaNumber = Integer.valueOf(cgpaInteger);
      if (cgpaNumber <0.00 && cgpaNumber>4.00)
      {
        Messages.setText("Please enter a CGPA between 0.00 and 4.00 ONLY!");
      }*/
    if (nric.getLength() !=12)
    {
      Messages.setText("Please enter a 12 digits NRIC number!");
    }

    else if (name.getText().isEmpty() == true)
    {
     Messages.setText("Please enter your name!");
    }
    else
    {
      Messages.setText("Save successful!");
    try{
      File stuinfoFile = new File("data/Student Dashboard/"+ id +".txt");
      stuinfoFile.createNewFile();
      BufferedWriter writer=new BufferedWriter(new FileWriter("data/Student Dashboard/"+ id +".txt"));
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
      writer.write("\n" + minor.getSelectionModel().getSelectedItem());
      writer.close();
      //I also tried the save name things, but also fails, the name can still be edited after the first save
      name.setEditable(false);
    }catch(IOException e)
    {
      System.out.println("An error occured.");
    }
  }

  }
  
  public void displayName() 
  {
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

  public void displayNRIC()
  {
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


  public void displayAcdStatus()
  {
    try {
      File stuinfoFile = new File("data/Student Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(stuinfoFile);
      String acdstatus;
      acdstatus = Files.readAllLines(Paths.get("data/Student Dashboard/" + id + ".txt")).get(3);
      acd_status.setValue(acdstatus);
      sc.close();
      } catch (IOException e) {
        
        e.printStackTrace();
      }
      
  }
  
  public void displaySemRegistered()
  {
    try {
      File stuinfoFile = new File("data/Student Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(stuinfoFile);
      String semregistered;
      semregistered = Files.readAllLines(Paths.get("data/Student Dashboard/" + id + ".txt")).get(4);
      sem_reg.setValue(semregistered);
      sc.close();
      } catch (IOException e) {
        
        e.printStackTrace();
      }
      
  }

  public void displayCGPA()
  {
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
  
  public void displayYear()
  {
    try {
      File stuinfoFile = new File("data/Student Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(stuinfoFile);
      String acdYear;
      acdYear = Files.readAllLines(Paths.get("data/Student Dashboard/" + id + ".txt")).get(6);
      year.setValue(acdYear);
      sc.close();
      } catch (IOException e) {
        
        e.printStackTrace();
      }
  }

  public void displaySchool()
  {
    try {
      File stuinfoFile = new File("data/Student Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(stuinfoFile);
      String School;
      School = Files.readAllLines(Paths.get("data/Student Dashboard/" + id + ".txt")).get(7);
      school.setValue(School);
      sc.close();
      } catch (IOException e) {
        
        e.printStackTrace();
      }
  }

  public void displayCampus()
  {
    try {
      File stuinfoFile = new File("data/Student Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(stuinfoFile);
      String Campuses;
      Campuses = Files.readAllLines(Paths.get("data/Student Dashboard/" + id + ".txt")).get(8);
      campus.setValue(Campuses);
      sc.close();
      } catch (IOException e) {
        
        e.printStackTrace();
      }
  }

  public void displayProgramme()
  {
    try {
      File stuinfoFile = new File("data/Student Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(stuinfoFile);
      String Programmes;
      Programmes = Files.readAllLines(Paths.get("data/Student Dashboard/" + id + ".txt")).get(9);
      programme.setValue(Programmes);
      sc.close();
      } catch (IOException e) {
        
        e.printStackTrace();
      }
  }

  public void displayMajor()
  {
    try {
      File stuinfoFile = new File("data/Student Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(stuinfoFile);
      String Majors;
      Majors = Files.readAllLines(Paths.get("data/Student Dashboard/" + id + ".txt")).get(10);
      major.setValue(Majors);
      sc.close();
      } catch (IOException e) {
        
        e.printStackTrace();
      }
  }

  public void displayMinor()
  {
    try {
      File stuinfoFile = new File("data/Student Dashboard/" + id + ".txt");
      Scanner sc = new Scanner(stuinfoFile);
      String Minors;
      Minors = Files.readAllLines(Paths.get("data/Student Dashboard/" + id + ".txt")).get(11);
      minor.setValue(Minors);
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


  

  @Override
  public void initialize(URL location, ResourceBundle resources){
    
    matric.setText(id);
    defaultInfo();
    ChoiceBoxItem();
   

}
}
