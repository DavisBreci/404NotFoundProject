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
public class SongListTest{
    @Test
    public void getInstanceTest1(){
        SongList sl = SongList.getInstance();
        assertNotNull(sl);
    }
    @Test
    public void getInstanceTest2(){
        SongList sl = SongList.getInstance();
        SongList sl2 = SongList.getInstance();
        assertEquals(sl, sl2);
    }
    @Test
    public void getInstanceTest3(){
        SongList sl = SongList.getInstance();
        SongList copy = sl;
        sl = SongList.getInstance();
        assertEquals(sl, copy);
    }
    @Test
    public void getInstanceTest4(){
        SongList sl = SongList.getInstance();
        SongList copy = sl;
        sl.getInstance();
        assertEquals(sl, copy);
    }
    SongList sl;
    @Before
    public void initialize(){
        sl = SongList.getInstance();
    }
    @Test
    public void getSongByTitleTest1(){
        assertNull(sl.getSongByTitle("asdfsdjflks"));
    }
    @Test
    public void getSongByTitleTest2(){
        assertNull(sl.getSongByTitle(null));
    }
    @Test
    public void getSongByTitleTest3(){
        assertNotNull(sl.getSongByTitle("Smoke on the Water"));
    }
    @Test
    public void getSongByTitleTest4(){
        sl.createSong("title", "me", "phonk", Key.AMAJOR_GbMINOR, DifficultyLevel.ADVANCED, Instrument.ACOUSTIC_BASS, new Score(null, Instrument.ACOUSTIC_BASS, 100));
        assertNotNull(sl.getSongByTitle("title"));
    }

    @Test
    public void createSongTest() {
        sl.createSong("Song1", "Brendan", "rock", Key.AMAJOR_GbMINOR, DifficultyLevel.ADVANCED, Instrument.ACOUSTIC_BASS, new Score(null, Instrument.ACOUSTIC_BASS, 100));
        assertEquals("Song1", sl.getSongByTitle("Song1").getTitle());
    }
    @Test
    public void createSongTest2() {
        sl.createSong("Song1", "Brendan", "rock", Key.AMAJOR_GbMINOR, DifficultyLevel.ADVANCED, Instrument.ACOUSTIC_BASS, new Score(null, Instrument.ACOUSTIC_BASS, 100));
        sl.createSong("Song2", "Chris", "jazz", Key.AMAJOR_GbMINOR, DifficultyLevel.ADVANCED, Instrument.ACOUSTIC_BASS, new Score(null, Instrument.ACOUSTIC_BASS, 100));
        assertEquals("Chris", sl.getSongsByArtist("Chris").get(0).getArtist());
    }
    //Does not pass, should throw an illegal argument exception to prevent two identical songs being made
    @Test
    public void createSongTest3() {
        sl.createSong("Song1", "Brendan", "rock", Key.AMAJOR_GbMINOR, DifficultyLevel.ADVANCED, Instrument.ACOUSTIC_BASS, new Score(null, Instrument.ACOUSTIC_BASS, 100));
        assertThrows(
            IllegalArgumentException.class,
            new ThrowingRunnable() {
                @Override
                public void run() throws Throwable {
                    sl.createSong("Song1", "Brendan", "rock", Key.AMAJOR_GbMINOR, DifficultyLevel.ADVANCED, Instrument.ACOUSTIC_BASS, new Score(null, Instrument.ACOUSTIC_BASS, 100));
                }
            }
        );   
    }
    //Does not pass, should be null when passed an empty string for Title Artist and genre
    @Test
    public void createSongTest4() {
        sl.createSong("", "", "", Key.AMAJOR_GbMINOR, DifficultyLevel.ADVANCED, Instrument.ACOUSTIC_BASS, new Score(null, Instrument.ACOUSTIC_BASS, 100));
        assertNull("", sl.getSongByTitle(""));
    }
    @Test
    public void createSongTest5() {
        assertThrows(
            IllegalArgumentException.class,
            new ThrowingRunnable() {
                @Override
                public void run() throws Throwable {
                    sl.createSong(null, null, null, Key.AMAJOR_GbMINOR, DifficultyLevel.ADVANCED, Instrument.ACOUSTIC_BASS, new Score(null, Instrument.ACOUSTIC_BASS, 100));
                }
            });
    }
    //Does not run, a song should not be able to be created with a null instrument and should throw an illegalArgumentException
    @Test
    public void createSongTest6() {
        sl.createSong("title", "me!", "jazz", Key.AMAJOR_GbMINOR, DifficultyLevel.ADVANCED, Instrument.ACOUSTIC_BASS, new Score(null, null, 0));
        assertEquals("title", sl.getSongsByArtist("me!").get(0).getTitle());
        assertThrows(
            IllegalArgumentException.class,
            new ThrowingRunnable() {
                @Override
                public void run() throws Throwable {
                    sl.createSong("title", "me!", "jazz", Key.AMAJOR_GbMINOR, DifficultyLevel.ADVANCED, Instrument.ACOUSTIC_BASS, new Score(null, null, 0));
                }
            });
    }
    public void testGetSongsByDifficultyNull(){
        assertEquals(0, sl.getSongsByDifficulty(null).size());
    }

    @Test
    public void testGetSongsByDifficultyNormal(){
        for(Song s : sl.getSongsByDifficulty(DifficultyLevel.ADVANCED))
            assertEquals(DifficultyLevel.ADVANCED, s.getDifficultyLevel());
    }

    @Test
    public void testGetSongsByKeyNull(){
        assertEquals(0, sl.getSongsByKey(null).size());
    }

    @Test
    public void testGetSongsByKeyNormal(){
        Song a = new Song(null, "Placeholder", "Penny Placeholder", "Placeholder music", Key.GMAJOR_EMINOR, DifficultyLevel.ADVANCED, Instrument.GUITAR, new Score(null, Instrument.GUITAR, 120));
        sl.add(a);
        for(Song s : sl.getSongsByKey(Key.GMAJOR_EMINOR))
            assertEquals(Key.GMAJOR_EMINOR, s.getKey());
        sl.removeSong(a);
    }

    @Test
    public void testGetSongsByGenreNull(){
        assertEquals(0, sl.getSongsByGenre(null).size());
    }

    @Test
    public void testGetSongsByGenreNormal(){
        Song a = new Song(null, "Placeholder", "Penny Placeholder", "Placeholder music", Key.GMAJOR_EMINOR, DifficultyLevel.ADVANCED, Instrument.GUITAR, new Score(null, Instrument.GUITAR, 120));
        sl.add(a);
        assertTrue(sl.getSongsByGenre("Placeholder music").contains(a));
        sl.removeSong(a);
    }

    @Test
    public void testGetSongsByArtistNull(){
        assertEquals(0, sl.getSongsByArtist(null).size());
    }

    @Test
    public void testGetSongsByArtistNormal(){
        Song a = new Song(null, "Placeholder", "Penny Placeholder", "Placeholder music", Key.GMAJOR_EMINOR, DifficultyLevel.ADVANCED, Instrument.GUITAR, new Score(null, Instrument.GUITAR, 120));
        sl.add(a);
        assertTrue(sl.getSongsByArtist("Penny Placeholder").contains(a));
        sl.removeSong(a);
    }
    
}