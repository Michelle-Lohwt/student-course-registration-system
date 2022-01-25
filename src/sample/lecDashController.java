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

public class lecDashController extends Controller implements Initializable {

  @FXML
  private TextField name, nric, staffID;

  @FXML
  private JFXButton editInfoButton, saveButton;

  @FXML
  private ChoiceBox<String> emp_status, position, school, campus;

  @FXML
  private ImageView profilePic;

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

  private void defaultInfo() {
    staffID.setText("L12345");
    emp_status.setValue("Active");
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
    nric.setEditable(false);

    name.setStyle("-fx-border-color: default");
    nric.setStyle("-fx-border-color: default");

    name.setDisable(true);
    nric.setDisable(true);
    emp_status.setDisable(true);
    position.setDisable(true);
    school.setDisable(true);
    campus.setDisable(true);
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
    ChoiceBoxItem();
    defaultInfo();
  }

}