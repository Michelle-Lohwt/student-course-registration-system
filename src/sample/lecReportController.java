package sample;

import java.io.IOException;
import java.net.URISyntaxException;

import javafx.scene.input.MouseEvent;

public class lecReportController extends Controller {
  public void LecDashboard(MouseEvent event) throws IOException {
    switchTo(event, "lecDash.fxml");
  }

  public void StuList(MouseEvent event) throws IOException {
    switchTo(event, "stuList.fxml");
  }

  public void LogOut(MouseEvent event) throws IOException {
    switchTo(event, "logout.fxml");
  }

  public void openBrowser() throws URISyntaxException, IOException {
    openLink();
  }

}

