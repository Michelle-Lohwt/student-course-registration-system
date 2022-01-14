package sample;

import java.io.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;  // Import the File class
import java.io.FileWriter;   // Import the FileWriter class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileReader;
import java.io.IOException;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;

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
  
  //Read and display list of all courses that can be registered from txt file
  private void displaycourselist(){
    try {
      File fileObj = new File("student1courseList.txt");
      Scanner fileReader = new Scanner(fileObj);
      while (fileReader.hasNextLine()) {
        courseList.getItems().add(fileReader.nextLine());
      }
      fileReader.close();
    
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
  
  //Read and Display Courses Registered from txt file
  private void displaycourseregistered(){
    try {
      File fileObj = new File("student1registeredCourse.txt");
      Scanner fileReader = new Scanner(fileObj);
      while (fileReader.hasNextLine()) {
        registeredCourse.getItems().add(fileReader.nextLine());
      }
      fileReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public void addcourse()  {    
    if(courseList.getSelectionModel().getSelectedItem() != null){
      //Store Courses Registered into txt file
      try (FileWriter myWriter = new FileWriter("student1registeredCourse.txt",true)){
        myWriter.write(String.valueOf(courseList.getSelectionModel().getSelectedItem()), 0,
        String.valueOf(courseList.getSelectionModel().getSelectedItem()).length());
        myWriter.write("\n");
        //System.out.println("Successfully wrote to the file.");
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
    
      //Clear the Registered Course ListView
    registeredCourse.getItems().clear();

    //Read and Display Courses Registered from txt file
    displaycourseregistered();

    //Filter out courses that have been registered by the student.
    filtercourselist();

    //Clear the Course List ListView
    courseList.getItems().clear();

    //Read and display list of all courses that can be registered from txt file
    displaycourselist();
    
    //Clear the searchCourse Textfield after a course is registered.
    searchCourse.clear();
    } 
  }

  public void removecourse()  {
    //Remove Courses Registered from txt file
    if(registeredCourse.getSelectionModel().getSelectedItem() != null){      
      try{
        File file = new File("student1registeredCourse.txt");
        File temp = new File("TempFile.txt");
        //File temp = File.createTempFile("temporarystudent", ".txt", file.getParentFile());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(temp));

        String lineToRemove = registeredCourse.getSelectionModel().getSelectedItem();
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
          // trim newline when comparing with lineToRemove
          String trimmedLine = currentLine.trim();
          if(trimmedLine.equals(lineToRemove)) continue;
          writer.write(currentLine + System.getProperty("line.separator"));
        }

        //Close the reader and writer (preferably in the finally block).
        reader.close();
        writer.close();
        //Delete the file.
        file.delete();
        //Rename the temp file.
        temp.renameTo(file);
        
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
      //Clear the Registered Course ListView
      registeredCourse.getItems().clear();
      //Read and Display Courses Registered from txt file
      displaycourseregistered();

      //Filter out courses that have been registered by the student.
      filtercourselist();

      //Clear the Course List ListView
      courseList.getItems().clear();

      //Read and display list of all courses that can be registered from txt file
      displaycourselist();
    }
  }

  public void searchcourse() {
    List<String> list = new ArrayList<>();
    try {
        File fileObj = new File("student1courseList.txt");
        Scanner fileReader = new Scanner(fileObj);
        while (fileReader.hasNextLine()) {
            list.add(fileReader.nextLine());
        }
        fileReader.close();
    } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }   

    List<String> result = list
      .stream()
      .filter(x -> x.toLowerCase().contains(searchCourse.getText().toLowerCase()))
      .collect(Collectors.toList());

    //conversion of ArrayList to String
    StringBuilder strbul = new StringBuilder();
    for(String str : result)
    {
        strbul.append(str);
        //for adding comma between elements
        strbul.append("\n");
    }

    String str=strbul.toString();

    //Clear the Course List ListView
    courseList.getItems().clear();

    //courseList.getItems().add(str);

    //Insert Course List Search Result into txt file
    try (FileWriter myWriter = new FileWriter("searchCourse.txt",true)){
      myWriter.write(str);
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    //Display the search result in Listview
    try {
      File fileObj = new File("searchCourse.txt");
      Scanner fileReader = new Scanner(fileObj);
      while (fileReader.hasNextLine()) {
        courseList.getItems().add(fileReader.nextLine());
      }
      fileReader.close();

      //Clear searchCourse.txt
      PrintWriter writer = new PrintWriter("searchCourse.txt");
      writer.print("");
      writer.close();    
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
  
  //Filter out courses that have been registered by the student,
  //i.e. Only contains the courses that have not been registered by the student.
  //e.g. Output.txt = Input.txt – Delete.txt
  //In this case: studentcourseList = courseList – studentregisteredCourse
  private void filtercourselist(){
    try{
      // PrintWriter object for output.txt
      PrintWriter pw = new PrintWriter("student1courseList.txt");      
      // BufferedReader object for delete.txt
      BufferedReader br2 = new BufferedReader(new FileReader("student1registeredCourse.txt"));      
      String line2 = br2.readLine();      
      // hashset for storing lines of delete.txt
      HashSet<String> hs = new HashSet<String>();      
      // loop for each line of delete.txt
      while(line2 != null)
      {
        hs.add(line2);
        line2 = br2.readLine();
      }
      // BufferedReader object for input.txt
      BufferedReader br1 = new BufferedReader(new FileReader("courseList.txt"));
      String line1 = br1.readLine();
      // loop for each line of input.txt
      while(line1 != null){
        // if line is not present in delete.txt, write it to output.txt
        if(!hs.contains(line1))
        {pw.println(line1);}
            
        line1 = br1.readLine();
      }
      
      //Flush the stream
      pw.flush();
        
      // closing resources
      br1.close();
      br2.close();
      pw.close();
    } catch (IOException e){
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  private void coursesuggestion() {
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
    displaycourselist();
    displaycourseregistered();
    addcourse();
    removecourse();
    searchcourse();
    filtercourselist();
    coursesuggestion();
  }

}