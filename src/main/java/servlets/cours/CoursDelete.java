package servlets.cours;

import connection.connection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "CoursDelete", value = "/CoursDelete")
public class CoursDelete extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = new connection().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM cours WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
//            redirect to the previous page
            response.sendRedirect(request.getHeader("Referer"));
        } catch (SQLException e) {
            throw new ServletException("SQL error", e);
        }
    }
}