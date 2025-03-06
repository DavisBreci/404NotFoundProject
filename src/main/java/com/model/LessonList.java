package com.model;

import java.util.ArrayList;

public class LessonList {
    
    private static LessonList lessonList;
    private ArrayList<Lesson> lessons;

    private LessonList() {
        lessons = new ArrayList<>();
    }

    public static LessonList getInstance() {
        if(lessonList == null) {
            lessonList = new LessonList();
        }

        return lessonList;
    }

    public Lesson getLesson(String title) {
        return null;
    }

    /**
     * @author brenskrz
     * @return lessons
     */
    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public Lesson addLesson(String title) {
        return null;
    }

    
}
