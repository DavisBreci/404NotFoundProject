package model;

import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import static java.util.Map.ofEntries;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.time.LocalDate;

import com.model.*;

public class LessonListTest {
   
    //Does not return the correct value, returns a memory address or extra value in front
    @Test
    public void createLessonTest1() {
        LessonList lessonList = LessonList.getInstance();
        ArrayList<Song> songs = new ArrayList<>();
        songs.add(new Song("id","Song1", "Brendan", "rock", Key.AMAJOR_GbMINOR, DifficultyLevel.ADVANCED, Instrument.ACOUSTIC_BASS, new Score(null, Instrument.ACOUSTIC_BASS, 100)));
        lessonList.createLesson(songs, "Lesson1" );
        assertEquals("Lesson1", lessonList.getLessons().get(0).getTitle());
    }
    @Test
    public void createLessonTest2() {
        LessonList lessonList = LessonList.getInstance();
        ArrayList<Song> songs = new ArrayList<>();
        songs.add(new Song("id","Song1", "Brendan", "rock", Key.AMAJOR_GbMINOR, DifficultyLevel.ADVANCED, Instrument.ACOUSTIC_BASS, new Score(null, Instrument.ACOUSTIC_BASS, 100)));

        lessonList.createLesson(songs, "Lesson1");
        Lesson lesson = lessonList.getLesson("Lesson1");
        assertFalse(lessonList.getLessons().isEmpty());

        assertNotNull(lesson);

    }
    @Test
    public void createLessonTest3() {
        LessonList lessonList = LessonList.getInstance();
        ArrayList<Song> songs = new ArrayList<>();
        songs.add(new Song("id","Song1", "Brendan", "rock", Key.AMAJOR_GbMINOR, DifficultyLevel.ADVANCED, Instrument.ACOUSTIC_BASS, new Score(null, Instrument.ACOUSTIC_BASS, 100)));
        songs.add(new Song("id","Song2", "Brendan", "rock", Key.AMAJOR_GbMINOR, DifficultyLevel.ADVANCED, Instrument.ACOUSTIC_BASS, new Score(null, Instrument.ACOUSTIC_BASS, 100)));
        songs.add(new Song("id","Song3", "Brendan", "rock", Key.AMAJOR_GbMINOR, DifficultyLevel.ADVANCED, Instrument.ACOUSTIC_BASS, new Score(null, Instrument.ACOUSTIC_BASS, 100)));
        lessonList.createLesson(songs, "Lesson2");
        assertEquals(2, lessonList.getLesson("Lesson2").getSongs().size());
    }
    //Does not run, should throw a IllegalArgumentException for creating two identical lessons
    @Test
    public void createLessonTest4() {
        LessonList lessonList = LessonList.getInstance();
        ArrayList<Song> songs = new ArrayList<>();
        songs.add(new Song("id","Song1", "Brendan", "rock", Key.AMAJOR_GbMINOR, DifficultyLevel.ADVANCED, Instrument.ACOUSTIC_BASS, new Score(null, Instrument.ACOUSTIC_BASS, 100)));
        lessonList.createLesson(songs, "Lesson2");
        assertThrows(
            IllegalArgumentException.class,
            new ThrowingRunnable() {
                @Override
                public void run() throws Throwable {
                    lessonList.createLesson(songs, "Lesson2");
                }
            }
        );   

    }

}
