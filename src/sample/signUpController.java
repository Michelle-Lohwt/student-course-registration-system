package sample;

import java.io.*;
import java.io.IOException;
import java.net.URISyntaxException;


import com.jfoenix.controls.JFXCheckBox;

import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.fxml.FXML;

public class signUpController extends Controller {

  @FXML
  private TextField matricNo, TextPassword, reTextPassword;

  @FXML
  private PasswordField password, rePassword;

  @FXML
  private RadioButton rbLecturer, rbStudent;

  @FXML
  private JFXCheckBox showPassword;

  @FXML
  private Text signUpMessage;

  @FXML
  private ToggleGroup studentLecturer;

  public void Login(MouseEvent event) throws IOException {
    switchTo(event, "login.fxml");
  }

  public void ContactUs(MouseEvent event) throws IOException {
    switchTo(event, "contactUs.fxml");
  }

  public void openBrowser() throws URISyntaxException, IOException {
    openLink();
  }

  public void signUp(MouseEvent event) throws IOException{
 if(matricNo.getText().isBlank()==true && password.getText().isBlank()==true)
 {
  signUpMessage.setText("Please enter matrics number and password!");
 }
 
 else if (matricNo.getText().isBlank()==false && password.getText().isBlank()==false && password.getLength()<6)
 {
  signUpMessage.setText("Password must have minimum 6 characters!");
 }
 
else if(matricNo.getText().isBlank()==true)
{
  signUpMessage.setText("Please enter matrics number!");
}
 
else if(password.getText().isBlank()==true && rePassword.getText().isBlank()==true )
{
  signUpMessage.setText("Please enter password!");
}

else if (!password.getText().equals (rePassword.getText()))
{
  signUpMessage.setText("Password is incorrect!");
}

 else{
   
  try {
    File fileObj = new File(matricNo.getText() + ".txt");
    
     if (fileObj.exists()) 
    {
      System.out.println(fileObj.getName() + " already exists.");
      signUpMessage.setText("This matrics number has been registered before!");
      //System.out.println(fileObj.getAbsolutePath());
    } 
    
    else if(!fileObj.exists())
    {
      signUpMessage.setText("Sign Up Successful!");
      System.out.println("File created: " + fileObj.getName());
      BufferedWriter writer=new BufferedWriter(new FileWriter(matricNo.getText() + ".txt"));
      writer.write(matricNo.getText());
      writer.write("\n" + password.getText());
      writer.close();
  //System.out.println(fileObj.getAbsolutePath());
      //System.out.println(fileObj.getAbsolutePath());
    }
    else
    {
      signUpMessage.setText("An error occurred!");
    }
  }
    
  
catch(IOException e)
{
  signUpMessage.setText("This matrics number has been registered before!");
}

 }
}

  public void TriggerPasswordCheckBox() {
    if (showPassword.isSelected()) {
      TextPassword.setText(password.getText());
      reTextPassword.setText(rePassword.getText());

      TextPassword.setVisible(true);
      reTextPassword.setVisible(true);

      password.setVisible(false);
      rePassword.setVisible(false);

    } else {
      password.setText(TextPassword.getText());
      rePassword.setText(reTextPassword.getText());

      TextPassword.setVisible(false);
      reTextPassword.setVisible(false);

      password.setVisible(true);
      rePassword.setVisible(true);
    }
  }
}
