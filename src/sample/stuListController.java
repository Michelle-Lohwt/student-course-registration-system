package sample;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class stuListController extends Controller implements Initializable {

  @FXML
  private JFXButton addCourseButton, removeCourseButton, editCourseButton, saveCourseDetailsButton;

  @FXML
  private ListView<String> courseList, teachingCourse;

  @FXML
  private TextField searchCourse, searchStudent;

  @FXML
  private TableView<?> studentList;

  @FXML
  private TableColumn<?, ?> matric;

  @FXML
  private TableColumn<?, ?> name;

  @FXML
  private Text courseTitle;

  @FXML
  private TextArea time, desc;

  @FXML
  private ImageView downloadStudent, downloadTeaching;

  public void LecDashboard(MouseEvent event) throws IOException {
    switchTo(event, "lecDash.fxml");
  }

  public void LogOut(MouseEvent event) throws IOException {
    switchTo(event, "logout.fxml");
  }

  public void ContactUs(MouseEvent event) throws IOException {
    switchTo(event, "lecReport.fxml");
  }

  public void openBrowser() throws URISyntaxException, IOException {
    openLink();
  }

  public void studentYear() {

  }

  public void EditCourse() {
    time.setEditable(true);
    desc.setEditable(true);

    time.setStyle("-fx-border-color: #eb7231");
    desc.setStyle("-fx-border-color: #eb7231");
  }

  public void SaveCourse() {
    time.setEditable(false);
    desc.setEditable(false);

    time.setStyle("-fx-border-color: transparent");
    desc.setStyle("-fx-border-color: transparent");
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    courseTitle.setText("CAT 201 Software Integrated Development Workshop kdfkslfsfhjshfjdkahahahhahahahahahahahahaha");
    time.setText("Tuesday 3pm - 4pm ahahhahahahahahahahahahadjfhjhjsfhjksffksdhfkshdfjkshfdjdkshfljsakhsah");
    desc.setText(
        "The course serves to dkksjfasjkfdsfkjslkdjfaskljfksjfahahhahahahahahahahahahadjfksdhfjksadhfljashfjkshd");
  }
}

