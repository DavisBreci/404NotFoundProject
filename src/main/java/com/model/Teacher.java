//stub finished
package com.model;
import java.util.*;

public class Teacher extends User {
    private ArrayList<ArrayList<User>> classes;
    private ArrayList<Lesson> lessons;

    public Teacher(UUID id, String first, String last, String email,
                   String user, String pass, ArrayList<ArrayList<User>>
                   classes, ArrayList<Lesson> lessons){
        super(id, first, last, email, user, pass);
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

    }
    public void updateStudents(ArrayList<User> classroom){

    }
    public void createLesson(String lessonName){

    }
}
