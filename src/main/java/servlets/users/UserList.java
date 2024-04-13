package servlets.users;

import connection.connection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.User;

@WebServlet(name = "UserListServlet", value = "/users")
public class UserList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = new ArrayList<>();

        // Fetch list of users
        try (Connection conn = new connection().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id,first_name, last_name, email, role FROM user");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));

                // Add user to the list
                users.add(user);
            }
        } catch (SQLException e) {
            throw new ServletException("Error fetching users", e);
        }

        // Set attributes for JSP
        request.setAttribute("users", users);
    }
}