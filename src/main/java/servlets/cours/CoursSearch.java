package servlets.cours;

import connection.connection;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.json.Json;
import utils.Cours;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CoursSearch", value = "/CoursSearch")
public class CoursSearch extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String searchTerm = request.getParameter("searchTerm");

        try (Connection conn = new connection().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM cours WHERE nom LIKE ?");
            stmt.setString(1, "%" + searchTerm + "%");

            ResultSet rs = stmt.executeQuery();
            List<Cours> coursList = new ArrayList<>();
            while (rs.next()) {
                Cours cours = new Cours();
                cours.setId(rs.getInt("id"));
                cours.setNom(rs.getString("nom"));
                cours.setDescription(rs.getString("description"));
                cours.setDateDebut(rs.getDate("date_debut"));
                cours.setDateFin(rs.getDate("date_fin"));
                coursList.add(cours);
            }

            // Create a JSON array and add the cours objects
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (Cours cours : coursList) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("id", cours.getId());
                objectBuilder.add("nom", cours.getNom());
                objectBuilder.add("description", cours.getDescription());
                objectBuilder.add("dateDebut", cours.getDateDebut().toString());
                objectBuilder.add("dateFin", cours.getDateFin().toString());
                arrayBuilder.add(objectBuilder);
            }

            // Write the JSON array to the response
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(arrayBuilder.build());
            out.flush();

        } catch (SQLException e) {
            throw new ServletException("SQL error", e);
        }
    }
}
