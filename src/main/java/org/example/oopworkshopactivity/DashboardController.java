package org.example.oopworkshopactivity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DashboardController {

    @FXML
    private TableView<Song> songTable; // Matches your UI table view layout

    @FXML
    public void initialize() {
        // Double-check if songTable is null before loading
        if (songTable != null) {
            loadSongsFromSupabase();
        } else {
            System.err.println("songTable is null! Check your FXML fx:id.");
        }
    }

    public void loadSongsFromSupabase() {
        // Try-with-resources auto-closes the connection so the pooler doesn't run out of slots
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT title, artist FROM songs")) { // Adjust your query table name if necessary

            // Clear existing layout list elements before reloading
            songTable.getItems().clear();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String artist = rs.getString("artist");

                songTable.getItems().add(new Song(id, title, artist));
            }

        } catch (SQLException e) {
            System.err.println("Database fetch timed out or failed. Displaying empty table.");
            e.printStackTrace();
        }
    }

    @FXML
    public void goToEditSongScene(ActionEvent event) throws IOException {
        // Add your logic to switch scenes here, or leave it empty for now
        // just to stop the crash!
        System.out.println("Edit button clicked!");
    }

    @FXML
    public void handleDeleteSong(ActionEvent event) {
        // Add your logic here, e.g.:
        System.out.println("Delete button clicked!");
    }

    @FXML
    public void goToAddSongScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addsong.fxml"));
            Parent root = loader.load();

            // Get the controller and pass the list
            AddSongController controller = loader.getController();
            controller.setDashboardController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}