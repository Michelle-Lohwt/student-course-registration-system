package sample;
import java.sql.*;
import java.util.List;

import sample.classes.Course;

public class YourDatabaseHandler {

    public class App {
        public static void main(String[] args)  
        {
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb","root","010830010195");
                Statement stmt= conn.createStatement();
                ResultSet rs=stmt.executeQuery("select Students_FN from students where Students_Year_Enrolled=2019");
                while(rs.next())
                {
                    System.out.println(rs.getString(1));
                }
                conn.close();
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }

    public List<Course> getAllCourses() {
        return null;
    }

    public List<Course> getFilteredCourses(String keyword) {
        return null;
    }
}
