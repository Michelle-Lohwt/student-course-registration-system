package sample;

import java.io.IOException;
import java.net.URISyntaxException;

import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.fxml.FXML;


public class stuReportController extends Controller{

  @FXML
  private Text Messages;

  static String name;

  public static void inputName(String text)
  {
    name =text;
  }
  public void StuDashboard(MouseEvent event) throws IOException {
    switchTo(event, "stuDash.fxml");
  }

  public void StuCourseRegistration(MouseEvent event) throws IOException{

    if(name.isBlank())
    {
      Messages.setText("Please save your info on dashboard first!");
    }else{
    switchTo(event, "courseReg.fxml");
  }
  }
  public void LogOut(MouseEvent event) throws IOException {
    switchTo(event, "logout.fxml");
  }

  public void openBrowser() throws URISyntaxException, IOException {
    openLink();
  }
}