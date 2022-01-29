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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;


public class stuDashController extends Controller implements Initializable {

  @FXML
  private ImageView profilePic;

  @FXML
  private TextField name, nric, matric, cgpa;

  @FXML
  private ChoiceBox<String> acd_status, sem_reg, year, school, campus, programme, major, minor;

  @FXML
  private JFXButton editInfoButton, saveButton;

  Float floatCGPA = (float)-1;
  Boolean CGPAsuccess = true;

  @FXML
  private Text Messages;
  
  @FXML
  private Button closeButton;
  
  static String id;
  static String studentName;

  public void LogOut(MouseEvent event) throws IOException {
    switchTo(event, "logout.fxml");
  }

  public void CourseRegistration(MouseEvent event) throws IOException {
    //I have tried to insert saveButton.isPressed() condition but cannot
     File stuinfoFile = new File("data/Student Dashboard/" + id + ".txt");
     Scanner sc = new Scanner(stuinfoFile);
     studentName = Files.readAllLines(Paths.get("data/Student Dashboard/" + id + ".txt")).get(0);
     sc.close();
    if(studentName== null)
    {
      Messages.setText("Please save the name before proceed to course registration!");
    }
    else 
  {
    stuReportController.inputName(name.getText());
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

  public static void getID(String text)
  {
    id=text;
  }

  // public static void getStudentName() throws IOException{
  //   File stuinfoFile = new File("data/Student Dashboard/" + id + ".txt");
  //   Scanner sc = new Scanner(stuinfoFile);
  //   studentName = Files.readAllLines(Paths.get("data/Student Dashboard/" + id + ".txt")).get(0);
  //   sc.close();
  // }

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
    if (name.getText().isEmpty()) 
    {
      name.setEditable(true);
      name.setDisable(false);
      name.setStyle("-fx-border-color: #eb7231");
    } 
    else 
    {
      name.setEditable(false);
      name.setDisable(true);
      name.setStyle("-fx-border-color: default");
    }
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

    if (nric.getLength() !=12)
    {
      Messages.setText("Please enter a 12 digits NRIC number!");
      
    }

    else if (name.getText().isEmpty() == true)
    {
     Messages.setText("Please enter your name!");
     
    }

    else if (floatCGPA <0 || floatCGPA>4)
    {
      Messages.setText("Please enter a CGPA between 0 and 4 only!");
      
    }
    else
    {
      Messages.setText("Save successful!");
      saveButton.setDisable(false);
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
      saveButton.setDisable(false);
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

public void validation()
{
  CGPAsuccess = false;
    floatCGPA = Float.parseFloat(cgpa.getText());
    if (floatCGPA < 0 || floatCGPA > 4) {
      Messages.setText("Please enter a CGPA between 0 and 4 only!");
    } else if (!cgpa.getText().contains(".")) {
      System.out.println("CGPA should contain 2 decimal places");
    } else if (cgpa.getText().length() > 4) {
      cgpa.setText(String.format("%.2f", floatCGPA));
    }

    if (!name.getText().isEmpty() && CGPAsuccess) {
      saveButton.setDisable(false);
      editInfoButton.setDisable(false);
    }
}

public void validateName(KeyEvent e) {
  if (name.getText().isEmpty()) 
  {
    Messages.setText("Please enter your name!");
    saveButton.setDisable(true);
  } 
  else if (!"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ/ ".contains(e.getCharacter())) 
  {
    Messages.setText("Your name should only contains alphabets or slashes! Try again!");
    name.setText(name.getText().substring(0, name.getText().length() - 1));
    name.positionCaret(name.getText().length());
    saveButton.setDisable(true);
  } else if (!name.getText().isEmpty()) {
    saveButton.setDisable(false);
  }
}

public void validateNRIC(KeyEvent e)
{
  if (nric.getText().isEmpty()) 
  {
    Messages.setText("Please enter your NRIC!");
    saveButton.setDisable(true);
    editInfoButton.setDisable(false);
  } 
  else if (!"0123456789".contains(e.getCharacter())) 
  {
    Messages.setText("Your NRIC should only contains numbers! Try again!");
    //name.setText(name.getText().substring(0, name.getText().length() - 1));
    //name.positionCaret(name.getText().length());
    saveButton.setDisable(true);
  } else if (!nric.getText().isEmpty()) {
    saveButton.setDisable(false);
  }else if (nric.getLength() !=12)
  {
    Messages.setText("Please enter a 12 digits NRIC number!");
  }
}
public void validateCGPA(KeyEvent e) {
  if (cgpa.getText().isEmpty()) 
  {
    Messages.setText("Please enter your CGPA!");
    saveButton.setDisable(true);
    editInfoButton.setDisable(false);
  } 
  else if (!"0123456789.".contains(e.getCharacter())) 
  {
    Messages.setText("Please input valid number!");
    cgpa.setText(cgpa.getText().substring(0, cgpa.getText().length() - 1));
    cgpa.positionCaret(cgpa.getText().length());
    saveButton.setDisable(true);
    //editInfoButton.setDisable(false);
  } 
  else if (!cgpa.getText().isEmpty())
  {
    saveButton.setDisable(false);
  }
}

  @Override
  public void initialize(URL location, ResourceBundle resources){
    saveButton.setDisable(true);
    matric.setText(id);
    defaultInfo();
    ChoiceBoxItem();
}
}