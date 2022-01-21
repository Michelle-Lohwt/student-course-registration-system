package sample;

import java.io.IOException;
import java.net.URISyntaxException;

import javafx.scene.input.MouseEvent;

public class stuReportController extends Controller{
  public void StuDashboard(MouseEvent event) throws IOException {
    switchTo(event, "stuDash.fxml");
  }

  public void StuCourseRegistration(MouseEvent event) throws IOException{
    switchTo(event, "courseReg.fxml");
  }

  public void LogOut(MouseEvent event) throws IOException {
    switchTo(event, "logout.fxml");
  }

  public void openBrowser() throws URISyntaxException, IOException {
    openLink();
  }
}