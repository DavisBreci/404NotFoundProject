package model;

import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.time.LocalDate;

import com.model.*;
/**
 * @author Davis Breci
 */
public class SongTest {
    Score score;
    @Before public void initialize(){
        score = new Score(null, Instrument.GUITAR, 120);
    }
    @Test
    public void constructorTest1(){
        assertThrows(
            IllegalArgumentException.class,
            new ThrowingRunnable() {
                @Override
                public void run() throws Throwable {
                    Song s = new Song(null, "cool song", null, "heavy metal", Key.AMAJOR_GbMINOR, DifficultyLevel.BEGINNER, Instrument.GUITAR, score);
                }
            }
        );
    }
    @Test
    public void constructorTest2(){
        assertThrows(
            IllegalArgumentException.class,
            new ThrowingRunnable() {
                @Override
                public void run() throws Throwable {
                    Song s = new Song(null, "cool song", "validArtist", "heavy metal", Key.AMAJOR_GbMINOR, null, Instrument.GUITAR, score);
                }
            }
        );
    }
    @Test
    public void constructorTest3(){
        Song s = new Song(null, "cool song", "validArtist", "heavy metal", Key.AMAJOR_GbMINOR, DifficultyLevel.ADVANCED, Instrument.GUITAR, score);
        assertNotNull((Object)s);
    }
}