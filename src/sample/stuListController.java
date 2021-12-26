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
  private JFXButton addCourseButton;

  @FXML
  private TableColumn<?, ?> email;

  @FXML
  private TableColumn<?, ?> matric;

  @FXML
  private TableColumn<?, ?> name;

  @FXML
  private PieChart pieChart;

  @FXML
  private ImageView printPreview;

  @FXML
  private TextField searchCourse;

  @FXML
  private TextField searchStudent;

  @FXML
  private TableView<Student> studentList;

  @FXML
  private ListView<String> teachingCourse;

  @FXML
  private Text totalStudents;

  public void LecDashboard(MouseEvent event) throws IOException {
    switchTo(event, "lecDashboard.fxml");
  }

  public void LogOut(MouseEvent event) throws IOException {
    switchTo(event, "logout.fxml");
  }

  public void ContactUs(MouseEvent event) throws IOException {
    switchTo(event, "lecReport.fxml");
  }

  public void openBrowser(MouseEvent event) throws URISyntaxException, IOException {
    openLink(event);
  }

  public void studentYear() {

  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }
}
