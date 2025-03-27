package com.model;
import java.util.*;
import java.time.LocalDate;

/**
 * Java class that represents a music teacher. Teachers manage classes, assigning lessons to studentss.
 */
public class Teacher extends User {
    private ArrayList<ArrayList<User>> classes;
    private ArrayList<Lesson> lessons;

    /**
     * Constructs a teacher
     * @param id the teacher's uuid (null if you want a random id)
     * @param first first name
     * @param last last name
     * @param email email
     * @param user username
     * @param pass password
     * @param streak number of consecutive days where the user played a song
     * @param songsPlayed number of songs played by the user
     * @param playlists the teacher's personal library
     * @param assignedLessons the lessons assigned to this user
     * @param lastPlayed the date the user last played a song
     * @param classes the teacher's classes
     * @param lessons the lessons assiigned by the teacher
     */
    public Teacher(String id, String first, String last, String email,
                   String user, String pass, int streak, int songsPlayed,
                   ArrayList<Playlist> playlists, ArrayList<Lesson> assignedLessons, LocalDate lastPlayed,
                   ArrayList<ArrayList<User>> classes, ArrayList<Lesson> lessons){
        super(id, first, last, email, user, pass, streak, songsPlayed, playlists, assignedLessons, lastPlayed);
        this.classes = classes;
        this.lessons = lessons;
    }

    /**
     * Adds a student to a class
     * @param classIndex the number of the class
     * @param student the student to be added
     * @return whether the addition was successful
     * @authoro Davis Breci
     */
    public boolean addStudent(int classIndex, User student){
        if(student != null){
            classes.get(classIndex).add(student);
            return true;
        }
        return false;
    }
    
    /**
     * Assigns a lesson to a class
     * @param classIndex the number of the class
     * @param lesson the lesson to be assigned
     * @author Davis Breci
     */
    public void assignLessons(int classIndex, Lesson lesson){
        lessons.add(lesson);
        for(User student : classes.get(classIndex))
            student.assignLesson(lesson);
    }

    /**
     * Retrieves the teacher's classes
     * @return a two-dimensional array where the members of the outer array are classes
     * @author Davis Breci
     */
    public ArrayList<ArrayList<User>> getClasses() {
        return classes;
    }

    /**
     * Retrieves the lessons that this teacher has assigned. 
     * @return the lessons
     * @author brenskrz
     */
    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    /**
     * Retrieves the teacher's assigned lessons
     * @author brenskrz
     */
    public ArrayList<Lesson> getAssignedLessons() {
        return super.getAssignedLessons();
    }
    
    /**
     * Allows the teacher to create a new class
     * @return the new class's number.
     * @author Christopher Ferguson
     * 
     */
    public int createClass(){
        classes.add(new ArrayList<User>());
        return classes.size() - 1;
    }
}
