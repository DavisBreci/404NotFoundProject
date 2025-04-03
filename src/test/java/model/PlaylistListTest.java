package model;

import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import com.model.Playlist;
import com.model.PlaylistList;
import com.model.Song;
import com.model.SongList;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.time.LocalDate;

/**
 * @author Christopher Ferguson
 */
public class PlaylistListTest {
    PlaylistList pll;
    SongList sl;

    @Before
    public void initialize(){
        pll = PlaylistList.getInstance();
        sl = SongList.getInstance();
    }

    @Test
    public void testSingleton(){
        PlaylistList pll2 = PlaylistList.getInstance();
        assertEquals(pll, pll2);
    }

    @Test
    public void testGetPlaylistNull(){
        assertNull(pll.getPlaylist(null, null, null, null, 0));
    }

    @Test
    public void getPlaylistCorrect(){
        Song alphys = sl.getSongByTitle("Alphys");
        Song gMaj = sl.getSongByTitle("G Major Chord");
        ArrayList<Song> library = new ArrayList<Song>();
        library.add(alphys);
        library.add(gMaj);
        assertNull(pll.getPlaylist("my epid playlist", "Davis Breci", "this is my le epic playlist",library , 2));
    }

    @Test
    public void getPlaylistWrongLength(){
        Song alphys = sl.getSongByTitle("Alphys");
        Song gMaj = sl.getSongByTitle("G Major Chord");
        ArrayList<Song> library = new ArrayList<Song>();
        library.add(alphys);
        library.add(gMaj);
        assertNull(pll.getPlaylist("my epid playlist", "Davis Breci", "this is my le epic playlist",library , 0));
    }

    @Test
    public void getPlaylistNullEntry(){
        pll.getAllPlaylists().add(null); // Add a null value to the playlists
        try{
            Song alphys = sl.getSongByTitle("Alphys");
            Song gMaj = sl.getSongByTitle("G Major Chord");
            ArrayList<Song> library = new ArrayList<Song>();
            library.add(alphys);
            library.add(gMaj);
            assertNull(pll.getPlaylist("my epid playlist", "Davis Breci", "this is my le epic playlist",library , 2));
        } catch(Exception e){
            pll.getAllPlaylists().removeLast();
            fail();
        }
    }

    @Test
    public void getPlaylistWrongLibrary(){ // Library is short one song
        Song alphys = sl.getSongByTitle("Alphys");
        ArrayList<Song> library = new ArrayList<Song>();
        library.add(alphys);
        assertNull(pll.getPlaylist("my epid playlist", "Davis Breci", "this is my le epic playlist",library , 2));
    }

    @Test
    public void testGetPlaylistByTitleNormal(){
        Playlist p = pll.createPlaylist("Jams", "Lammy", "null");
        assertEquals(p, pll.getPlaylistByTitle("Jams"));
        pll.removePlaylist(p.id);
    }

    @Test
    public void testGetPlaylistByTitleTypo(){
        Playlist p = pll.createPlaylist("Jams", "Lammy", "null");
        assertNull(pll.getPlaylistByTitle("Jamss"));
        pll.removePlaylist(p.id);
    }

    @Test
    public void testGetPlaylistByTitleNull(){
        assertNull(pll.getPlaylistByTitle(null));
    }
    
    @Test
    public void testGetPlaylistByAuthorNormal(){
        Playlist p = pll.createPlaylist("Jams", "Lammy", "null");
        assertEquals(p, pll.getPlaylistByAuthor("Lammy"));
        pll.removePlaylist(p.id);
    }

    @Test
    public void testGetPlaylistByAuthorTypo(){
        Playlist p = pll.createPlaylist("Jams", "Lammy", "null");
        assertNull(pll.getPlaylistByAuthor("Lamm"));
        pll.removePlaylist(p.id);
    }

    @Test
    public void testGetPlaylistByAuthorNull(){
        assertNull(pll.getPlaylistByAuthor(null));
    }


    @Test
    public void testCreatePlaylistNull(){
        try{
            assertNull(pll.createPlaylist(null, null, null));
        } catch(Exception e){
            fail();
        }
    }

    @Test
    public void testCreatePlaylistTitle(){ // Ensure each playlist has a unique title
        Playlist p = pll.createPlaylist("Jams", "Lammy", "null");
        Playlist p2 = pll.createPlaylist("Jams", "Lammy", "null");
        assertNotEquals(p.getTitle(), p2.getTitle());
    }

    @Test
    public void testCreatePlaylistId(){ // Ensure each playlist has a unique id
        Playlist p = pll.createPlaylist("Jams", "Lammy", "null");
        Playlist p2 = pll.createPlaylist("Jams", "Lammy", "null");
        assertNotEquals(p.id, p2.id);
    }

    @Test
    public void testCreatePlaylistTilde(){ // Tilde is reserved for titles
        assertNull(pll.createPlaylist("~Jams", "Lammy", "null"));
    }

    @Test
    public void testRemovePlaylistById(){
        Playlist p = pll.createPlaylist("Jams", "Lammy", "null");
        pll.removePlaylist(p.id);
        assertFalse(pll.getAllPlaylists().contains(p));
    }

    @Test
    public void testRemovePlaylistWrongId(){
        try{
            pll.removePlaylist("blah");
        } catch(Exception e){
            fail();
        }
    }

    @Test
    public void testRemovePlaylistWrongNull(){
        try{
            pll.removePlaylist(null);
        } catch(Exception e){
            fail();
        }
    }

}
