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

@WebServlet("/editPatient")
public class EditPatientServlet extends HttpServlet {
    private static final String querySelect = "SELECT * FROM PatientTable WHERE PATIENT_ID = ?";
    private static final String queryUpdate = "UPDATE PatientTable SET PATIENT_NAME=?, AGE=?, PHONE_NO=?, ADMISSION_DATE=?, DISCHARGE_DATE=?, CURRENT_STATUS=?, COVID_STATUS=?, GENDER=? WHERE PATIENT_ID=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");

        String patientId = req.getParameter("id");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "1234");
             PreparedStatement psSelect = con.prepareStatement(querySelect);) {

            psSelect.setString(1, patientId);
            ResultSet rs = psSelect.executeQuery();

            pw.println("<br><br><h1>Edit Patient details</h1>");
            pw.println("<form action='updatePatient' method='post'>");

            while (rs.next())
            {
            	pw.println("<table>");

            	pw.println("<tr>");
                pw.println("<td><input type='hidden' name='patientId' value='" + rs.getString("PATIENT_ID") + "'></td>");
            	pw.println("</tr>");

            	pw.println("<tr>");
            	pw.println("<td>Patient Name:</td>");
            	pw.println("<td><input type='text' name='patientName' value='" + rs.getString("PATIENT_NAME") + "' required></td>");
            	pw.println("</tr>");
            	pw.println("<tr><td>Age:</td>");
            	pw.println("<td><input type='number' name='age' value='" + rs.getInt("AGE") + "' required></td></tr>");
            	
            	pw.println("<tr><td>Phone Number:</td>");
            	pw.println("<td><input type='text' name='phoneno' value='" + rs.getString("PHONE_NO") + "' required></td></tr>");
 
            	pw.println("<tr><td>Admission Date:</td>");
            	pw.println("<td><input type='date' name='aadmission_date' value='" + rs.getString("ADMISSION_DATE") + "' required></td></tr>");
            
            	pw.println("<tr><td>Discharge Date:</td>");
            	pw.println("<td><input type='date' name='discharge_date' value='" + rs.getString("DISCHARGE_DATE") + "' required></td></tr>");

            	
            	pw.println("<tr><td>Current Status:</td>");
            	pw.println("<td><input type='text' name='currentstatus' value='" + rs.getString("CURRENT_STATUS") + "' required></td></tr>");
            	
            	pw.println("<tr><td>Covid-19 Status:</td>");
            	pw.println("<td><input type='checkbox' name='covid-19' " + (rs.getBoolean("COVID_STATUS") ? "checked" : "") + "></td></tr>");
          
            	pw.println("<tr><td>Gender:</td>");
            	pw.println("<td><select name='gender'>");
            	pw.println("<option value='male' " + (rs.getString("GENDER").equalsIgnoreCase("male") ? "selected" : "") + ">Male</option>");
            	pw.println("<option value='female' " + (rs.getString("GENDER").equalsIgnoreCase("female") ? "selected" : "") + ">Female</option>");
            	pw.println("<option value='others' " + (rs.getString("GENDER").equalsIgnoreCase("others") ? "selected" : "") + ">Others</option>");
            	pw.println("</select></td></tr>");
            
            	pw.println("<tr><td colspan='2'><input type='submit' value='Update'></td></tr>");
    	pw.println("</table>");
    	  pw.println("<header><a href='index.html'>Home</a> <a href='homestart.html'>back</a></header>");
      	pw.println("<style>header { position: fixed;  top: 0;   left: 0; width: 100%;padding: 10px 125px 10px; background:black; justify-content: space-between;  align-items: center; }");
      	pw.println(" a{color:#469;</style>");

            	
            }

            pw.println("</form>");
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h2>");
        }
    }
}

