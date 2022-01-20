package sample;

import java.io.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File; // Import the File class
import java.io.FileWriter; // Import the FileWriter class
import java.io.FileNotFoundException; // Import this class to handle errors
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class courseRegController extends Controller implements Initializable {

  // private final ObservableList<Courses> dataList =
  // FXCollections.observableArrayList();
  @FXML
  private JFXButton addCourseButton, removeCourseButton;

  @FXML
  private ListView<String> courseList, registeredCourse;

  @FXML
  private ImageView printPreview;

  @FXML
  private Text courseTitle, time, desc;

  @FXML
  private TextField searchCourse;

  public void StuDashboard(MouseEvent event) throws IOException {
    switchTo(event, "stuDash.fxml");
  }

  public void LogOut(MouseEvent event) throws IOException {
    switchTo(event, "logout.fxml");
  }

  public void ContactUs(MouseEvent event) throws IOException {
    switchTo(event, "stuReport.fxml");
  }

  public void printPreview(MouseEvent event) throws IOException {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("classes/Student.fxml"));
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();

      stage.setTitle("Select Course List pdf directory");
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
      System.out.println("Can't load new window");
    }

  }

  public void openBrowser() throws URISyntaxException, IOException {
    openLink();
  }

  // Read and display list of all courses that can be registered from txt file
  public void displaycourselist() {
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

  // Read and display Courses Registered of the student from txt file
  public void displaycourseregistered() {
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

  public void addcourse() {
    if (courseList.getSelectionModel().getSelectedItem() != null) {
      // Store Courses Registered into txt file
      try (FileWriter myWriter = new FileWriter("student1registeredCourse.txt", true)) {
        String linetoAdd = courseList.getSelectionModel().getSelectedItem();
        myWriter.write(String.valueOf(linetoAdd), 0, String.valueOf(linetoAdd).length());
        myWriter.write("\n");
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }

      // Update both Registered Course ListView, Course List ListView and
      // filter out courses that have been registered by the student.
      updatebothlist();

      // Clear the searchCourse Textfield after a course is registered.
      searchCourse.clear();
    }
  }


  public void removecourse() {
    if (registeredCourse.getSelectionModel().getSelectedItem() != null) {
      // Remove Courses Registered from txt file
      try {
        File file = new File("student1registeredCourse.txt");
        File temp = new File("TempFile.txt");
        // File temp = File.createTempFile("temporarystudent", ".txt",
        // file.getParentFile());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(temp));

        String lineToRemove = registeredCourse.getSelectionModel().getSelectedItem();
        String currentLine;

        while ((currentLine = reader.readLine()) != null) {
          // Trim newline when comparing with lineToRemove
          String trimmedLine = currentLine.trim();
          if (trimmedLine.equals(lineToRemove))
            continue;
          writer.write(currentLine + System.getProperty("line.separator"));
        }

        // Close the reader and writer (preferably in the finally block).
        reader.close();
        writer.close();
        // Delete the file.
        file.delete();
        // Rename the temp file.
        temp.renameTo(file);

      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }

      // Update both Registered Course ListView, Course List ListView and
      // filter out courses that have been registered by the student.
      updatebothlist();
    }
  }

  public void searchcourse() {
    // Get the list of courses that can be registered by the student and stored in
    // an ArrayList named "list"
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

    // Perform the search(filter) in "list" and output the result in another
    // ArrayList named "result"
    List<String> result = list
        .stream()
        .filter(x -> x.toLowerCase().contains(searchCourse.getText().toLowerCase()))
        .collect(Collectors.toList());

    // Convert the elements in "result" from ArrayList to String
    StringBuilder strbul = new StringBuilder();
    for (String str : result) {
      strbul.append(str);
      strbul.append("\n");
    }

    String str = strbul.toString();

    // Clear the Course List ListView
    courseList.getItems().clear();

    // Insert Course List Search Result into txt file
    try (FileWriter myWriter = new FileWriter("searchCourse.txt", true)) {
      myWriter.write(str);
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    // Display the search result in Listview
    try {
      File fileObj = new File("searchCourse.txt");
      Scanner fileReader = new Scanner(fileObj);
      while (fileReader.hasNextLine()) {
        courseList.getItems().add(fileReader.nextLine());
      }
      fileReader.close();

      // Clear searchCourse.txt
      PrintWriter writer = new PrintWriter("searchCourse.txt");
      writer.print("");
      writer.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public void updatebothlist() {
    // Clear the Registered Course ListView
    registeredCourse.getItems().clear();
    // Read and Display Courses Registered from txt file
    displaycourseregistered();
    // Filter out courses that have been registered by the student,
    // i.e. Only contains the courses that have not been registered by the student.
    // e.g. Output.txt = Input.txt – Delete.txt
    // In this case: studentcourseList = courseList – studentregisteredCourse
    try {
      // PrintWriter object for output.txt
      PrintWriter pw = new PrintWriter("student1courseList.txt");
      // BufferedReader object for delete.txt
      BufferedReader br2 = new BufferedReader(new FileReader("student1registeredCourse.txt"));
      String line2 = br2.readLine();
      // hashset for storing lines of delete.txt
      HashSet<String> hs = new HashSet<String>();
      // loop for each line of delete.txt
      while (line2 != null) {
        hs.add(line2);
        line2 = br2.readLine();
      }
      // BufferedReader object for input.txt
      BufferedReader br1 = new BufferedReader(new FileReader("courseList.txt"));
      String line1 = br1.readLine();
      // loop for each line of input.txt
      while (line1 != null) {
        // if line is not present in delete.txt, write it to output.txt
        if (!hs.contains(line1)) {
          pw.println(line1);
        }

        line1 = br1.readLine();
      }

      // Flush the stream
      pw.flush();

      // Closing resources
      br1.close();
      br2.close();
      pw.close();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    // Clear the Course List ListView
    courseList.getItems().clear();
    // Read and display list of all courses that can be registered from txt file
    displaycourselist();
  }

  public void coursesuggestion() {
    System.out.println("function coursesuggestion() is executed.");
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    displaycourselist();
    displaycourseregistered();
    coursesuggestion();
    courseTitle.setText("CAT 201 Software Integrated Development Workshop kdfkslfsfhjshfjdkahahahhahahahahahahahahaha");
    time.setText("Tuesday 3pm - 4pm ahahhahahahahahahahahahadjfhjhjsfhjksffksdhfkshdfjkshfdjdkshfljsakhsah");
    desc.setText(
        "The course serves to dkksjfasjkfdsfkjslkdjfaskljfksjfahahhahahahahahahahahahadjfksdhfjksadhfljashfjkshd");
  }
}