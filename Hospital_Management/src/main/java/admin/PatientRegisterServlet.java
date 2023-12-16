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

@WebServlet("/registerPatient")
public class PatientRegisterServlet extends HttpServlet {
    private static final String query = "INSERT INTO PatientTable(PATIENT_ID,PATIENT_NAME,AGE,PHONE_NO,ADMISSION_DATE,DISCHARGE_DATE,CURRENT_STATUS,COVID_STATUS,GENDER) VALUES(?,?,?,?,?,?,?,?,?)";

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

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ hospital", "root", "1234");
             PreparedStatement ps = con.prepareStatement(query);) {

            ps.setString(1, patientId);
            ps.setString(2, patientName);
            ps.setInt(3, age);
            ps.setString(4, phoneNo);
            ps.setString(5, admissionDate);
            ps.setString(6, dischargeDate);
            ps.setString(7, currentStatus);
            ps.setBoolean(8, covidStatus);
            ps.setString(9, gender);

            int count = ps.executeUpdate();
            if (count == 1) {
                req.getRequestDispatcher("homestart.html").include(req, res);
                pw.println("<div class='container'>");
                req.getRequestDispatcher("patientList").include(req, res);
            } else {
                req.getRequestDispatcher("homestart.html").include(req, res);
                pw.println("<h2>Record not Registered Successfully</h2>");
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

