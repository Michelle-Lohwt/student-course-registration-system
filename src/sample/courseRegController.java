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
import java.nio.file.Files;
import java.nio.file.Paths;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
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
  private Text courseTitle, time;

  @FXML
  private TextArea desc;

  @FXML
  private TextField searchCourse;

  static String stuID;
  static String stuName;

  public static void inputID(String text) {
    stuID = text;
  }

  public static void inputName(String text) {
    stuName = text;
  }

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
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("download.fxml"));
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      Image icon = new Image("sample/images/download.png");
      stage.getIcons().add(icon);
      stage.setTitle("Select Course List pdf directory");
      stage.setResizable(false);
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
      File fileObj = new File("data/Student Course List/" + stuID + ".txt");
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
      File fileObj = new File("data/Student Registered Course/" + stuID + ".txt");
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

  public void searchcourse() {
    // Get the list of courses that can be registered by the student and stored in
    // an ArrayList named "list"
    List<String> list = new ArrayList<>();
    try {
      File fileObj = new File("data/Student Course List/" + stuID + ".txt");
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
    try (FileWriter myWriter = new FileWriter("data/Search.txt", true)) {
      myWriter.write(str);
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    // Display the search result in Listview
    try {
      File fileObj = new File("data/Search.txt");
      Scanner fileReader = new Scanner(fileObj);
      while (fileReader.hasNextLine()) {
        courseList.getItems().add(fileReader.nextLine());
      }
      fileReader.close();
      fileObj.delete();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  // Clear the Course Details
  public void cleardetails() {
    courseTitle.setText("");
    time.setText("");
    desc.setText("");
  }

  // When a Course in the Course List is Clicked, this function will be executed.
  public void courselistdetails() {
    if (courseList.getSelectionModel().getSelectedItem() != null) {
      addCourseButton.setDisable(false);

      // Clear the Course Details before displaying
      cleardetails();

      // Display the Course Details
      courseTitle.setText(courseList.getSelectionModel().getSelectedItem());
      try {
        List<String> lines = Files
            .lines(Paths.get("data/Course Details/" + courseList.getSelectionModel().getSelectedItem() + ".txt"))
            .collect(Collectors.toList());
        time.setText(lines.get(0));
        desc.setText(lines.get(2));
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
    }
  }

  // When a Course in the Course Registered is Clicked, this function will be
  // executed.
  public void courseregistereddetails() {
    if (registeredCourse.getSelectionModel().getSelectedItem() != null) {
      removeCourseButton.setDisable(false);

      // Clear the Course Details before displaying
      cleardetails();

      // Display the Course Details
      courseTitle.setText(registeredCourse.getSelectionModel().getSelectedItem());
      try {
        List<String> lines = Files
            .lines(Paths.get("data/Course Details/" + registeredCourse.getSelectionModel().getSelectedItem() + ".txt"))
            .collect(Collectors.toList());
        time.setText(lines.get(0));
        desc.setText(lines.get(2));
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
    }
  }

  public void addcourse() {
    if (courseList.getSelectionModel().getSelectedItem() != null) {
      // Store Courses Registered into txt file
      try (FileWriter myWriter = new FileWriter("data/Student Registered Course/" + stuID + ".txt", true)) {
        String linetoAdd = courseList.getSelectionModel().getSelectedItem();
        myWriter.write(String.valueOf(linetoAdd), 0, String.valueOf(linetoAdd).length());
        myWriter.write("\n");
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }

      // Store the student information into the respective courseStudentList.txt
      try (FileWriter myWriter = new FileWriter(
          "data/Course Student List/" + courseList.getSelectionModel().getSelectedItem() + ".txt", true)) {
        myWriter.write(String.valueOf(stuName), 0, String.valueOf(stuName).length());
        myWriter.write("\t");
        myWriter.write(String.valueOf(stuID), 0, String.valueOf(stuID).length());
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

      addCourseButton.setDisable(true);
      removeCourseButton.setDisable(true);
    }
  }

  public void removecourse() {
    if (registeredCourse.getSelectionModel().getSelectedItem() != null) {
      // Remove Courses Registered from txt file
      try {
        File file = new File("data/Student Registered Course/" + stuID + ".txt");
        File temp = new File("data/Student Registered Course/TempFile.txt");
        // File temp = File.createTempFile("temporarystudent",
        // ".txt",file.getParentFile());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(temp));

        String lineToRemove = registeredCourse.getSelectionModel().getSelectedItem();
        String currentLine;

        while ((currentLine = reader.readLine()) != null) {
          // Trim newline when comparing with lineToRemove
          if (currentLine.trim().equals(lineToRemove))
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
      // Remove the student information from the respective courseStudentList.txt
      try {
        File file = new File(
            "data/Course Student List/" + registeredCourse.getSelectionModel().getSelectedItem() + ".txt");
        File temp = new File("data/Course Student List/TempFile.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
        String lineToRemove = stuName + "\t" + stuID;
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
          // Trim newline when comparing with lineToRemove
          if (currentLine.trim().equals(lineToRemove))
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

      addCourseButton.setDisable(true);
      removeCourseButton.setDisable(true);
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
      PrintWriter pw = new PrintWriter("data/Student Course List/" + stuID + ".txt");
      // BufferedReader object for delete.txt
      BufferedReader br2 = new BufferedReader(new FileReader("data/Student Registered Course/" + stuID + ".txt"));
      String line2 = br2.readLine();
      // hashset for storing lines of delete.txt
      HashSet<String> hs = new HashSet<String>();
      // loop for each line of delete.txt
      while (line2 != null) {
        hs.add(line2);
        line2 = br2.readLine();
      }
      // BufferedReader object for input.txt
      BufferedReader br1 = new BufferedReader(new FileReader("data/Course List.txt"));
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

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    displaycourselist();
    displaycourseregistered();
    addCourseButton.setDisable(true);
    removeCourseButton.setDisable(true);
  }
}