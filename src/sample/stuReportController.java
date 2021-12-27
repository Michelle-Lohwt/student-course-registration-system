package sample;

import java.io.IOException;
import java.net.URISyntaxException;

import javafx.scene.input.MouseEvent;

public class stuReportController extends Controller{
  public void StuDashboard(MouseEvent event) throws IOException {
    switchTo(event, "stuDashboard.fxml");
  }

  public void StuCourseRegistration(MouseEvent event) throws IOException{
    switchTo(event, "courseReg.fxml");
  }

  public void LogOut(MouseEvent event) throws IOException {
    switchTo(event, "logout.fxml");
  }

  public void openBrowser(MouseEvent event) throws URISyntaxException, IOException {
    openLink(event);
  }
}
