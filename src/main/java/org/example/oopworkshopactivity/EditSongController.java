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

    public void setSongToEdit(Song song) {
        this.currentSongId = song.getId();
        titleField.setText(song.getTitle());
        artistField.setText(song.getArtist());
    }

    @FXML
    public void handleUpdateSong(ActionEvent event) throws IOException {
        String query = "UPDATE songs SET title = ?, artist = ? WHERE id = ?";
        try (Connection conn = org.example.oopworkshopactivity.DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, titleField.getText());
            pstmt.setString(2, artistField.getText());
            pstmt.setInt(3, currentSongId);
            pstmt.executeUpdate();

            handleCancel(event);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCancel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/oopworkshopactivity/dashboard.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
