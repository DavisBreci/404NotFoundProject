package com.model;

import java.util.ArrayList;

/**
 * Class that manages all the program's lessons
 */
public class LessonList {
    
    private static LessonList lessonList;
    private  ArrayList<Lesson> lessons;
    /**
     * singleton class constructor
     * @author Davis Breci
     */ 
    private LessonList() {
        lessons = DataLoader.getAllLessons();
    }
    /**
     * returns instance of singleton class
     * @return the LessonList instance
     * @author Davis Breci
     * 
     */    
    public static LessonList getInstance() {
        if(lessonList == null) {
            lessonList = new LessonList();
        }
        return lessonList;
    }
    /**
     * searches for lesson by title
     * @param title lesson being searched for
     * @return matching lesson
     * @author Davis Breci
     */
    public Lesson getLesson(String title) {
        for(Lesson l : lessons)
            if(l.getTitle().equals(title))
                return l;
        return null;
    }

    /**
     * Getter for the lessons
     * @return The arrayList of lessons
     * @author brenskrz
     */
    public ArrayList<Lesson> getLessons() {
        return lessons;
    }
    /**
     * creates a new lesson and adds it to the lesson list
     * @param songs songs included in the lesson
     * @param title title (without number code at beginning). cannot begin with ~.
     * @author Davis Breci
     */
    public Lesson createLesson(ArrayList<Song> songs, String title) {
        if(title.charAt(0) == '~')
            return null;
        Lesson toAdd = new Lesson(null, songs, title);
        lessons.add(toAdd);
        return toAdd;
    }
}
