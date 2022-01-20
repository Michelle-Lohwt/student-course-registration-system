package sample;

import java.io.IOException;
import java.net.URISyntaxException;
import java.io.File;
import java.util.Scanner;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;

public class loginController extends Controller {

  @FXML
  private ToggleGroup studentLecturer;

  @FXML
  private RadioButton rbLecturer, rbStudent;

  @FXML
  private Text loginMessage;

  @FXML
  private TextField studentIDTextField;

  @FXML
  private PasswordField passwordTextField;

  public void SignUp(MouseEvent event) throws IOException {
    switchTo(event, "signUp.fxml");
  }

  public void ContactUs(MouseEvent event) throws IOException {
    switchTo(event, "contactUs.fxml");
  }

  public void openBrowser() throws URISyntaxException, IOException {
    openLink();
  }

  public void SignIn(MouseEvent event) throws IOException{
    if (studentIDTextField.getText().isBlank()==true && passwordTextField.getText().isBlank()==true)
    {
      loginMessage.setText("Please enter matrics number and password!");
    }
    else if(studentIDTextField.getText().isBlank()==true)
    {
    loginMessage.setText("Please enter matrics number!");
    }
 
    else if(passwordTextField.getText().isBlank()==true)
    {
    loginMessage.setText("Please enter password!");
    }

    else{
      try
      {
        File fileObj = new File(studentIDTextField.getText() + ".txt");
        Scanner sc =new Scanner(fileObj);
        String correctStudentID = sc.nextLine();
        String correctPassword = sc.nextLine();
        if(!studentIDTextField.getText().equals(correctStudentID))
        {
          loginMessage.setText("Matrics number is incorrect!");
        }
        else if(!passwordTextField.getText().equals(correctPassword))
        {
          loginMessage.setText("Password is incorrect!");
        }
        else if (studentIDTextField.getText().equals(correctStudentID) && passwordTextField.getText().equals(correctPassword) && rbStudent.isSelected())
       {
        switchTo(event, "stuDash.fxml");
       }

        
        while(sc.hasNextLine())
        {
        System.out.println(sc.nextInt());
        
      } 
      sc.close();
    }
      catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }

    }
  }
    /**if (rbStudent.isSelected()) {
      switchTo(event, "stuDash.fxml");
    } else if (rbLecturer.isSelected()) {
      switchTo(event, "lecDash.fxml");
    }*/
  

}

