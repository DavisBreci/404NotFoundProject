package com.model;

import java.util.Random;

public class UserInterface {
    public static void main(String[] args) {
        teacherSignUpScenario();
    }
    /**
     * @author Christopher Ferguson
     */
    public static void teacherSignUpScenario(){
        MusicSystemFACADE system = MusicSystemFACADE.getInstance();
        String firstName = "Valerie";
        String lastName = "Frizzle";
        String email = "valeriefrizzle@yahoo.com";
        String username = "MzFrizz" + (new Random().nextInt(2025) + 1);
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
        String lessonName = "Learning Songs From Vide Games: 'Alphys' by Toby Fox";
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
     * @author Christopher Ferguson
     */
    public static void writingASongScenario(){
        MusicSystemFACADE system = MusicSystemFACADE.getInstance();
        String username = "ctferg";
        String password = "247sucks";
        System.out.println("Attempting to sign in as " + username);
        if(system.login(username, password))
            System.out.println("Login successful!");
        else
            System.out.println("Login failed");
            
        System.out.println("Beginning to write a score");

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
        System.out.println("Creating a power chord shape: " + powerChord);
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
        System.out.println("Wrote first measure: | " + smokeOnTheWater.get(0));
        m = smokeOnTheWater.get(1);  // Second measure
        m.put(new Rational("1/8"), powerChord.deepCopy());
            powerChord.transpose(3);
        m.put(new Rational("3/8"), powerChord.deepCopy());
            powerChord.transpose(-1);
        m.put(new Rational("1/2"), powerChord.deepCopy());
            powerChord.shiftString(-1);
        System.out.println("Wrote second measure: | " + smokeOnTheWater.get(1));
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
        System.out.println("Wrote third measure: | " + smokeOnTheWater.get(2));
        m = smokeOnTheWater.get(3); // Fourth measure
        m.put(new Rational("1/8"), powerChord.deepCopy());
        System.out.println("Wrote fourth measure: | " + smokeOnTheWater.get(3));
        System.out.println("Score writing complete, adding score to song \"Smoke on the Water\" by Deep Purple");
        Song smokeOnTheWaterSong = new Song(
            null, 
            "Smoke on the Water", 
            "Deep Purple", 
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
    }
}
