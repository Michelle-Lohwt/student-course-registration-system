package sample;

import sample.classes.Student;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class stuListController extends Controller implements Initializable {

  @FXML
    private JFXButton addCourseButton, removeCourseButton;

    @FXML
    private ListView<String> courseList, teachingCourse;

    @FXML
    private TextField searchCourse, searchStudent;

    @FXML
    private PieChart pieChart;

    @FXML
    private Text totalStudents;

    @FXML
    private ImageView printPreview;

    @FXML
    private TableView<Student> studentList;

    @FXML
    private TableColumn<?, ?> matric;

    @FXML
    private TableColumn<?, ?> name;

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

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }
}

