package servlets;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import connection.connection;
import java.sql.*;
import connection.connection;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "login", value = "/login")
public class Login extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = "";
        String lastName = "";
        try {
            connection conn = new connection();
            Connection dbConnection = conn.getConnection();
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT first_name, last_name FROM User WHERE email = ? AND password = ?");
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                firstName = rs.getString("first_name");
                lastName = rs.getString("last_name");
                HttpSession session = request.getSession();
                session.setAttribute("name", firstName + " " + lastName);
                response.sendRedirect("menu/index.jsp");
                System.out.println("Login Successful");
            } else {
                response.sendRedirect("auth/login.jsp?error=1");
                System.out.println("Login Failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
