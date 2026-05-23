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

public class AddSongController {

    private DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }
    @FXML
    private TextField titleField;
    @FXML
    private TextField artistField;

    @FXML
    public void initialize() {
        // Keep this strictly empty or limited to local UI setups!
        // Do NOT make blocking database connections here, or it will freeze the view change.
    }
    @FXML
    public void handleBack(ActionEvent event) {
        // Logic to close the current window
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    public void saveSong(ActionEvent event) {
        String title = titleField.getText();
        String artist = artistField.getText();

        if (title.isEmpty() || artist.isEmpty()) {
            System.out.println("Validation Error: Please fill in all text fields.");
            return;
        }

        String insertSQL = "INSERT INTO songs (title, artist) VALUES (?, ?)";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, title);
            pstmt.setString(2, artist);
            pstmt.executeUpdate();

            System.out.println("Success: Song added to Supabase database container.");

            // Return back to the updated dashboard playlist screen right after saving
            goToDashboard(event);

        } catch (SQLException e) {
            System.err.println("Failed to insert record across the transaction pooler pipeline.");
            e.printStackTrace();
        }
    }
    @FXML
    public void handleSaveSong(ActionEvent event) {
        System.out.println("Save button clicked!");

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    public void cancelAction(ActionEvent event) {
        goToDashboard(event);
    }

    private void goToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            Parent dashboardRoot = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(dashboardRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}