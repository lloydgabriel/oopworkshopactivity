package org.example.oopworkshopactivity;

public class Song {

    private int id;
    private String title;
    private String artist;

    public Song(int id, String title, String artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    public Song(String title, String artist) {
        this.id = 0;
        this.title = title;
        this.artist = artist;
    }

    // --- GETTERS ---

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    // --- SETTERS (Optional, but good practice) ---

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}