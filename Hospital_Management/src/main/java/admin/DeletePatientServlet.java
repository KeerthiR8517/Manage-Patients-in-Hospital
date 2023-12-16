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


@WebServlet("/deletePatient")
public class DeletePatientServlet extends HttpServlet {
    private static final String querySelect = "SELECT DISCHARGE_DATE FROM PatientTable WHERE PATIENT_ID = ?";
    private static final String queryDelete = "DELETE FROM PatientTable WHERE PATIENT_ID = ?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String patientId = req.getParameter("id");

        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        
        } 
        catch (ClassNotFoundException cnf) 
        {
            cnf.printStackTrace();
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "1234");
             PreparedStatement psSelect = con.prepareStatement(querySelect);
             PreparedStatement psDelete = con.prepareStatement(queryDelete);) 
        {
             psSelect.setString(1, patientId);
             ResultSet rs = psSelect.executeQuery();
             if (rs.next() && rs.getString("DISCHARGE_DATE") != null)
             {
               
                psDelete.setString(1, patientId);
                int count = psDelete.executeUpdate();
                if (count == 1) 
                {
                    res.sendRedirect("patientList");
                }   else
                {
                    PrintWriter pw = res.getWriter();
                    pw.println("<h2>Record not Deleted Successfully</h2>");
                }
             } 
                  else 
             {
                          PrintWriter pw = res.getWriter();
                pw.println("<h2>Cannot delete the patient. Patient is not discharged.</h2>");
             }
        } catch (SQLException se) {
            se.printStackTrace();
            PrintWriter pw = res.getWriter();
            pw.println("<h1>" + se.getMessage() + "</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            PrintWriter pw = res.getWriter();
            pw.println("<h1>" + e.getMessage() + "</h2>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
