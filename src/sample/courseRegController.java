package sample;
// import sample.classes.Courses;
import java.sql.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class courseRegController extends Controller implements Initializable {

  // private final ObservableList<Courses> dataList =
  // FXCollections.observableArrayList();
  @FXML
  private JFXButton addCourseButton, removeCourseButton;

  @FXML
  private ListView<String> courseList, courseSuggestion, registeredCourse;

  @FXML
  private ImageView printPreview;

  @FXML
  private TextField searchCourse, searchSuggest;

  public void StuDashboard(MouseEvent event) throws IOException {
    switchTo(event, "stuDash.fxml");
  }

  public void LogOut(MouseEvent event) throws IOException {
    switchTo(event, "logout.fxml");
  }

  public void ContactUs(MouseEvent event) throws IOException {
    switchTo(event, "stuReport.fxml");
  }

  public void openBrowser() throws URISyntaxException, IOException {
    openLink();
  }

  public void addcourse() {

    
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/course_register","root","CAT201**");
            Statement stmt= conn.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT COURSE_NAME FROM course_register.course;");
            while(rs.next())
            {
               courseList.getItems().addAll(rs.getString(1));
            }
            conn.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
  
  

  public void removecourse() {
    try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/course_register","root","CAT201**");
            Statement stmt= conn.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT COURSE_NAME FROM course_register.course;");
            while(rs.next())
            {
               registeredCourse.getItems().addAll(rs.getString(1));
            }
            conn.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
  }

  private void suggestcourse() {
    try
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/course_register","root","CAT201**");
        Statement stmt= conn.createStatement();
        ResultSet rs=stmt.executeQuery("SELECT COURSE_NAME FROM course_register.course;");
        while(rs.next())
        {
           courseSuggestion.getItems().addAll(rs.getString(1));
        }
        conn.close();
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    addcourse();
    removecourse();
    suggestcourse();
  }

}
