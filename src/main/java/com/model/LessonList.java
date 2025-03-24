package com.model;

import java.util.ArrayList;

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
     * @author Davis Breci
     * @return
     */    
    public static LessonList getInstance() {
        if(lessonList == null) {
            lessonList = new LessonList();
        }
        return lessonList;
    }
    /**
     * searches for lesson by title
     * @author Davis Breci
     * @param title lesson being searched for
     * @return matching lesson
     */
    public Lesson getLesson(String title) {
        for(Lesson l : lessons)
            if(l.getTitle().equals(title))
                return l;
        return null;
    }

    /**
     * Getter for the lessons
     * @author brenskrz
     * @return The arrayList of lessons
     */
    public ArrayList<Lesson> getLessons() {
        return lessons;
    }
    /**
     * creates a new lesson and adds it to the lesson list
     * @param songs songs included in the lesson
     * @param title title (without number code at beginning). cannot begin with ~.
     */
    public void createLesson(ArrayList<Song> songs, String title) {
        if(title.charAt(0) == '~')
            return;
        lessons.add(new Lesson(null, songs, title));
    }

    
}
