package com.model;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import org.jfugue.player.Player;

public class UserInterface {
    public static void main(String[] args) {
        UserList users = UserList.getInstance();
        SongList songList = SongList.getInstance();
        songList.removeSong(songList.getSongByTitle("Smoke On the Water"));
        users.removeUser("MzFrizz");
        users.removeUser("ffred");
        teacherSignUpScenario();
        fredSignUpScenario();
        writingASongScenario();
        playingASongScenario();
    }
    /**
     * @author Christopher Ferguson
     */
    public static void teacherSignUpScenario(){
        MusicSystemFACADE system = MusicSystemFACADE.getInstance();
        String firstName = "Valerie";
        String lastName = "Frizzle";
        String email = "valeriefrizzle@yahoo.com";
        String username = "MzFrizz";
        String password = "@Bc123";
        System.out.println("Attempting to sign up as " + username + "...");
        if(system.signUp(true, firstName, lastName, email, username, password))
            System.out.println("\tSign-up successful!");
        else
            System.out.println("\tSign-up failed.");

        System.out.println("Attempting to create a class...");
        int classNumber = system.createClass();
        if(classNumber >= 0)
            System.out.println("\tSuccessfully created class #" + classNumber + "!");
        else
            System.out.println("\tClass creation failed.");
        System.out.println("Attempting to add Students to class...");
        system.addToClass(
                classNumber,
                system.fetchUser("dbreci"),
                system.fetchUser("ctferg")
        );
        System.out.println("Printing roster for class #" + classNumber + "...");
        for(User user : system.getRoster(classNumber))
            System.out.println("\t" + user.getUsername());
        String lessonName = "Learning Songs From Video Games: 'Alphys' by Toby Fox";
        System.out.println("Attempting to create a lesson \"" + lessonName + "\"...");
        Lesson firstLesson = system.createLesson(lessonName); // Doesn't work yet because LessonList isn't done
        firstLesson.addSong(system.getSong("Alphys"));
        System.out.println("Attempting to assign lesson to class #" + classNumber + "...");
        system.assignLesson(classNumber, firstLesson);
        for(User user : system.getRoster(classNumber)){
            System.out.println("\t" + user.getUsername() + "'s assigned lessons:");
            for(Lesson lesson : user.getAssignedLessons())
                System.out.println("\t\t" + lesson.getTitle());
        }
        System.out.println("Logging out now");
        system.logout();
    }
    /**
     * Fred attempts to sign up but must change his username to something unique
     * @author Davis Breci
     */
    public static void fredSignUpScenario(){
        MusicSystemFACADE system = MusicSystemFACADE.getInstance();
        // boolean teacher = false;
        // String first = "Fellicia";
        // String last = "Fredrickson";
        // String email = "fellicia@gmail.com";
        // String user = "ffredrickson";
        // String pass = "iHaTeMyBr0tHeR*";
        // system.signUp(teacher,first,last,email,user,pass);
        // system.logout();
        boolean teacher = false;
        String first = "Fred";
        String last = "Fredrickson";
        String email = "fred^2@gmail.com";
        String user = "ffredrickson";
        String pass = "I<3mysister";
        ArrayList<User> userList = UserList.getInstance().getAllUsers();
        for(User u : userList)
            System.out.println(u.toString());
        System.out.println("UserList "+(UserList.getInstance().contains(user)
                           ? "contains " : "does not contain ")+user);
        System.out.println("Attempting to sign up as "+user+"...");
        if(system.signUp(teacher, first, last, email, user, pass))
            System.out.println("\tSign-up successful!");
        else {
            System.out.println("\tSign-up failed.");
            user = "ffred";
            System.out.println("Attempting to sign up as "+user+"...");
            System.out.println("\tSign-up "+(system.signUp(teacher, first, last, email, user, pass)
                               ? "success!" : "failed."));
        }
        system.logout();
        userList = UserList.getInstance().getAllUsers();
        for(User u : userList)
            System.out.println(u.toString());
        System.out.println("UserList "+(UserList.getInstance().contains(user)
                           ? "contains " : "does not contain ")+user);
        System.out.println("Attempting to login as "+user+"...");
        System.out.println("Login "+(system.login(user, pass) ? "success!" : "failed."));
        system.logout();
    }
    /**
     * @author Christopher Ferguson
     */
    public static void writingASongScenario(){
        MusicSystemFACADE system = MusicSystemFACADE.getInstance();
        String username = "ctferg";
        String password = "247sucks";
        System.out.println("Scenario 3: Making a Song\n");
        System.out.println("Attempting to sign in as " + username);

        if(system.login(username, password))
            System.out.println("Login successful!");
        else
            System.out.println("Login failed");
            
        System.out.println("\nBeginning to write a score");

        Instrument instrument = Instrument.DISTORTION_GUITAR; // Choose our instrument
        int tempo = 120;
        System.out.println("Creating " + instrument + " score with a tempo of " + tempo);
        Score smokeOnTheWater = new Score(null, instrument, tempo); // Initialize the score
        System.out.println("Creating four empty measures");
        Rational timeSignature = new Rational(4);
        for(int i = 0; i < 4; i++){// Add 4 measures of 4/4
            smokeOnTheWater.add(new Measure(instrument, timeSignature));
            System.out.println("\tCreated measure " + (i + 1) + " with time signature " + timeSignature);
        }

        Chord powerChord = new Chord(NoteValue.EIGHTH, false, instrument); // Create a power chord shape
        powerChord.put(new Note(PitchClass.D, 3), 1);
        powerChord.put(new Note(PitchClass.A, 3), 2);
        System.out.println("\nCreating a power chord shape: " + powerChord);
        Measure m = smokeOnTheWater.get(0); // First measure
        m.put(new Rational("0/1"), powerChord.deepCopy()); // Add chord to measure
            powerChord.shiftString(1); // Move the chord shape around
            powerChord.transpose(-2);
        m.put(new Rational("1/4"), powerChord.deepCopy()); // repeat!
            powerChord.transpose(2);
        m.put(new Rational("2/4"), powerChord.deepCopy());
            powerChord.shiftString(-1);
        m.put(new Rational("7/8"), powerChord.deepCopy());
            powerChord.shiftString(1);
            powerChord.transpose(-2);
        System.out.println("Wrote first measure:\n" + Score.getMeasureTablature(m));
        m = smokeOnTheWater.get(1);  // Second measure
        m.put(new Rational("1/8"), powerChord.deepCopy());
            powerChord.transpose(3);
        m.put(new Rational("3/8"), powerChord.deepCopy());
            powerChord.transpose(-1);
        m.put(new Rational("1/2"), powerChord.deepCopy());
            powerChord.shiftString(-1);
        System.out.println("Wrote second measure:\n" + Score.getMeasureTablature(m));
        m = smokeOnTheWater.get(2); // Third measure
        m.put(new Rational("0/4"), powerChord.deepCopy());
            powerChord.shiftString(1);
            powerChord.transpose(-2);
        m.put(new Rational("1/4"), powerChord.deepCopy());
            powerChord.transpose(2);
        m.put(new Rational("1/2"), powerChord.deepCopy());
            powerChord.transpose(-2);
        m.put(new Rational("7/8"), powerChord.deepCopy());
            powerChord.shiftString(-1);
            powerChord.transpose(2);
        System.out.println("Wrote third measure:\n" + Score.getMeasureTablature(m));
        m = smokeOnTheWater.get(3); // Fourth measure
        m.put(new Rational("1/8"), powerChord.deepCopy());
        System.out.println("Wrote fourth measure:\n" + Score.getMeasureTablature(m));
        System.out.println("Score writing complete, adding score to song \"Smoke on the Water\" by Christopher Ferguson");
        Song smokeOnTheWaterSong = new Song(
            null, 
            "Smoke on the Water", 
            "Christopher Ferguson", 
            "Hard Rock",
             Key.BbMAJOR_GMINOR, 
             DifficultyLevel.BEGINNER,
             Instrument.DISTORTION_GUITAR,
             smokeOnTheWater
        );
        System.out.println("Adding full song to library");
        if(system.addSongToLibrary(smokeOnTheWaterSong))
            System.out.println("Addition successful!");
        else
            System.out.println("Addition unsuccessful.");
        
        System.out.println("\nPlaying song...");
        system.playSong(smokeOnTheWaterSong);
        System.out.println("Playback complete");
        System.out.println("\nLogging out...");
        system.logout();

        username = "dbreci";
        password = "404nf";
        System.out.println("\nAttempting to sign in as " + username);

        if(system.login(username, password))
            System.out.println("Login successful!");
        else
            System.out.println("Login failed");
        
        System.out.println("\nSearching for \"Smoke on the Water\"");
        Song retrievedSong = system.getSong("Smoke on the Water");
        if(retrievedSong == smokeOnTheWaterSong)
            System.out.println("Found \"Smoke on the Water\" by " + retrievedSong.getArtist() + "!");
        else
            System.out.println("Search failed");
        System.out.println("\nPlaying song...");
        system.playSong(retrievedSong);
        System.out.println("\nLogging out...");
        system.logout();

    }

    /**
     * @author Ryan Mazzella
     */
    public static void playingASongScenario(){
        SongList songList = SongList.getInstance();
        //User chooses artist to search for
        String ARTIST = "James Jamerson";
        System.out.println("Searching for songs by " + ARTIST);
        ArrayList<Song> search = songList.getSongsByArtist(ARTIST);
        System.out.println(search.size() + " song(s) found by " + ARTIST);
        for(int i=0; i<search.size(); i++) {
            System.out.println(i+1 + ". " + search.get(i).getTitle());
        }
        //User selects a song to play from the results
        int CHOICE = 1;
        Player p = new Player();
        System.out.println("Chosen " + (CHOICE) + ". " + search.get(CHOICE-1).getTitle());
        System.out.println("Now playing " + search.get(CHOICE-1).getTitle());
        p.play(search.get(CHOICE-1).getScore().getSequence(0, search.get(CHOICE-1).getScore().size(), null, 1));
        //Sheet music is then output to a text file
        try {
            FileWriter file = new FileWriter(search.get(CHOICE-1).getTitle() + ".txt");
            file.write(search.get(CHOICE-1).getScore().getTablature());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Sheet music has been output to " + search.get(CHOICE-1).getTitle() + ".txt");
    }
}
