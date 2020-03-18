package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

public class DBUtility {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    public static void connect() throws SQLException                 // function for database connection
    {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection("jdbc:sqlserver://s16988308.onlinehome-server.com:1433;databaseName=CUNY_DB;integratedSecurity=false;", "cst3613", "password1");
            statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
    public static void disconnect() throws SQLException                   // function for database disconnection
    {
        if (preparedStatement != null) preparedStatement.close();
        if (statement != null) statement.close();
        if (connection != null) connection.close();
    }
    public static ResultSet getSSN(String SSN) throws Exception {
        String str = null;
        ResultSet rs = statement.executeQuery("select ssn,firstName,lastName from  Students where Students.ssn = " + SSN);
     return rs;
    }

    public static ObservableList<EnrollCourse> getAllData(String ssn) throws Exception {
        String queryString = "select ssn,title,grade from Course c,Enrollment e where e.courseId=c.courseID AND e.ssn="+ssn;
        ObservableList<EnrollCourse> data = FXCollections.observableArrayList();
        ResultSet rs = statement.executeQuery(queryString);
        while (rs.next()) {
            data.add(new EnrollCourse(rs.getString(1), rs.getString(2),rs.getString(3)));
        }
        return data;
    }
    public static String removeEnroll(String name,String ssn) throws Exception
    {
        String queryString = "Delete from Enrollment where Enrollment.courseId = (select courseID from Course c where c.title = '"+name+"') AND Enrollment.ssn = " + ssn + "";
        statement = connection.createStatement();
        statement.executeUpdate(queryString);
        return name;
    }

    public static ObservableList<String> getcourses(String ssn) throws Exception {
        String queryString = "Select DISTINCT c.title From Course c , Enrollment e WHERE  e.courseId != c.courseID AND e.ssn ="+ssn;
        ObservableList<String> course = FXCollections.observableArrayList();
        ResultSet rs = statement.executeQuery(queryString);
        while (rs.next()) {
            course.add(rs.getString(1));
        }
        return course;
    }

    public static int addcourse(String name,String ssn) throws Exception
    {int checkid=0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String queryString = "select Course.courseID from Course where Course.title = '"+name+"'";
        String grade="A";
        ResultSet rs = statement.executeQuery(queryString);
        if(rs.next())
        {
            String query = " Insert into Enrollment(ssn,courseId,dateRegistered,grade) values("+ ssn + ", '"+ rs.getString(1) + "','"+dateFormat.format(cal.getTime())+"','"+grade+"')";
            statement = connection.createStatement();
             checkid=  statement.executeUpdate(query);
        }

        return checkid;
    }
}
