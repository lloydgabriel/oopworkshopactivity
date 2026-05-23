package org.example.oopworkshopactivity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditSongController {

    @FXML private TextField titleField;
    @FXML private TextField artistField;
    private int currentSongId;

    private DashboardController dashboardController;

    public void setDashboardController(DashboardController controller) {
        this.dashboardController = controller;
    }

    public void setSongToEdit(Song song) {
        this.currentSongId = song.getId();
        titleField.setText(song.getTitle());
        artistField.setText(song.getArtist());
    }

    @FXML
    public void handleUpdateSong(ActionEvent event) throws IOException {
        String query = "UPDATE songs SET title = ?, artist = ? WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, titleField.getText());
            pstmt.setString(2, artistField.getText());
            pstmt.setInt(3, currentSongId); // This ensures only this specific song updates
            pstmt.executeUpdate();

            if (dashboardController != null) {
                dashboardController.loadSongsFromSupabase();
            }

            handleCancel(event);

        } catch (SQLException e) {
            e.printStackTrace();
            handleCancel(event);
        }
    }

    @FXML
    public void handleCancel(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
