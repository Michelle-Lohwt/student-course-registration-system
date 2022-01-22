package sample;

<<<<<<< HEAD
import sample.classes.Student;
import sample.classes.StudentList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
=======
>>>>>>> dc2130f694f14deb71f6dc4fc4fd242793f04e2f
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.StringConcatFactory;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
<<<<<<< HEAD
import java.util.Scanner;
import java.util.stream.Collectors;

=======
>>>>>>> dc2130f694f14deb71f6dc4fc4fd242793f04e2f
import com.jfoenix.controls.JFXButton;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
<<<<<<< HEAD
import javafx.scene.Scene;
=======
>>>>>>> dc2130f694f14deb71f6dc4fc4fd242793f04e2f
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class stuListController extends Controller implements Initializable {

  @FXML
  private JFXButton addCourseButton, removeCourseButton, editCourseButton, saveCourseDetailsButton;

  @FXML
  private ListView<String> courseList, teachingCourse;

  @FXML
  private TextField searchCourse, searchStudent;

  @FXML
<<<<<<< HEAD
  //public TableView<StudentList> studentList;
  public TableView<StudentList> studentList = new TableView<>();

  @FXML
  //public TableColumn<StudentList, String> name;
  public TableColumn<StudentList, String> name = new TableColumn<>("name");

  @FXML
  //public TableColumn<StudentList, Integer> matric;
  public TableColumn<StudentList, Integer> matric = new TableColumn<>("matric");
=======
  private TableView<?> studentList;

  @FXML
  private TableColumn<?, ?> matric;

  @FXML
  private TableColumn<?, ?> name;
>>>>>>> dc2130f694f14deb71f6dc4fc4fd242793f04e2f

  @FXML
  private Text courseTitle;

  @FXML
  private TextArea time, desc;

  @FXML
  private ImageView downloadStudent, downloadTeaching;

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
      File fileObj = new File("data/Lecturer Course List/"+"123456"+".txt");
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
      File fileObj = new File("data/Lecturer Teaching Course/"+"123456"+".txt");
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
      try (FileWriter myWriter = new FileWriter("data/Lecturer Teaching Course/"+"123456"+".txt",true)){
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
        File file = new File("data/Lecturer Teaching Course/"+"123456"+".txt");
        File temp = new File("data/Lecturer Teaching Course/TempFile.txt");
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
        File fileObj = new File("data/Lecturer Course List/"+"123456"+".txt");
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
    try (FileWriter myWriter = new FileWriter("data/Search.txt",true)){
      myWriter.write(str);
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    //Display the search result in Listview
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
      PrintWriter pw = new PrintWriter("data/Lecturer Course List/"+"123456"+".txt");
      //BufferedReader object for delete.txt
      BufferedReader br2 = new BufferedReader(new FileReader("data/Lecturer Teaching Course/"+"123456"+".txt"));
      String line2 = br2.readLine();
      //hashset for storing lines of delete.txt
      HashSet<String> hs = new HashSet<String>();
      //loop for each line of delete.txt
      while(line2 != null){
        hs.add(line2);
        line2 = br2.readLine();
      }
      //BufferedReader object for input.txt
      BufferedReader br1 = new BufferedReader(new FileReader("data/Course List.txt"));
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

  //When a Course in the Course List is Clicked, this function will be executed.
  public void courselist() {
    if(courseList.getSelectionModel().getSelectedItem() != null){
      
      addCourseButton.setDisable(false);
      
      //Display the Course Details
      courseTitle.setText(courseList.getSelectionModel().getSelectedItem());
      try{
        List<String> lines = Files.lines(Paths.get("data/Course Details/"+courseList.getSelectionModel().getSelectedItem()+".txt")).collect(Collectors.toList());
        time.setText(lines.get(0));
        desc.setText(lines.get(2));
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }

      editCourseButton.setDisable(false);
      /*
      //Display the Course Student List
      try{
        Collection<StudentList> list = Files.readAllLines(new File("data/Course Student List/"+courseList.getSelectionModel().getSelectedItem()+".txt").toPath())
                  .stream()
                  .map(line -> {
                      String[] details = line.split("\t");
                      new StudentList().setName(details[0]);
                      new StudentList().setMatric(details[1]);
                      return new StudentList();
                  })
                  .collect(Collectors.toList());

        ObservableList<StudentList> details = FXCollections.observableArrayList(list);

        TableView<StudentList> studentList = new TableView<>();
        TableColumn<StudentList, String> col1 = new TableColumn<>();
        TableColumn<StudentList, String> col2 = new TableColumn<>();

        studentList.getColumns().addAll(col1, col2);

        col1.setCellValueFactory(data -> data.getValue().nameProperty());
        col2.setCellValueFactory(data -> data.getValue().matricProperty());

        studentList.setItems(details);

        //primaryStage.setScene(new Scene(new StackPane(studentList)));
        //primaryStage.show();
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
      */     

      //File file = new File("data/Course Student List/"+courseList.getSelectionModel().getSelectedItem()+".txt");
      //ObservableList<StudentList> list = FXCollections.observableArrayList();

      name.setCellValueFactory(new PropertyValueFactory<StudentList, String>("name"));
      matric.setCellValueFactory(new PropertyValueFactory<StudentList, Integer>("matric"));

      studentList = new TableView<>();

      try {
        BufferedReader br = new BufferedReader(new FileReader(new File("data/Course Student List/"+courseList.getSelectionModel().getSelectedItem()+".txt")));
        String line;

        while ((line = br.readLine()) != null){
          //String data = inputStream.nextLine();
//          System.out.println(data);
          String[] values_line = line.split("\t");
//              Name[0] = (String)values[0];
//              Score[0] = (String)values[1];
//          for(int i = 0; i < values_line.length; i++){
            System.out.println(String.valueOf(values_line[0]+"\t"+values_line[1]));
            studentList.getItems().add(new StudentList(String.valueOf(values_line[0]),Integer.parseInt(values_line[1])));
            //list.getItems().add(new StudentList(String.valueOf(values_line[0]),Integer.parseInt(values_line[1])));
//          }
        }
        br.close();
      } catch (Exception e) {
        e.printStackTrace();
      }

      //name.setCellValueFactory(new PropertyValueFactory<StudentList, String>("name"));
      //matric.setCellValueFactory(new PropertyValueFactory<StudentList, Integer>("matric"));
      //name.setCellValueFactory(data -> data.getValue.nameProperty());
      //matric.setCellValueFactory(data -> data.getValue.matricProperty());

      //bind list into the table
      //studentList.setItems(list);

      studentList.getColumns().addAll(name, matric);
      
    }
  }

  //When a Course in the Course Registered is Clicked, this function will be executed.
  public void teachingcourse() {
    if(teachingCourse.getSelectionModel().getSelectedItem() != null){
      
      removeCourseButton.setDisable(false);
      
      //Display the Course Details
      courseTitle.setText(teachingCourse.getSelectionModel().getSelectedItem());
      try{
        List<String> lines = Files.lines(Paths.get("data/Course Details/"+teachingCourse.getSelectionModel().getSelectedItem()+".txt")).collect(Collectors.toList());
        time.setText(lines.get(0));
        desc.setText(lines.get(2));
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }

      editCourseButton.setDisable(false);

      //Display the Course Student List
    }
  }
  
  public void EditCourse() {
    editCourseButton.setDisable(true);
    saveCourseDetailsButton.setDisable(false);
    time.setEditable(true);
    desc.setEditable(true);
    courseList.setDisable(true);
    addCourseButton.setDisable(true);
    teachingCourse.setDisable(true);
    removeCourseButton.setDisable(true);

    time.setStyle("-fx-border-color: #eb7231");
    desc.setStyle("-fx-border-color: #eb7231");
  }

  public void SaveCourse() {
    editCourseButton.setDisable(false);
    saveCourseDetailsButton.setDisable(true);
    time.setEditable(false);
    desc.setEditable(false);
    courseList.setDisable(false);
    addCourseButton.setDisable(false);
    teachingCourse.setDisable(false);
    removeCourseButton.setDisable(false);

    time.setStyle("-fx-border-color: transparent");
    desc.setStyle("-fx-border-color: transparent");
        
    try (FileWriter myWriter = new FileWriter("data/Course Details/"+courseTitle.getText()+".txt")){
      myWriter.write(time.getText().replaceAll("\n",", "));
      myWriter.write("\n\n");
      myWriter.write(desc.getText().replaceAll("\n"," "));
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
  
  public void studentYear() {

  }

<<<<<<< HEAD
  public void searchstudent(){
    
  }
  
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    displaycourselist();
    displayteachingcourse();
    addCourseButton.setDisable(true);
    removeCourseButton.setDisable(true);
    editCourseButton.setDisable(true);
    saveCourseDetailsButton.setDisable(true);
=======
  public void EditCourse() {
    time.setEditable(true);
    desc.setEditable(true);

    time.setStyle("-fx-border-color: #eb7231");
    desc.setStyle("-fx-border-color: #eb7231");
  }

  public void SaveCourse() {
    time.setEditable(false);
    desc.setEditable(false);

    time.setStyle("-fx-border-color: transparent");
    desc.setStyle("-fx-border-color: transparent");
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    courseTitle.setText("CAT 201 Software Integrated Development Workshop kdfkslfsfhjshfjdkahahahhahahahahahahahahaha");
    time.setText("Tuesday 3pm - 4pm ahahhahahahahahahahahahadjfhjhjsfhjksffksdhfkshdfjkshfdjdkshfljsakhsah");
    desc.setText(
        "The course serves to dkksjfasjkfdsfkjslkdjfaskljfksjfahahhahahahahahahahahahadjfksdhfjksadhfljashfjkshd");
>>>>>>> dc2130f694f14deb71f6dc4fc4fd242793f04e2f
  }
}