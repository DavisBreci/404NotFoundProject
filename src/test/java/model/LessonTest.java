package model;

import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import static org.junit.Assert.*;

import java.util.ArrayList;

import com.model.*;

public class LessonTest {
    @Test
    public void constructorTest1(){
        assertThrows(
            IllegalArgumentException.class,
            new ThrowingRunnable() {
                @Override
                public void run() throws Throwable {
                    Lesson l = new Lesson(null, new ArrayList<Song>(), null);
                }
            }
        );
    }
    @Test
    public void constructorTest2(){
        assertThrows(
            IllegalArgumentException.class,
            new ThrowingRunnable() {
                public void run(){
                    Lesson l = new Lesson(null, new ArrayList<Song>(), "asdfksdakfjklasjd;lfvjdahfjkvjdkvjlkhdfjhvjkldhfkjhaskjdhfjkdhajfklhadjkfh");
                }
            }
        );
    }
    @Test
    public void constructorTest3(){
        Lesson l = new Lesson(null, null, "hello world");
        assertNotNull(l);
    }
    ArrayList<Song> songs;
    Song s;
    Lesson l;
    @Before
    public void initialize(){
        songs = new ArrayList<Song>();
        s = new Song(null, "Hello", "Adele", "Pop", Key.CMAJOR_AMINOR, DifficultyLevel.INTERMEDIATE, Instrument.GUITAR, new Score(null, Instrument.GUITAR, 80));
        songs.add(s);
        l = new Lesson(null, songs, "Adele Hits");
    }
    @Test
    public void addSongTest1(){
        l.addSong(null);
        assertEquals(l.getSongs().size(), 1);
    }
    @Test
    public void addSongTest2(){
        l.addSong(s);
        assertEquals(l.getSongs().size(), 2);
    }
    @Test
    public void addSongTest3(){
        l.addSong(new Song(null, "hi", "jadele", "prop", Key.BbMAJOR_GMINOR, DifficultyLevel.ADVANCED, Instrument.DISTORTION_GUITAR, new Score(null, Instrument.DISTORTION_GUITAR, 250)));
        assertEquals(l.getSongs().size(), 2);
    }
    @Test
    public void removeSongTest1(){
        l.removeSong(songs.get(0));
        assertEquals(l.getSongs().size(), 0);
    }
    @Test
    public void removeSongTest2(){
        l.removeSong(null);
        assertEquals(l.getSongs().size(), 1);
    }
    @Test
    public void removeSongTest3(){
        l.removeSong(0);
        assertEquals(l.getSongs().size(), 0);
    }
    @Test
    public void removeSongTest4(){
        l.removeSong(1);
        assertEquals(l.getSongs().size(), 1);
    }
}