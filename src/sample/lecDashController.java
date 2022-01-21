package sample;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class lecDashController extends Controller implements Initializable {

  @FXML
  private Text name, nric, position, school, staffID, status;

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

  private void lecInfo() {
    name.setText("ABC");
    nric.setText("12345");
    staffID.setText("L12345");
    status.setText("ACTIVE");
    school.setText("School of Computer Science");
    position.setText("Senior Lecturer");
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    lecInfo();
  }

}