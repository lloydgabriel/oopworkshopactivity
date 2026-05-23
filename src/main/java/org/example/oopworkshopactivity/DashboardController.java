package org.example.oopworkshopactivity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private TableView<Song> songTable;

    // 1. Define the ObservableList here
    private ObservableList<Song> songList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        System.out.println("DEBUG: Controller Class: " + this.getClass().getName());

        // 2. Bind the list to the table in initialization
        if (songTable != null) {
            songTable.setItems(songList);
            loadSongsFromSupabase();
        } else {
            System.err.println("songTable is null! Check your FXML fx:id.");
        }
    }

    public void loadSongsFromSupabase() {
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, title, artist FROM songs")) {

            // 3. Add to the ObservableList instead of table.getItems() directly
            songList.clear();
            while (rs.next()) {
                songList.add(new Song(rs.getInt("id"), rs.getString("title"), rs.getString("artist")));
            }

        } catch (SQLException e) {
            System.err.println("Database fetch failed.");
            e.printStackTrace();
        }
    }

    // 4. This method allows AddSongController to update the UI
    public void addSongToList(Song newSong) {
        songList.add(newSong);
    }

    @FXML
    public void goToEditSongScene(ActionEvent event) throws IOException {
        System.out.println("Edit button clicked!");
    }

    @FXML
    public void handleDeleteSong(ActionEvent event) {
        Song selectedSong = songTable.getSelectionModel().getSelectedItem();
        if (selectedSong != null) {
            songList.remove(selectedSong);
        }
    }

    @FXML
    public void goToAddSongScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addsong.fxml"));
            Parent root = loader.load();

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