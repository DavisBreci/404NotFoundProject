package model;

import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.time.LocalDate;

import com.model.*;

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
}