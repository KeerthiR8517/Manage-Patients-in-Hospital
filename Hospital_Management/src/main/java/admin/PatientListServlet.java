package admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/patientList")
public class PatientListServlet extends HttpServlet {
    private static final String querySelect = "SELECT PATIENT_ID,PATIENT_NAME,AGE,PHONE_NO,ADMISSION_DATE,DISCHARGE_DATE,CURRENT_STATUS,COVID_STATUS,GENDER FROM PatientTable";
    private static final String queryDelete = "DELETE FROM PatientTable WHERE PATIENT_ID = ?";
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "1234");
             PreparedStatement psSelect = con.prepareStatement(querySelect);
             ResultSet rs = psSelect.executeQuery();) {
        	
            pw.println("<table align='center'>");
            
            pw.println("<th><br><br><h1>All Patients</h1></th>");
            pw.println("</table>");
            pw.println("<table border='1' align='center'>");
            pw.println("<tr>");
            pw.println("<th>Patient ID</th>");
            pw.println("<th>Patient Name</th>");
            pw.println("<th>Age</th>");
            pw.println("<th>Phone Number</th>");
            pw.println("<th>Admission Date</th>");
            pw.println("<th>Discharge Date</th>");
            pw.println("<th>Current Status</th>");
            pw.println("<th>Covid Status</th>");
            pw.println("<th>Gender</th>");
            pw.println("<th>Edit</th>");
            pw.println("<th>Delete</th>");
            pw.println("</tr>");

            while (rs.next()) {
                pw.println("<tr>");
                pw.println("<td>" + rs.getString("PATIENT_ID") + "</td>");
                pw.println("<td>" + rs.getString("PATIENT_NAME") + "</td>");
                pw.println("<td>" + rs.getInt("AGE") + "</td>");
                pw.println("<td>" + rs.getString("PHONE_NO") + "</td>");
                pw.println("<td>" + rs.getString("ADMISSION_DATE") + "</td>");
                pw.println("<td>" + rs.getString("DISCHARGE_DATE") + "</td>");
                pw.println("<td>" + rs.getString("CURRENT_STATUS") + "</td>");
                pw.println("<td>" + (rs.getBoolean("COVID_STATUS") ? "Positive" : "Negative") + "</td>");
                pw.println("<td>" + rs.getString("GENDER") + "</td>");
                pw.println("<style>");
                pw.println("a { color: blue;  }"); // Change 'blue' to your preferred color
                pw.println("a:hover { color: red; }"); // Change 'red' to the hover color you prefer
                pw.println("</style>");
                pw.println("<td><a href='editPatient?id=" + rs.getString("PATIENT_ID") + "'>Edit</a></td>");
                pw.println("<td><a href='deletePatient?id=" + rs.getString("PATIENT_ID") + "'>Delete</a></td>");
                pw.println("</tr>");
            }
          

            pw.println("</table>");
        
        pw.println("<header><a href='index.html'>Home</a> <a href='homestart.html'>back</a></header>");
    	pw.println("<style>header { position: fixed;  top: 0;   left: 0; width: 100%;padding: 10px 125px 10px; background:black;   align-items: center; }");
    	pw.println(" a{color:#469;</style>");
        }
        catch (SQLException se) 
        {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h2>");
        }   
        catch (Exception e)
        {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h2>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
