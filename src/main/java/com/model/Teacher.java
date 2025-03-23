//stub complete
package com.model;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Teacher extends User {
    private ArrayList<ArrayList<User>> classes;
    private ArrayList<Lesson> lessons;

    public Teacher(String id, String first, String last, String email,
                   String user, String pass, int streak, int songsPlayed,
                   ArrayList<Playlist> playlists, ArrayList<Lesson> assignedLessons, LocalDate lastPlayed,
                   ArrayList<ArrayList<User>> classes, ArrayList<Lesson> lessons){
        super(id, first, last, email, user, pass, streak, songsPlayed, playlists, assignedLessons, lastPlayed);
        this.classes = classes;
        this.lessons = lessons;
    }
    public boolean addStudent(int classIndex, User student){
        if(student != null){
            classes.get(classIndex).add(student);
            return true;
        }
        return false;
    }
    public void assignLessons(int classIndex, Lesson lesson){
        lessons.add(lesson);
        for(User student : classes.get(classIndex))
            student.assignLesson(lesson);
    }
//probably not necessary
    // public void createLesson(String lessonName){

    // }
    public ArrayList<ArrayList<User>> getClasses() {
        return classes;
    }
    public ArrayList<Lesson> getLessons() {
        return lessons;
    }
    public ArrayList<Lesson> getAssignedLessons() {
        return super.getAssignedLessons();
    }
    
    /**
     * @author Christopher Ferguson
     * Allows the teacher to create a new class
     * @return the new class's number.
     */
    public int createClass(){
        classes.add(new ArrayList<User>());
        return classes.size() - 1;
    }
}
