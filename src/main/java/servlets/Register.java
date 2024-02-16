package servlets;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import connection.connection;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "register", value = "/register")
public class Register extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Input validation (add more as needed)
        if (firstName == null || firstName.isEmpty()) {
            response.sendRedirect("auth/register.jsp?error=firstname");
        }
        if (lastName == null || lastName.isEmpty()) {
            response.sendRedirect("auth/register.jsp?error=lastname");
        }
        if (email == null || email.isEmpty()) {
            response.sendRedirect("auth/register.jsp?error=email");
        }
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")) {
            response.sendRedirect("auth/register.jsp?error=emailValidation");
        }
        if (password == null || password.isEmpty()) {
            response.sendRedirect("auth/register.jsp?error=password");
        }


        try (Connection conn = new connection().getConnection()) {
            PreparedStatement stmtCheck = conn.prepareStatement("SELECT * FROM User WHERE email = ?");
            stmtCheck.setString(1, email);
            ResultSet rs = stmtCheck.executeQuery();
            if (rs.next()) {
                response.sendRedirect("auth/register.jsp?error=emailExists");
            } else {
                PreparedStatement stmtInsert = conn.prepareStatement("INSERT INTO User (first_name, last_name, email, password,role) VALUES (?, ?, ?, ?,?)");
                stmtInsert.setString(1, firstName);
                stmtInsert.setString(2, lastName);
                stmtInsert.setString(3, email);
                stmtInsert.setString(4, password);
                stmtInsert.setString(5, "student");
                int rowsInserted = stmtInsert.executeUpdate();

                if (rowsInserted == 1) {
                    HttpSession session = request.getSession();
                    session.setAttribute("name", firstName + lastName);
                    response.sendRedirect("menu/index.jsp");
                    System.out.println("Registration Successful");
                } else {
                    throw new ServletException("Registration failed. Please try again later.");
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database error occurred. Please try again later.", e);
        }

    }
}
