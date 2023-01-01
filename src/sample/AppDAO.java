package sample;

import com.mysql.cj.protocol.Resultset;
import sample.classes.Course;
import sample.classes.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// TODO: Turn AppDAO into an interface and implement it in StudentDAO, LecturerDAO, and CourseDAO
public class AppDAO {
    public enum UserType {
        STUDENT, LECTURER
    }

    public enum ChoiceBoxItems{
        SEMESTER, SCHOOL, CAMPUS, PROGRAMME, MAJOR, MINOR
    }

    private static final String DBUrl = String.format("jdbc:oracle:thin:%s/%s@%s", APIKeys.DBuser, APIKeys.DBPass, APIKeys.DBName);
    private static Connection conn = null;

    public static void DBConnect() throws SQLException {
        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            conn = DriverManager.getConnection(DBUrl);
            if (conn != null) {
                System.out.println("Connected Successfully");
            }
        } catch (SQLException exc) {
            System.out.println("Something terrible happened!");
        }
    }

    public static void DBDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    public static void registerUser(String ID, String Pass, UserType User) throws SQLException {
        Statement stmnt = null;
        try {
            DBConnect();
            stmnt = conn.createStatement();
            switch (User) {
                case STUDENT ->
                        stmnt.execute(String.format("INSERT INTO STUDENT (STUDENT_ID, STUDENT_PASSWORD) VALUES (%s,'%s')", ID, Pass));
                case LECTURER ->
                        stmnt.execute(String.format("INSERT INTO LECTURER (LECTURER_ID, LECTURER_PASSWORD) VALUES (%s,'%s')", ID, Pass));
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            if (stmnt != null) {
                stmnt.close();
            }
            DBDisconnect();
        }
    }

    public static boolean loginUser(String ID, String Pass, UserType User) throws SQLException {
        boolean status = false;
        Statement stmnt = null;
        ResultSet result = null;
        try {
            DBConnect();

            stmnt = conn.createStatement();
            result = switch (User) {
                case STUDENT ->
                        stmnt.executeQuery(String.format("SELECT STUDENT_ID, STUDENT_PASSWORD FROM STUDENT WHERE STUDENT_ID = %s", ID));
                case LECTURER ->
                        stmnt.executeQuery(String.format("SELECT LECTURER_ID, LECTURER_PASSWORD FROM STUDENT WHERE LECTURER_ID = %s", ID));
            };

            result.next();
            String correctPassword = result.getString(2);
            if (Pass.equals(correctPassword)) {
                status = true;
            }

        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {

            if (result != null) {
                result.close();
            }
            if (stmnt != null) {
                stmnt.close();
            }
            DBDisconnect();
        }
        return status;
    }

    public static Student getStudentDetails(int ID) throws SQLException{
        Statement stmnt = null;
        ResultSet result = null;
        Student stud = null;
        try {
            DBConnect();

            stmnt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            result = stmnt.executeQuery(String.format("SELECT * FROM STUDENT WHERE STUDENT_ID = %s", ID));
            result.first();
            stud = new Student(ID);
            stud.setName(result.getString("STUDENT_NAME"));
            stud.setCampus(result.getString("CAMPUS_NAME"));
            stud.setCGPA(result.getFloat("STUDENT_GPA"));
            stud.setMajor(result.getString("MAJOR_CODE"));
            stud.setMinor(result.getString("MINOR_CODE"));
            stud.setSchool(result.getString("SCHOOL_NAME"));
            stud.setNRIC(result.getLong("STUDENT_NRIC"));
            stud.setProgramme(result.getString("DEGREE_NAME"));
            stud.setSem(result.getString("SEMESTER_CODE"));
            stud.setYear(result.getInt("STUDENT_YEAR"));
            stud.setStatus(result.getString("STATUS_NAME"));

        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {

            if (result != null) {
                result.close();
            }
            if (stmnt != null) {
                stmnt.close();
            }
            DBDisconnect();
        }
        return stud;
    }

    private static String nullFormat(String text){
        if(text == null){
            return null;
        } else{
            return "'" + text + "'";
        }
    }

    private static Long nullFormat(long num){
        return num == 0 ? null : num;
    }

    public static void updateStudentDetails(Student stud) throws SQLException{
        Statement stmnt = null;
        try{
            DBConnect();
            stmnt = conn.createStatement();
            stmnt.execute(String.format(
                    "UPDATE STUDENT SET STUDENT_NAME = %s, STUDENT_NRIC = %s, STATUS_NAME = %s, SEMESTER_CODE = %s, STUDENT_GPA = %s, STUDENT_YEAR = %s, SCHOOL_NAME = %s, CAMPUS_NAME = %s, DEGREE_NAME = %s, MAJOR_CODE = %s, MINOR_CODE = %s WHERE STUDENT_ID = %s",
                    nullFormat(stud.getName()), nullFormat(stud.getNRIC()), nullFormat(stud.getStatus()), nullFormat(stud.getSem()), stud.getCGPA(), stud.getYear(),
                    nullFormat(stud.getSchool()), nullFormat(stud.getCampus()), nullFormat(stud.getProgramme()), nullFormat(stud.getMajor()), nullFormat(stud.getMinor()),
                    stud.getID()
                    ));
            System.out.println("Student record updated successfully!");
        } catch (SQLException exc) {
            System.out.println("Failed to update student");
            exc.printStackTrace();
        } finally {
            if(stmnt != null){
                stmnt.close();
            }
            DBDisconnect();
        }
    }

    public static ArrayList<String> getChoiceBoxItems(ChoiceBoxItems choice) throws SQLException{
        Statement stmnt = null;
        ResultSet result = null;
        ArrayList<String> degrees = new ArrayList<String>();
        try{
            DBConnect();
            stmnt = conn.createStatement();
            final String query = "SELECT * FROM " + switch (choice) {
                case SEMESTER -> "SEMESTER";
                case SCHOOL -> "SCHOOLS";
                case CAMPUS -> "CAMPUS";
                case PROGRAMME -> "DEGREE";
                case MAJOR -> "MAJOR";
                case MINOR -> "MINOR";
            };

            final String colName = switch (choice) {
                case SEMESTER -> "SEMESTER_CODE";
                case SCHOOL -> "SCHOOL_NAME";
                case CAMPUS -> "CAMPUS_NAME";
                case PROGRAMME -> "DEGREE_NAME";
                case MAJOR -> "MAJOR_CODE";
                case MINOR -> "MINOR_CODE";
            };

            result = stmnt.executeQuery(query);

            while(result.next()){
                degrees.add(result.getString(colName));
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            if (stmnt != null){
                stmnt.close();
            }
            if(result != null){
                result.close();
            }
            DBDisconnect();
        }
        return degrees;
    }

    public static ArrayList<Course> getCourseList() throws SQLException{
        Statement stmnt = null;
        ResultSet result = null;
        ArrayList<Course> courses= new ArrayList<Course>();
        try {
            DBConnect();
            stmnt = conn.createStatement();
            result = stmnt.executeQuery("SELECT * FROM COURSES");

            while(result.next()){
                courses.add(new Course(result.getString("COURSE_CODE"), result.getString("COURSE_TITLE"), result.getString("COURSE_DESC"), result.getString("COURSE_TIME")));
            }
        } catch (SQLException exc) {
            System.out.println("Something went wrong while getting the course list");
        } finally {
            if(stmnt != null){
                stmnt.close();
            }
            if(result != null){
                result.close();
            }
            DBDisconnect();
        }
        return courses;
    }

    public static ArrayList<String> getStudentCourseList (String ID) throws SQLException {
        Statement stmnt = null;
        ResultSet result = null;
        ArrayList<String> studentCourseList = new ArrayList<String>();
        try {
            DBConnect();
            stmnt = conn.createStatement();
            result = stmnt.executeQuery(String.format("SELECT COURSE_CODE FROM STUDENT_COURSES WHERE STUDENT_ID = %s", ID));

            while(result.next()){
                studentCourseList.add(result.getString("COURSE_CODE"));
            }
        } catch (SQLException exc) {
            System.out.println("An error occurred while fetching the student's course list");
        } finally {
            if (stmnt != null){
                stmnt.close();
            }
            if (result != null){
                result.close();
            }
            DBDisconnect();
        }
        return studentCourseList;
    }

    public static void registerStudentCourse(String studID, String courseCode) throws SQLException {
        Statement stmnt = null;
        try {
            DBConnect();
            stmnt = conn.createStatement();
            stmnt.execute(String.format("INSERT INTO STUDENT_COURSES VALUES ('%s', '%s')", studID, courseCode));

        } catch (SQLException exc) {
            System.out.println("Couldn't register student to course");
        } finally {
            if (stmnt != null) {
                stmnt.close();
            }
            DBDisconnect();
        }
    }

    public static void removeStudentCourse(String studID, String courseCode) throws SQLException{
        Statement stmnt = null;
        try {
            DBConnect();
            stmnt = conn.createStatement();
            stmnt.execute(String.format("DELETE FROM STUDENT_COURSES WHERE STUDENT_ID = '%s' AND COURSE_CODE = '%s'", studID, courseCode));

        } catch (SQLException exc) {
            System.out.println("Couldn't unregister student to course");
            exc.printStackTrace();
        } finally {
            if (stmnt != null) {
                stmnt.close();
            }
            DBDisconnect();
        }
    }

}
