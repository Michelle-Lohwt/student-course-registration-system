package sample;

import sample.classes.StudentList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class stuListController extends Controller implements Initializable {

  @FXML
  private JFXButton addCourseButton, removeCourseButton, editCourseButton, saveCourseDetailsButton;

  @FXML
  private ListView<String> courseList, teachingCourse;

  @FXML
  private TextField searchCourse, searchStudent;

  @FXML
  public TableView<StudentList> studentList;

  @FXML
  public TableColumn<StudentList, String> name;

  @FXML
  public TableColumn<StudentList, String> matric;

  @FXML
  private Text courseTitle;

  @FXML
  private TextField time;

  @FXML
  private TextArea desc;

  @FXML
  private ImageView downloadStudent, downloadTeaching;

  static String lecID;

  public static void inputID(String text) {
    lecID = text;
  }

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

  // Read and display list of all courses that can be registered from txt file
  public void displaycourselist() {
    try {
      File fileObj = new File("data/Lecturer Course List/" + lecID + ".txt");
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

  // Read and display teaching courses of the lecturer from txt file
  public void displayteachingcourse() {
    try {
      File fileObj = new File("data/Lecturer Teaching Course/" + lecID + ".txt");
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

  public void searchcourse() {
    // Get the list of courses that can be registered by the lecturer and stored in
    // an ArrayList named "list"
    List<String> list = new ArrayList<>();
    try {
      File fileObj = new File("data/Lecturer Course List/" + lecID + ".txt");
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

  //Clear the Course Details
  public void cleardetails(){
    courseTitle.setText("");
    time.clear();
    desc.clear();
  }

  // When a Course in the Course List is Clicked, this function will be executed.
  public void courselist() {
    if (courseList.getSelectionModel().getSelectedItem() != null) {
      addCourseButton.setDisable(false);

      //Clear the TableView
      studentList.getItems().clear();

      //Clear the Course Details
      cleardetails();

      // Display the Course Details
      downloadController.inputCourseTitle(courseList.getSelectionModel().getSelectedItem());
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

      editCourseButton.setDisable(false);

      //Display the Course Student List
      try{
        Collection<StudentList> list = Files.readAllLines(new File("data/Course Student List/"+courseList.getSelectionModel().getSelectedItem()+".txt").toPath())
              .stream()
              .map(line -> {
              String[] details = line.split("\t");
              StudentList sl = new StudentList();
              sl.setName(details[0]);
              sl.setMatric(details[1]);
              return sl;
              })
              .collect(Collectors.toList());
  
        ObservableList<StudentList> details = FXCollections.observableArrayList(list);
            
        name.setCellValueFactory(data -> data.getValue().nameProperty());
        matric.setCellValueFactory(data -> data.getValue().matricProperty());
            
        studentList.setItems(details);
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
    }
  }

  // When a Course in the Course Registered is Clicked, this function will be
  // executed.
  public void teachingcourse() {
    if (teachingCourse.getSelectionModel().getSelectedItem() != null) {
      removeCourseButton.setDisable(false);

      //Clear the TableView
      studentList.getItems().clear();

      //Clear the Course Details
      cleardetails();

      // Display the Course Details
      courseTitle.setText(teachingCourse.getSelectionModel().getSelectedItem());
      downloadController.inputCourseTitle(teachingCourse.getSelectionModel().getSelectedItem());
      try {
        List<String> lines = Files
            .lines(Paths.get("data/Course Details/" + teachingCourse.getSelectionModel().getSelectedItem() + ".txt"))
            .collect(Collectors.toList());
        time.setText(lines.get(0));
        desc.setText(lines.get(2));
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }

      editCourseButton.setDisable(false);

      //Display the Course Student List
      try{
        Collection<StudentList> list = Files.readAllLines(new File("data/Course Student List/"+teachingCourse.getSelectionModel().getSelectedItem()+".txt").toPath())
              .stream()
              .map(line -> {
              String[] details = line.split("\t");
              StudentList sl = new StudentList();
              sl.setName(details[0]);
              sl.setMatric(details[1]);
              return sl;
              })
              .collect(Collectors.toList());
  
        ObservableList<StudentList> details = FXCollections.observableArrayList(list);
            
        name.setCellValueFactory(data -> data.getValue().nameProperty());
        matric.setCellValueFactory(data -> data.getValue().matricProperty());
            
        studentList.setItems(details);
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
    }
  }

  public void addcourse() {
    if (courseList.getSelectionModel().getSelectedItem() != null) {
      // Store Courses Added into txt file
      try (FileWriter myWriter = new FileWriter("data/Lecturer Teaching Course/" + lecID + ".txt", true)) {
        String linetoAdd = courseList.getSelectionModel().getSelectedItem();
        myWriter.write(String.valueOf(linetoAdd), 0, String.valueOf(linetoAdd).length());
        myWriter.write("\n");
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }

      // Update both Teaching Course ListView, Course List ListView and
      // filter out courses that have been registered by the lecturer.
      updatebothlist();

      // Clear the searchCourse Textfield after a course is registered.
      searchCourse.clear();

      addCourseButton.setDisable(true);
      removeCourseButton.setDisable(true);
    }
  }

  public void removecourse() {
    if (teachingCourse.getSelectionModel().getSelectedItem() != null) {
      // Remove Teaching Course from txt file
      try {
        File file = new File("data/Lecturer Teaching Course/" + lecID + ".txt");
        File temp = new File("data/Lecturer Teaching Course/TempFile.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(temp));

        String lineToRemove = teachingCourse.getSelectionModel().getSelectedItem();
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

      // Update both Teaching Course ListView, Course List ListView and
      // filter out courses that have been registered by the lecturer.
      updatebothlist();

      addCourseButton.setDisable(true);
      removeCourseButton.setDisable(true);
    }
  }

  public void updatebothlist() {
    // Clear the Teaching Course ListView
    teachingCourse.getItems().clear();
    // Read and Display Teaching Courses from txt file
    displayteachingcourse();
    // Filter out courses that have been registered by the lecturer,
    // i.e. Only contains the courses that have not been registered by the lecturer.
    // e.g. Output.txt = Input.txt – Delete.txt
    // In this case: lecturercourseList = courseList – lecturerteachingCourse
    try {
      // PrintWriter object for output.txt
      PrintWriter pw = new PrintWriter("data/Lecturer Course List/" + lecID + ".txt");
      // BufferedReader object for delete.txt
      BufferedReader br2 = new BufferedReader(new FileReader("data/Lecturer Teaching Course/" + lecID + ".txt"));
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

  public void EditCourse() {
    saveCourseDetailsButton.setDisable(false);
    editCourseButton.setDisable(true);
    courseList.setDisable(true);
    teachingCourse.setDisable(true);
    addCourseButton.setDisable(true);
    removeCourseButton.setDisable(true);

    time.setEditable(true);
    desc.setEditable(true);

    time.setStyle("-fx-border-color: #eb7231");
    desc.setStyle("-fx-border-color: #eb7231");
  }

  public void SaveCourse() {
    saveCourseDetailsButton.setDisable(true);
    editCourseButton.setDisable(false);
    courseList.setDisable(false);
    teachingCourse.setDisable(false);
    addCourseButton.setDisable(true);
    removeCourseButton.setDisable(true);

    time.setEditable(false);
    desc.setEditable(false);

    time.setStyle("-fx-border-color: transparent");
    desc.setStyle("-fx-border-color: transparent");

    try (OutputStreamWriter myWriter = new OutputStreamWriter(new FileOutputStream("data/Course Details/" + courseTitle.getText() + ".txt"), StandardCharsets.UTF_8)) {
      myWriter.write(time.getText().replaceAll("\n", ", "));
      myWriter.write("\n\n");
      myWriter.write(desc.getText().replaceAll("\n", " "));
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
  
  public void searchstudent() {
    // Get the list of students that registered the course and store in ArrayList named "list"
    List<String> list = new ArrayList<>();
    try {
      File fileObj = new File("data/Course Student List/"+courseTitle.getText()+".txt");
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
        .filter(x -> x.toLowerCase().contains(searchStudent.getText().toLowerCase()))
        .collect(Collectors.toList());

    // Convert the elements in "result" from ArrayList to String
    StringBuilder strbul = new StringBuilder();
    for (String str : result) {
      strbul.append(str);
      strbul.append("\n");
    }

    String str = strbul.toString();

    // Insert Search Result into txt file
    try (FileWriter myWriter = new FileWriter("data/Search.txt", true)) {
      myWriter.write(str);
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    // Clear the TableView
    studentList.getItems().clear();

    // Display the search result in Listview
    try{
      Collection<StudentList> table = Files.readAllLines(new File("data/Search.txt").toPath())
            .stream()
            .map(line -> {
            String[] details = line.split("\t");
            StudentList sl = new StudentList();
            sl.setName(details[0]);
            sl.setMatric(details[1]);
            return sl;
            })
            .collect(Collectors.toList());

      ObservableList<StudentList> details = FXCollections.observableArrayList(table);
          
      name.setCellValueFactory(data -> data.getValue().nameProperty());
      matric.setCellValueFactory(data -> data.getValue().matricProperty());
          
      studentList.setItems(details);

      new File("data/Search.txt").delete();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public void printTeachCourse(MouseEvent event) throws IOException {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TeachCourseDownload.fxml"));
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      Image icon = new Image("sample/images/download.png");
      stage.getIcons().add(icon);
      stage.setTitle("Select Teaching Course List pdf directory");
      stage.setResizable(false);
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
      System.out.println("Can't load new window");
    }
  }
    public void printStuList(MouseEvent event) throws IOException {
      try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StuListDownload.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Image icon = new Image("sample/images/download.png");
        stage.getIcons().add(icon);
        stage.setTitle("Select Course Student List pdf directory");
        stage.setResizable(false);
        stage.setScene(new Scene(root1));
        stage.show();
      } catch (Exception e) {
        System.out.println("Can't load new window");
      }
  
    }
  
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    displaycourselist();
    displayteachingcourse();
    addCourseButton.setDisable(true);
    removeCourseButton.setDisable(true);
    editCourseButton.setDisable(true);
    saveCourseDetailsButton.setDisable(true);
  }
}