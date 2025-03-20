package com.model;
import java.util.*;

public class Playlist {
    public String id;
    private String title;
    private String author;
    private String description;
    private ArrayList<Song> songs;

    public Playlist(String id, String title, String author, String description, ArrayList<Song> songs){
        ID temp = id == null ? new ID() : new ID(id);
        this.id = temp.uuid;
        this.title = title;
        this.author = author;
        this.description = description;
        this.songs = songs;
    }
    public void editTitle(String title){
        this.title = title;
    }
    public void addSong(Song song){
        songs.add(song);
    }
    public void removeSong(Song song){
        for(int i = 0; i < songs.size(); i++){
            if(songs.get(i) == song){
                songs.remove(i);
                --i;
            }
        }
    }
    public void editDescription(String description){
        this.description = description;
    }
    public void sortByTitle(){
        songs.sort(new Comparator<Song>(){
            public int compare(Song o1, Song o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
    }
    public void sortByArtist(){
        songs.sort(new Comparator<Song>(){
            public int compare(Song o1, Song o2) {
                return o1.getArtist().compareTo(o2.getArtist());
            }
        });
    }
    public void sortByGenre(){
        songs.sort(new Comparator<Song>(){
            public int compare(Song o1, Song o2) {
                return o1.getGenre().compareTo(o2.getGenre());
            }
        });
    }
    public void sortByKey(){
        Comparator<Song> sortByKey = Comparator.comparingInt(s -> s.getKey().ordinal());
        Collections.sort(songs, sortByKey);
    }
    public void sortByDifficulty(){
        Comparator<Song> sortByKey = Comparator.comparingInt(s -> s.getDifficultyLevel().ordinal());
        Collections.sort(songs, sortByKey);
    }
    public String getId() {
        return this.id;
     }
     public String getTitle() {
        return title;
     }
     public String getAuthor() {
        return author;
     }
     public String getDescription() {
        return description;
     }
     public ArrayList<Song> getSongs() {
        return songs;
     }
}
