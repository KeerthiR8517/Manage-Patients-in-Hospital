package admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updatePatient")
public class UpdatePatientServlet extends HttpServlet {
    private static final String queryUpdate = "UPDATE PatientTable SET PATIENT_NAME=?, AGE=?, PHONE_NO=?, ADMISSION_DATE=?, DISCHARGE_DATE=?, CURRENT_STATUS=?, COVID_STATUS=?, GENDER=? WHERE PATIENT_ID=?";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");

        String patientId = req.getParameter("patientId");
        String patientName = req.getParameter("patientName");
        int age = Integer.parseInt(req.getParameter("age"));
        String phoneNo = req.getParameter("phoneno");
        String admissionDate = req.getParameter("aadmission_date");
        String dischargeDate = req.getParameter("discharge_date");
        String currentStatus = req.getParameter("currentstatus");
        boolean covidStatus = req.getParameter("covid-19") != null;
        String gender = req.getParameter("gender");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "1234");
             PreparedStatement psUpdate = con.prepareStatement(queryUpdate);) {

            psUpdate.setString(1, patientName);
            psUpdate.setInt(2, age);
            psUpdate.setString(3, phoneNo);
            psUpdate.setString(4, admissionDate);
            psUpdate.setString(5, dischargeDate);
            psUpdate.setString(6, currentStatus);
            psUpdate.setBoolean(7, covidStatus);
            psUpdate.setString(8, gender);
            psUpdate.setString(9, patientId);

            int count = psUpdate.executeUpdate();
            if (count == 1) {
                res.sendRedirect("patientList");
            } else {
                pw.println("<h2>Record not Updated Successfully</h2>");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h2>");
        }
    }
}

