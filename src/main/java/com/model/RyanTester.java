package com.model;


import javax.sound.midi.Sequence;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.jfugue.player.Player;

public class RyanTester extends DataConstants {
    public static void main(String[] args) {
        /*
         * DataLoader tests
         */
        //Score dummyscore = DataLoader.getScoreFromID("3a6c83d2-2235-4fff-84dc-7ad6ec2dabf8");
        //ArrayList<User> dummyusers = DataLoader.getUsers();
        //ArrayList<Teacher> dummyusers = DataLoader.getTeachers();
        //ArrayList<Song> dummysongs = DataLoader.getAllSongs();
        //ArrayList<Playlist> dummPlaylists = DataLoader.getAllPlaylists();


        /*
         * Adding a new song
         */
        // Player pALT = new Player();
        // Sequence rawMidi = DataLoader.loadSequence("IHeardItThroughTheGrapevine.mid");
        // Score jjScore = Score.midiToScore(rawMidi, 0, Instrument.ELECTRIC_BASS_FINGER);
        
        // Song jjSong = new Song(null, "I Heard It Through The Grapevine", "James Jamerson", "Soul", Key.GbMAJOR_EbMINOR, DifficultyLevel.INTERMEDIATE, Instrument.ELECTRIC_BASS_FINGER, jjScore);
        // //p.play(jj2Song.getScore().getSequence(0, jj2Song.getScore().size(), null, 1));

        // SongList songList = SongList.getInstance();
        // songList.add(jjSong);
        // DataWriter.saveNewScore(jjScore, SCORE_FILE_NAME);
        // DataWriter.saveSongs(songList.getSongList());
        // pALT.play(jjScore.getSequence(0, jjScore.size(), null, 1));



        /*
         * Scenario Code
         */
        SongList songList = SongList.getInstance();
        String ARTIST = "James Jamerson";
        System.out.println("Searching for songs by " + ARTIST);
        ArrayList<Song> search = songList.getSongsByArtist(ARTIST);
        System.out.println(search.size() + " song(s) found by " + ARTIST);
        for(int i=0; i<search.size(); i++) {
            System.out.println(i+1 + ". " + search.get(i).getTitle());
        }
        int CHOICE = 1;
        Player p = new Player();
        //p.play(search.get(CHOICE-1).getScore().getSequence(0, search.get(CHOICE-1).getScore().size(), null, 1));
        System.out.println("Chosen " + (CHOICE) + ". " + search.get(CHOICE-1).getTitle());
        try {
            FileWriter file = new FileWriter(search.get(CHOICE-1).getTitle() + ".txt");
            file.write(search.get(CHOICE-1).getScore().getTablature());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Sheet music has been output to " + search.get(CHOICE-1).getTitle() + ".txt");


    }
}