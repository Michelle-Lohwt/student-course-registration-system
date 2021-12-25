package sample;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class stuDasController extends Controller implements Initializable {

  @FXML
  private Text acd_status, campus, cgpa, major, matric, minor, name, nric, programme, school, sem_reg, year;

  public void LogOut(MouseEvent event) throws IOException {
    switchTo(event, "logout.fxml");
  }

  public void CourseRegistration(MouseEvent event) throws IOException {
    switchTo(event, "courseRegistration.fxml");
  }

  public void openBrowser(MouseEvent event) throws URISyntaxException, IOException {
    openLink(event);
  }

  private void stuInfo() {
    name.setText("Michelle");
    nric.setText("12345");
    matric.setText("149104");
    acd_status.setText("ACTIVE");
    sem_reg.setText("201");
    cgpa.setText("3.77");
    year.setText("Year 3");
    school.setText("School of Computer Science");
    campus.setText("Main Campus");
    programme.setText("Computer Science");
    major.setText("Software Engineering");
    minor.setText("Accounting");
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    stuInfo();
  }
}
