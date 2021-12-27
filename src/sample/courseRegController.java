package sample;
// import sample.classes.Courses;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class courseRegController extends Controller implements Initializable {

  // private final ObservableList<Courses> dataList =
  // FXCollections.observableArrayList();
  @FXML
  private JFXButton addCourseButton;

  @FXML
  private ListView<String> courseList, courseSuggestion, registeredCourse;

  @FXML
  private ImageView printPreview;

  @FXML
  private JFXButton removeCourseButton;

  @FXML
  private TextField searchCourse, searchSuggest;

  public void StuDashboard(MouseEvent event) throws IOException {
    switchTo(event, "stuDash.fxml");
  }

  public void LogOut(MouseEvent event) throws IOException {
    switchTo(event, "logout.fxml");
  }

  public void ContactUs(MouseEvent event) throws IOException {
    switchTo(event, "stuReport.fxml");
  }

  public void openBrowser() throws URISyntaxException, IOException {
    openLink();
  }

  public void addcourse() {
    courseList.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseList.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseList.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseList.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseList.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseList.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseList.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseList.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseList.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseList.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseList.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseList.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseList.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseList.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
  }

  public void removecourse() {
    registeredCourse.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    registeredCourse.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    registeredCourse.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    registeredCourse.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    registeredCourse.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    registeredCourse.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    registeredCourse.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    registeredCourse.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    registeredCourse.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    registeredCourse.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    registeredCourse.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    registeredCourse.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    registeredCourse.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
  }

  private void suggestcourse() {
    courseSuggestion.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseSuggestion.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseSuggestion.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseSuggestion.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseSuggestion.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseSuggestion.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseSuggestion.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseSuggestion.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseSuggestion.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseSuggestion.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseSuggestion.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    addcourse();
    removecourse();
    suggestcourse();
  }

}
