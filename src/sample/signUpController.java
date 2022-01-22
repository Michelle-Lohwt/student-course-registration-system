package sample;

import java.io.*;
import java.io.IOException;
import java.net.URISyntaxException;

<<<<<<< HEAD
=======

>>>>>>> dc2130f694f14deb71f6dc4fc4fd242793f04e2f
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
<<<<<<< HEAD
  
  public void signUp(MouseEvent event) {
    
    //These 3 things are actually You Quan's part, but I help him to do a bit.
    //These 3 things are actually need to happen whenever a new user sign up. No need if log in.

    //1. Create student1courselist.txt file
    try {
      File fileObj = new File("src/sample/data/Student Course List/"+matricNo.getText()+".txt");
      if (fileObj.createNewFile()) {
        System.out.println("File created: " + fileObj.getName());
      } else {
        System.out.println(fileObj.getName() + " already exists.");
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    //2. Copy the content of courseList.txt into student1courseList.txt
    try{
    FileInputStream in = new FileInputStream(new File("src/sample/data/Course List.txt"));
    FileOutputStream out = new FileOutputStream(new File ("src/sample/data/Student Course List/"+matricNo.getText()+".txt"));

      try{
        int n;
        while ((n = in.read()) != -1){
          out.write(n);
        }
      }

      finally{
        if (in != null) {
          in.close();
        }
        if (out != null) {
          out.close();
        }
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    //3. Create student1registeredCourse.txt file
    try {
      File fileObj = new File("src/sample/data/Student Registered Course/"+matricNo.getText()+".txt");
      if (fileObj.createNewFile()) {
        System.out.println("File created: " + fileObj.getName());
        //System.out.println(fileObj.getAbsolutePath());
      } else {
        System.out.println(fileObj.getName() + " already exists.");
        //System.out.println(fileObj.getAbsolutePath());
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    signUpMessage.setText("Sign Up Successful");
  }
=======

  public void signUp(MouseEvent event) throws IOException{
 if(matricNo.getText().isBlank()==true && password.getText().isBlank()==true && rePassword.getText().isBlank()==true)
 {
  signUpMessage.setText("Please enter matrics number and password!");
 }
 
 else if 
 (matricNo.getText().isBlank()==false && password.getText().isBlank()==false && 
 password.getLength()<6 && rePassword.getText().isBlank()==false && rePassword.getLength()<6)
 {
  signUpMessage.setText("Password must have minimum 6 characters!");
 }
 
else if(matricNo.getText().isBlank()==true)
{
  signUpMessage.setText("Please enter matrics number!");
}
 
else if(password.getText().isBlank()==true || rePassword.getText().isBlank()==true)
{
  signUpMessage.setText("Please enter password!");
}

else if (!password.getText().equals (rePassword.getText()))
{
  signUpMessage.setText("Password does not match!");
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
>>>>>>> dc2130f694f14deb71f6dc4fc4fd242793f04e2f

  public void TriggerPasswordCheckBox() {
    if (showPassword.isSelected()) {
      TextPassword.setText(password.getText());
      reTextPassword.setText(rePassword.getText());

<<<<<<< HEAD
      TextPassword.setVisible(true);
      reTextPassword.setVisible(true);

=======
      TextPassword.setDisable(false);
      reTextPassword.setDisable(false);

      TextPassword.setVisible(true);
      reTextPassword.setVisible(true);

      password.setDisable(true);
      rePassword.setDisable(true);

>>>>>>> dc2130f694f14deb71f6dc4fc4fd242793f04e2f
      password.setVisible(false);
      rePassword.setVisible(false);

    } else {
      password.setText(TextPassword.getText());
      rePassword.setText(reTextPassword.getText());

<<<<<<< HEAD
      TextPassword.setVisible(false);
      reTextPassword.setVisible(false);

=======
      TextPassword.setDisable(true);
      reTextPassword.setDisable(true);

      TextPassword.setVisible(false);
      reTextPassword.setVisible(false);

      password.setDisable(false);
      rePassword.setDisable(false);

>>>>>>> dc2130f694f14deb71f6dc4fc4fd242793f04e2f
      password.setVisible(true);
      rePassword.setVisible(true);
    }
  }
<<<<<<< HEAD
}
=======
}
>>>>>>> dc2130f694f14deb71f6dc4fc4fd242793f04e2f
