package sample;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.fxml.FXML;

public class signUpController extends Controller {

  @FXML
  private ToggleGroup studentLecturer;

  @FXML
  private Text signUpMessage;

  @FXML
  private TextField studentIDTextField;

  @FXML
  private PasswordField passwordField , passwordField1;

  public void Login(MouseEvent event) throws IOException {
    switchTo(event, "login.fxml");
  }

  public void ContactUs(MouseEvent event) throws IOException {
    switchTo(event, "contactUs.fxml");
  }

  public void openBrowser() throws URISyntaxException, IOException {
    openLink();
  }

  public void signUp(MouseEvent event) {
 if(studentIDTextField.getText().isBlank()==true && passwordField.getText().isBlank()==true)
 {
  signUpMessage.setText("Please enter matrics number and password!");
 }

 else if (studentIDTextField.getText().isBlank()==false && passwordField.getText().isBlank()==false && passwordField.getLength()<6)
 {
  signUpMessage.setText("Password must have minimum 6 characters!");
 }

else if(studentIDTextField.getText().isBlank()==true)
{
  signUpMessage.setText("Please enter matrics number!");
}

else if(passwordField.getText().isBlank()==true ||passwordField1.getText().isBlank()==true )
{
  signUpMessage.setText("Please enter password!");
}

else if (passwordField.getText() != passwordField1.getText())
{
  signUpMessage.setText("Password is incorrect!");
}

 else if (passwordField.getText() == passwordField1.getText()){
   signUpMessage.setText("Sign Up Successful!");
  try {
    File fileObj = new File(studentIDTextField.getText() + "courseList.txt");
    if (fileObj.createNewFile()) {
      System.out.println("File created: " + fileObj.getName());
      //System.out.println(fileObj.getAbsolutePath());
    } else if (!fileObj.createNewFile()){
      signUpMessage.setText("This matrics number has been registered before!");
      System.out.println(fileObj.getName() + " already exists.");
      
      //System.out.println(fileObj.getAbsolutePath());
    }

    
  } catch (IOException e) {
    System.out.println("An error occurred.");
    e.printStackTrace();
  }
 }
 }  
  }

/**if (studentIDTextField.getText().isBlank()==false && passwordField.getText().isBlank()==false && passwordField.getLength()>=6 && (passwordField.getText()==passwordField1.getText()))
{
  //signUpMessage.setText("Sign Up Successful!");
  try {
    File fileObj = new File(studentIDTextField.getText() + "courseList.txt");
    if (fileObj.createNewFile()) {
      System.out.println("File created: " + fileObj.getName());
      //System.out.println(fileObj.getAbsolutePath());
    } else if (!fileObj.createNewFile()){
      signUpMessage.setText("This matrics number has been registered before!");
      System.out.println(fileObj.getName() + " already exists.");
      
      //System.out.println(fileObj.getAbsolutePath());
    }

    
  } catch (IOException e) {
    System.out.println("An error occurred.");
    e.printStackTrace();
  }
  
}

else if (studentIDTextField.getText().isBlank()==false && passwordField.getText().isBlank()==false && passwordField.getLength() <6)
{
  signUpMessage.setText("Password must have minimum 6 characters!");
}

else if (passwordField.getText()!= passwordField1.getText())
{
  signUpMessage.setText("Password incorrect!");
}

else if (passwordField.getText()==passwordField1.getText())
{
  signUpMessage.setText("Sign Up Successful!");
}
else{
  signUpMessage.setText("Please enter matrics number and password!");
 }

        }
      }**/






