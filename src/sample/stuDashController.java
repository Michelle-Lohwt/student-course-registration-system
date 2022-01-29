package sample;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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

  Float floatCGPA = (float) -1;
  Boolean success = true;

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

  private void defaultInfo() {
    // Please change the matric number
    matric.setText("12345");
    acd_status.setValue("Active");
  }

  public void editStuInfo() {
    if (name.getText().isEmpty()) {
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
  }

  public void validation() {
    success = false;
    floatCGPA = Float.parseFloat(cgpa.getText());
    if (floatCGPA < 0 || floatCGPA > 4) {
      System.out.println("CGPA should be within 0 and 4");
      saveButton.setDisable(true);
    } else if (!cgpa.getText().contains(".")) {
      System.out.println("CGPA should have 2 decimal points");
    } else if (cgpa.getText().length() > 4) {
      System.out.println("We have converted the CGPA to 2 decimal points");
      cgpa.setText(String.format("%.2f", floatCGPA));
    }

    if (!name.getText().isEmpty() && success) {
      saveButton.setDisable(false);
    }
  }

  public void validateName(KeyEvent e) {
    if (name.getText().isEmpty()) {
      System.out.println("Please enter name");
      saveButton.setDisable(true);
    } else if (!"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ/ ".contains(e.getCharacter())) {
      System.out.println("Please input valid name");
      name.setText(name.getText().substring(0, name.getText().length() - 1));
      name.positionCaret(name.getText().length());
      saveButton.setDisable(true);
    } else if (!name.getText().isEmpty()) {
      saveButton.setDisable(false);
    }
  }

  public void validateCGPA(KeyEvent e) {
    if (cgpa.getText().isEmpty()) {
      saveButton.setDisable(true);
    } else if (!"0123456789.".contains(e.getCharacter())) {
      System.out.println("Please input valid number");
      cgpa.setText(cgpa.getText().substring(0, cgpa.getText().length() - 1));
      cgpa.positionCaret(cgpa.getText().length());
      saveButton.setDisable(true);
    } else {
      saveButton.setDisable(false);
    }
  }

  public void saveInfo() {
    name.setEditable(false);
    downloadController.inputName(name.getText());
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
  public void initialize(URL location, ResourceBundle resources) {
    saveButton.setDisable(true);
    ChoiceBoxItem();
    defaultInfo();
  }
}