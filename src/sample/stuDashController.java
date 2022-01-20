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

  private void defaultInfo() {
    // Please change the matric number
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

  public void saveInfo() {
    name.setEditable(false);
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
    ChoiceBoxItem();
    defaultInfo();
  }
}
