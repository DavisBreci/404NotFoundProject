package com.model;

import java.util.ArrayList;

public class LessonList {
    
    private static LessonList lessonList;
    private  ArrayList<Lesson> lessons;
    
        LessonList() {
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
         * Getter for the lessons
         * @return The arrayList of lessons
         */
        public ArrayList<Lesson> getLessons() {
            return lessons;
        }
    
        public void addLesson(String title, ArrayList<Song> songs) {
            Lesson newLesson = new Lesson(null, songs, title);
            lessons.add(newLesson);
    }

    
}
