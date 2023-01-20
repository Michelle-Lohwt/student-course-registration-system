package sample;

import sample.classes.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CourseDAO extends DAO {

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
        } finally {
            if (stmnt != null) {
                stmnt.close();
            }
            DBDisconnect();
        }
    }

    public static void updateCourse(String time, String desc, String courseCode) throws SQLException {
        Statement stmnt = null;
        try{
            DBConnect();
            stmnt = conn.createStatement();
            stmnt.execute(String.format("UPDATE COURSES SET COURSE_TIME = %s, COURSE_DESC = %s WHERE COURSE_CODE = %s", nullFormat(time), nullFormat(desc), nullFormat(courseCode)));
            System.out.println("Course updated successfully!");
        } catch (SQLException exc) {
            System.out.println("Failed to update course");
        } finally {
            if(stmnt != null){
                stmnt.close();
            }
            DBDisconnect();
        }
    }

    public static ArrayList<String> getCourseStudent(String courseCode) throws SQLException {
        Statement stmnt = null;
        ResultSet result = null;
        ArrayList<String> studentIDs = new ArrayList<String>();
        try {
            DBConnect();
            stmnt = conn.createStatement();
            result = stmnt.executeQuery(String.format("SELECT STUDENT_ID FROM STUDENT_COURSES WHERE COURSE_CODE = %s", nullFormat(courseCode)));

            while(result.next()){
                studentIDs.add(result.getString("STUDENT_ID"));
            }
        } catch (SQLException exc) {
            System.out.println("An error occurred while fetching the course's student list");
        } finally {
            if (stmnt != null){
                stmnt.close();
            }
            if (result != null){
                result.close();
            }
            DBDisconnect();
        }
        return studentIDs;
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
                case POSITION -> "POSITIONS";
            };

            final String colName = switch (choice) {
                case SEMESTER -> "SEMESTER_CODE";
                case SCHOOL -> "SCHOOL_NAME";
                case CAMPUS -> "CAMPUS_NAME";
                case PROGRAMME -> "DEGREE_NAME";
                case MAJOR -> "MAJOR_CODE";
                case MINOR -> "MINOR_CODE";
                case POSITION -> "POSITION_TITLE";
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
}
