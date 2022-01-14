package sample;
// import sample.classes.Courses;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
  public void printPreview(MouseEvent event)throws IOException{
    try{
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("classes/Student.fxml"));
      Parent root1= (Parent) fxmlLoader.load();
      Stage stage= new Stage();

      stage.setTitle("Select Course List pdf directory");
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e){
      System.out.println("Can't load new window");
    }
   //switchTo(event,"classes/Student.fxml");
  }
  public void openBrowser() throws URISyntaxException, IOException {
    openLink();
  }

  public void addcourse() {

    courseList.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseList.getItems().add("CMT221 - DATABASE ORGANISATIONS AND DESIGN");
    courseList.getItems().add("CST232 - OPERATING SYSTEMS");
    courseList.getItems().add("CSE241 - FOUNDATION OF SOFTWARE ENGINEERING");
    courseList.getItems().add("CPT111 - PRINCIPLES OF PROGRAMMING");
    courseList.getItems().add("CPT112 - DISCRETE STRUCTURES");
    courseList.getItems().add("CST131 - COMPUTER ORGANISATIONS");
    courseList.getItems().add("CPT113 - PROGRAMMING METHODOLOGY AND DATA STRUCTURES");
    courseList.getItems().add("CPT115 - MATHEMATICAL METHODS FOR COMPUTER SCIENCE");
    courseList.getItems().add("CPC151 - FUNDAMENTALS OF LOGIC AND ARTIFICIAL INTELLIGENCE");
    courseList.getItems().add("AKW103 - INTRODUCTION TO MANAGEMENT");
    courseList.getItems().add("AKW104 - ACCOUNTING AND FINANCE");
    courseList.getItems().add("ATW202 - BUSINESS RESEARCH METHOD");
    courseList.getItems().add("ATW241 - PRINCIPLES OF MARKETING");
    courseList.getItems().add("ATW262 - PRINCIPLES OF FINANCE");
    courseList.getItems().add("MAA101 - CALCULUS FOR SCIENCE STUDENTS");
    courseList.getItems().add("MAT111 - LINEAR ALGEBRA");
    courseList.getItems().add("MAT181 - PROGRAMMING FOR SCIENTIFIC APPLICATIONS");
    courseList.getItems().add("MAA111 - ALGEBRA FOR SCIENCE STUDENTS");
    courseList.getItems().add("LAK100 - KOREAN LANGUAGE");
    courseList.getItems().add("LAJ100 - JAPANESE LANGUAGE");
    courseList.getItems().add("LKM400 - BAHASA MALAYSIA");
    courseList.getItems().add("LSP300 - ACADEMIC ENGLISH");
    courseList.getItems().add("HFF225 - PHILOSOPHY AND CURRENT ISSUES");
    courseList.getItems().add("HFE224 - APPRECIATION OF ETHICS AND CIVILISATIONS");
    }
  
  

  public void removecourse() {
    registeredCourse.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    registeredCourse.getItems().add("CMT221 - DATABASE ORGANISATIONS AND DESIGN");
    registeredCourse.getItems().add("CST232 - OPERATING SYSTEMS");
    registeredCourse.getItems().add("CSE241 - FOUNDATION OF SOFTWARE ENGINEERING");
    registeredCourse.getItems().add("CPT111 - PRINCIPLES OF PROGRAMMING");
    registeredCourse.getItems().add("CPT112 - DISCRETE STRUCTURES");
    registeredCourse.getItems().add("CST131 - COMPUTER ORGANISATIONS");
    registeredCourse.getItems().add("CPT113 - PROGRAMMING METHODOLOGY AND DATA STRUCTURES");
    registeredCourse.getItems().add("CPT115 - MATHEMATICAL METHODS FOR COMPUTER SCIENCE");
    registeredCourse.getItems().add("CPC151 - FUNDAMENTALS OF LOGIC AND ARTIFICIAL INTELLIGENCE");
    registeredCourse.getItems().add("AKW103 - INTRODUCTION TO MANAGEMENT");
    registeredCourse.getItems().add("AKW104 - ACCOUNTING AND FINANCE");
    registeredCourse.getItems().add("ATW202 - BUSINESS RESEARCH METHOD");
    registeredCourse.getItems().add("ATW241 - PRINCIPLES OF MARKETING");
    registeredCourse.getItems().add("ATW262 - PRINCIPLES OF FINANCE");
    registeredCourse.getItems().add("MAA101 - CALCULUS FOR SCIENCE STUDENTS");
    registeredCourse.getItems().add("MAT111 - LINEAR ALGEBRA");
    registeredCourse.getItems().add("MAT181 - PROGRAMMING FOR SCIENTIFIC APPLICATIONS");
    registeredCourse.getItems().add("MAA111 - ALGEBRA FOR SCIENCE STUDENTS");
    registeredCourse.getItems().add("LAK100 - KOREAN LANGUAGE");
    registeredCourse.getItems().add("LAJ100 - JAPANESE LANGUAGE");
    registeredCourse.getItems().add("LKM400 - BAHASA MALAYSIA");
    registeredCourse.getItems().add("LSP300 - ACADEMIC ENGLISH");
    registeredCourse.getItems().add("HFF225 - PHILOSOPHY AND CURRENT ISSUES");
    registeredCourse.getItems().add("HFE224 - APPRECIATION OF ETHICS AND CIVILISATIONS");
  }

  private void suggestcourse() {
    courseSuggestion.getItems().add("CAT201 - INTEGRATED SOFTWARE DEVELOPMENT WORKSHOP");
    courseSuggestion.getItems().add("CMT221 - DATABASE ORGANISATIONS AND DESIGN");
    courseSuggestion.getItems().add("CST232 - OPERATING SYSTEMS");
    courseSuggestion.getItems().add("CSE241 - FOUNDATION OF SOFTWARE ENGINEERING");
    courseSuggestion.getItems().add("CPT111 - PRINCIPLES OF PROGRAMMING");
    courseSuggestion.getItems().add("CPT112 - DISCRETE STRUCTURES");
    courseSuggestion.getItems().add("CST131 - COMPUTER ORGANISATIONS");
    courseSuggestion.getItems().add("CPT113 - PROGRAMMING METHODOLOGY AND DATA STRUCTURES");
    courseSuggestion.getItems().add("CPT115 - MATHEMATICAL METHODS FOR COMPUTER SCIENCE");
    courseSuggestion.getItems().add("CPC151 - FUNDAMENTALS OF LOGIC AND ARTIFICIAL INTELLIGENCE");
    courseSuggestion.getItems().add("AKW103 - INTRODUCTION TO MANAGEMENT");
    courseSuggestion.getItems().add("AKW104 - ACCOUNTING AND FINANCE");
    courseSuggestion.getItems().add("ATW202 - BUSINESS RESEARCH METHOD");
    courseSuggestion.getItems().add("ATW241 - PRINCIPLES OF MARKETING");
    courseSuggestion.getItems().add("ATW262 - PRINCIPLES OF FINANCE");
    courseSuggestion.getItems().add("MAA101 - CALCULUS FOR SCIENCE STUDENTS");
    courseSuggestion.getItems().add("MAT111 - LINEAR ALGEBRA");
    courseSuggestion.getItems().add("MAT181 - PROGRAMMING FOR SCIENTIFIC APPLICATIONS");
    courseSuggestion.getItems().add("MAA111 - ALGEBRA FOR SCIENCE STUDENTS");
    courseSuggestion.getItems().add("LAK100 - KOREAN LANGUAGE");
    courseSuggestion.getItems().add("LAJ100 - JAPANESE LANGUAGE");
    courseSuggestion.getItems().add("LKM400 - BAHASA MALAYSIA");
    courseSuggestion.getItems().add("LSP300 - ACADEMIC ENGLISH");
    courseSuggestion.getItems().add("HFF225 - PHILOSOPHY AND CURRENT ISSUES");
    courseSuggestion.getItems().add("HFE224 - APPRECIATION OF ETHICS AND CIVILISATIONS");
  }

  
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    addcourse();
    removecourse();
    suggestcourse();
  }

}



