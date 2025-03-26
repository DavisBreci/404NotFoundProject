package com.model;


import javax.sound.midi.Sequence;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.jfugue.player.Player;

public class RyanTester extends DataConstants {
    public static void main(String[] args) {
        //Score dummyscore = DataLoader.getScoreFromID("3a6c83d2-2235-4fff-84dc-7ad6ec2dabf8");
        //ArrayList<User> dummyusers = DataLoader.getUsers();
        //ArrayList<Teacher> dummyusers = DataLoader.getTeachers();
        //ArrayList<Song> dummysongs = DataLoader.getAllSongs();
        //ArrayList<Playlist> dummPlaylists = DataLoader.getAllPlaylists();

        Player pALT = new Player();
        Sequence rawMidi = DataLoader.loadSequence("Money.mid");
        Score jj1 = Score.midiToScore(rawMidi, 0, Instrument.ELECTRIC_BASS_FINGER);
        pALT.play(jj1.getSequence(0, jj1.size(), null, 1));
        //Song jj2Song = new Song(null, "What's Going On", "Marvin Gaye", "Soul", Key.EMAJOR_DbMINOR, DifficultyLevel.INTERMEDIATE, Instrument.ELECTRIC_BASS_FINGER, jj2);
        //p.play(jj2Song.getScore().getSequence(0, jj2Song.getScore().size(), null, 1));

        //SongList songList = SongList.getInstance();
        //songList.add(jj2Song);
        //DataWriter.saveNewScore(jj2, SCORE_FILE_NAME);
        //DataWriter.saveSongs(songList.getSongList());
        //search songs by artist
        //ArrayList<Song> search = songList.getSongsByArtist("Marvin Gaye");
        // //list songs
        // System.out.println(search.size() + " song(s) found by Marvin Gaye");
        // for(int i=0; i<search.size(); i++) {
        //     System.out.println(i+1 + ". " + search.get(i).getTitle());
        // }
        // Player p = new Player();
        // //choose song 
        // int choice = 0;
        // //p.play(search.get(choice).getScore().getSequence(0, search.get(choice).getScore().size(), null, 1));
        // System.out.println("Chosen " + search.get(choice).getTitle());
        // //System.out.println("Outputting the following text to a file:\n" + search.get(choice).getScore().getTablature());
        // //output song to txt 
        // try {
        //     FileWriter file = new FileWriter(search.get(choice).getTitle() + ".txt");
        //     file.write(search.get(choice).getScore().getTablature());
        // } catch (Exception e) {
        //     e.printStackTrace();
        //}



        
        SongList songList = SongList.getInstance();
        String ARTIST = "Marvin Gaye";
        System.out.println("Searching for songs by " + ARTIST);
        ArrayList<Song> search = songList.getSongsByArtist(ARTIST);
        System.out.println(search.size() + " song(s) found by " + ARTIST);
        for(int i=0; i<search.size(); i++) {
            System.out.println(i+1 + ". " + search.get(i).getTitle());
        }
        int CHOICE = 1;
        // Player p = new Player();
        // p.play(search.get(CHOICE-1).getScore().getSequence(0, search.get(CHOICE-1).getScore().size(), null, 1));
        System.out.println("Chosen " + (CHOICE-1) + ". " + search.get(CHOICE-1).getTitle());
        try {
            FileWriter file = new FileWriter(search.get(CHOICE-1).getTitle() + ".txt");
            file.write(search.get(CHOICE-1).getScore().getTablature());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Sheet music has been output to " + search.get(CHOICE-1).getTitle() + ".txt");


    }
}