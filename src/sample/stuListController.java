package sample;

import sample.classes.Student;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class stuListController extends Controller implements Initializable {

  @FXML
    private JFXButton addCourseButton, removeCourseButton;

    @FXML
    private ListView<String> courseList, teachingCourse;

    @FXML
    private TextField searchCourse, searchStudent;

    @FXML
    private PieChart pieChart;

    @FXML
    private Text totalStudents;

    @FXML
    private ImageView printPreview;

    @FXML
    private TableView<Student> studentList;

    @FXML
    private TableColumn<?, ?> matric;

    @FXML
    private TableColumn<?, ?> name;

  public void LecDashboard(MouseEvent event) throws IOException {
    switchTo(event, "lecDash.fxml");
  }

  public void LogOut(MouseEvent event) throws IOException {
    switchTo(event, "logout.fxml");
  }

  public void ContactUs(MouseEvent event) throws IOException {
    switchTo(event, "lecReport.fxml");
  }

  public void openBrowser() throws URISyntaxException, IOException {
    openLink();
  }

  //Read and display list of all courses that can be registered from txt file
  public void displaycourselist() {
    try {
      File fileObj = new File("lecturer1courseList.txt");
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

  //Read and display teaching courses of the lecturer from txt file
  public void displayteachingcourse() {
    try {
      File fileObj = new File("lecturer1teachingCourse.txt");
      Scanner fileReader = new Scanner(fileObj);
        while (fileReader.hasNextLine()) {
          teachingCourse.getItems().add(fileReader.nextLine());
        }
      fileReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public void addcourse(){
    if(courseList.getSelectionModel().getSelectedItem() != null){
      //Store Courses Added into txt file
      try (FileWriter myWriter = new FileWriter("lecturer1teachingCourse.txt",true)){
        String linetoAdd = courseList.getSelectionModel().getSelectedItem();
        myWriter.write(String.valueOf(linetoAdd), 0, String.valueOf(linetoAdd).length());
        myWriter.write("\n");
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
    
    //Update both Teaching Course ListView, Course List ListView and 
    //filter out courses that have been registered by the lecturer.
    updatebothlist();
    
    //Clear the searchCourse Textfield after a course is registered.
    searchCourse.clear();
    } 
  }

  public void removecourse(){
    if(teachingCourse.getSelectionModel().getSelectedItem() != null){
      //Remove Teaching Course from txt file
      try{
        File file = new File("lecturer1teachingCourse.txt");
        File temp = new File("lecturer1TempFile.txt");
        //File temp = File.createTempFile("temporarystudent", ".txt", file.getParentFile());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(temp));

        String lineToRemove = teachingCourse.getSelectionModel().getSelectedItem();
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
          //Trim newline when comparing with lineToRemove
          if(currentLine.trim().equals(lineToRemove)) continue;
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

    //Update both Teaching Course ListView, Course List ListView and 
    //filter out courses that have been registered by the lecturer.
    updatebothlist();
    }
  }

  public void searchcourse() {
    //Get the list of courses that can be registered by the lecturer and stored in an ArrayList named "list"
    List<String> list = new ArrayList<>();
    try {
        File fileObj = new File("lecturer1courseList.txt");
        Scanner fileReader = new Scanner(fileObj);
        while (fileReader.hasNextLine()) {
          list.add(fileReader.nextLine());
        }
        fileReader.close();
    } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }

    //Perform the search(filter) in "list" and output the result in another ArrayList named "result"
    List<String> result = list
      .stream()
      .filter(x -> x.toLowerCase().contains(searchCourse.getText().toLowerCase()))
      .collect(Collectors.toList());

    //Convert the elements in "result" from ArrayList to String
    StringBuilder strbul = new StringBuilder();
    for(String str : result){
        strbul.append(str);
        strbul.append("\n");
    }

    String str=strbul.toString();

    //Clear the Course List ListView
    courseList.getItems().clear();

    //Insert Course List Search Result into txt file
    try (FileWriter myWriter = new FileWriter("lecturerSearchCourse.txt",true)){
      myWriter.write(str);
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    //Display the search result in Listview
    try {
      File fileObj = new File("lecturerSearchCourse.txt");
      Scanner fileReader = new Scanner(fileObj);
      while (fileReader.hasNextLine()) {
        courseList.getItems().add(fileReader.nextLine());
      }
      fileReader.close();

      //Clear lecturerSearchCourse.txt
      PrintWriter writer = new PrintWriter("lecturerSearchCourse.txt");
      writer.print("");
      writer.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public void updatebothlist(){
    //Clear the Teaching Course ListView
    teachingCourse.getItems().clear();
    //Read and Display Teaching Courses from txt file
    displayteachingcourse();
    //Filter out courses that have been registered by the lecturer,
    //i.e. Only contains the courses that have not been registered by the lecturer.
    //e.g. Output.txt = Input.txt – Delete.txt
    //In this case: lecturercourseList = courseList – lecturerteachingCourse
    try{
      //PrintWriter object for output.txt
      PrintWriter pw = new PrintWriter("lecturer1courseList.txt");
      //BufferedReader object for delete.txt
      BufferedReader br2 = new BufferedReader(new FileReader("lecturer1teachingCourse.txt"));
      String line2 = br2.readLine();
      //hashset for storing lines of delete.txt
      HashSet<String> hs = new HashSet<String>();
      //loop for each line of delete.txt
      while(line2 != null){
        hs.add(line2);
        line2 = br2.readLine();
      }
      //BufferedReader object for input.txt
      BufferedReader br1 = new BufferedReader(new FileReader("courseList.txt"));
      String line1 = br1.readLine();
      //loop for each line of input.txt
      while(line1 != null){
        //if line is not present in delete.txt, write it to output.txt
        if(!hs.contains(line1))
        {pw.println(line1);}
            
        line1 = br1.readLine();
      }
      
      //Flush the stream
      pw.flush();
        
      //Closing resources
      br1.close();
      br2.close();
      pw.close();
    } catch (IOException e){
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    //Clear the Course List ListView
    courseList.getItems().clear();
    //Read and display list of all courses that can be registered from txt file
    displaycourselist();
  }

  public void studentYear() {

  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    displaycourselist();
    displayteachingcourse();
  }
}

