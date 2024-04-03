package servlets;

import connection.connection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.HashPassword;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@WebServlet(name = "login", value = "/login")
public class Login extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = "";
        String lastName = "";

        if (email == null || email.isEmpty() || !email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")) {
            System.out.println("Email Validation Failed");
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp?error=emailValidation");
            return;
        }


        if (password == null || password.isEmpty() || !password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")) {
            System.out.println("Password Validation Failed");
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp?error=passwordValidation");
            return;
        }

        String hashedPassword = null;
        try {
            hashedPassword =  HashPassword.hashPassword(password+email);
        } catch (NoSuchAlgorithmException e) {
            throw new ServletException("Password hashing failed.", e);
        }
        try {
            connection conn = new connection();
            Connection dbConnection = conn.getConnection();
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT first_name, last_name, role FROM User WHERE email = ? AND password = ?");
            stmt.setString(1, email);
            stmt.setString(2, hashedPassword);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                firstName = rs.getString("first_name");
                lastName = rs.getString("last_name");
                HttpSession session = request.getSession();
                session.setAttribute("name", firstName + " " + lastName);
                session.setAttribute("role", rs.getString("role"));
                response.sendRedirect(request.getContextPath() + "/menu/index.jsp");
                System.out.println("Login Successful");
            } else {
                response.sendRedirect(request.getContextPath() + "/auth/login.jsp?error=loginFailed");
                System.out.println("Login Failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

