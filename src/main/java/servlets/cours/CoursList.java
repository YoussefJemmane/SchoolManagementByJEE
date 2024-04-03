package servlets.cours;

import connection.connection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.Cours;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "listCours", value = "/listCours")
public class CoursList extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try (Connection conn = new connection().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM cours");

            ResultSet rs = stmt.executeQuery();
            List<Cours> coursList = new ArrayList<>();
            while(rs.next()){
                Cours cours = new Cours();
                cours.setId(rs.getInt("id"));
                cours.setNom(rs.getString("nom"));
                cours.setDescription(rs.getString("description"));
                cours.setDateDebut(rs.getDate("date_debut"));
                cours.setDateFin(rs.getDate("date_fin"));
                coursList.add(cours);
            }

            request.setAttribute("cours", coursList);
            request.getRequestDispatcher("/cours/index.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("SQL error", e);
        }
    }
}
