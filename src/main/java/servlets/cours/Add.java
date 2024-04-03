// a servlet for adding a cours {{id {auto incremented},nom,description,date de debut, date de fin}}
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

@WebServlet(name = "addCours", value = "/addCours")
public class Add extends HttpServlet {

        @Override
        public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            try (Connection conn = new connection().getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO cours (nom, description, date_debut, date_fin) VALUES (?, ?, ?, ?)");
                stmt.setString(1, request.getParameter("nom"));
                stmt.setString(2, request.getParameter("description"));
                stmt.setDate(3, java.sql.Date.valueOf(request.getParameter("date_debut")));
                stmt.setDate(4, java.sql.Date.valueOf(request.getParameter("date_fin")));
                stmt.executeUpdate();
                response.sendRedirect("/listCours");
            } catch (SQLException e) {
                throw new ServletException("SQL error", e);
            }
        }
}