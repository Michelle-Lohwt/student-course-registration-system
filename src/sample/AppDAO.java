package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AppDAO {
    private static final String DBUrl = String.format("jdbc:oracle:thin:%s/%s@%s", APIKeys.DBuser, APIKeys.DBPass, APIKeys.DBName);
    private static Connection conn = null;

    public static void DBConnect() throws SQLException{
        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            conn = DriverManager.getConnection(DBUrl);
            if(conn != null){
                System.out.println("Connected Successfully");
            }
        } catch (SQLException exc){
            System.out.println("Something terrible happened!");
        }
    }

    public static void DBDisconnect() throws SQLException{
        try{
            if(conn != null && !conn.isClosed()){
                conn.close();
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    public static void createStudent(String StudID, String StudPass) throws SQLException{
        Statement stmnt = null;
        try{
            DBConnect();
            stmnt = conn.createStatement();
            stmnt.execute(String.format("INSERT INTO STUDENT (STUDENT_ID, STUDENT_PASSWORD) VALUES (%s,'%s')", StudID, StudPass));
        }   catch (SQLException exc){
            exc.printStackTrace();
        }   finally{
            if (stmnt != null){
                stmnt.close();
            }
            DBDisconnect();
        }
    }

    public static void createLecturer(String LectID, String LectPass) throws SQLException{
        Statement stmnt = null;
        try{
            DBConnect();
            stmnt = conn.createStatement();
            stmnt.execute(String.format("INSERT INTO LECTURER (LECTURER_ID, LECTURER_PASSWORD) VALUES (%s,'%s')", LectID, LectPass));
        }   catch (SQLException exc){
            exc.printStackTrace();
        }   finally{
            if (stmnt != null){
                stmnt.close();
            }
            DBDisconnect();
        }
    }

}
