package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import static org.junit.Assert.*;

import java.util.ArrayList;

import com.model.*;
/**
 * @author Davis Breci
 */
public class PlaylistTest {
    @Test
    public void constructorTest1(){
        assertThrows(
            IllegalArgumentException.class,
            new ThrowingRunnable(){
                public void run(){
                    Playlist pl = new Playlist(null, null, null, null, null);
                }
            }
        );
    }
    @Test
    public void constructorTest2(){
        assertThrows(
            IllegalArgumentException.class,
            new ThrowingRunnable(){
                public void run(){
                    Playlist pl = new Playlist(null, null, "me", null, null);
                }
            }
        );
    }
    @Test
    public void constructortest3(){
        assertThrows(
            IllegalArgumentException.class,
            new ThrowingRunnable(){
                public void run(){
                    Playlist pl = new Playlist(null, "le epic playlist", null, null, null);
                }
            }
        );
    }
    @Test
    public void constructortest4(){
        assertThrows(
            IllegalArgumentException.class,
            new ThrowingRunnable(){
                public void run(){
                    Playlist pl = new Playlist(null, "sakdlfkjasdljkblkjashdkjfbambflkjdvbkj.db .fkjvjkadb.fvbl", null, null, null);
                }
            }
        );
    }
    @Test
    public void constructortest5(){
        assertThrows(
            IllegalArgumentException.class,
            new ThrowingRunnable(){
                public void run(){
                    Playlist pl = new Playlist(null, "le epic playlist", "sadflajksdbjfjasdjbvljkabdfvjmabdjklfhvjkabdljkf", null, null);
                }
            }
        );
    }
    @Test
    public void constructorTest6(){
        Playlist pl = new Playlist(null, "my awesome playlist", "Davis", null, null);
        assertNotNull(pl);
    }
    @Test
    public void constructorTest7(){
        ArrayList<Song> songs = new ArrayList<Song>();
        songs.add(new Song(null, "", "", "", Key.AMAJOR_GbMINOR, DifficultyLevel.BEGINNER, Instrument.ACOUSTIC_BASS, new Score(null, Instrument.ACOUSTIC_BASS, 100)));
        Playlist pl = new Playlist(null, "title", "author", null, songs);
        assertEquals(pl.getSongs(), songs);
    }
    @Test
    public void constructorTest8(){
        Playlist pl = new Playlist(null, "", "", "a playlist of my favorite 90s songs", null);
        assertEquals(pl.getDescription(), "a playlist of my favorite 90s songs");
    }
    @Test
    public void constructorTest9(){
        ArrayList<Song> songs = new ArrayList<Song>();
        songs.add(null);
        assertThrows(
            IllegalArgumentException.class,
            new ThrowingRunnable() {
                public void run() throws Throwable{
                    pl = new Playlist(null, "", "", "", songs);
                }
            }
        );
    }
    Playlist pl;
    Song s;
    Song as;
    @Before
    public void initialize(){
        pl = new Playlist(null, "goodTitle", "goodAuthor", "desciption", null);
        s = new Song(null, "", "", "", Key.AMAJOR_GbMINOR, DifficultyLevel.ADVANCED, Instrument.ACOUSTIC_BASS, new Score(null, Instrument.ACOUSTIC_BASS, 100));
        as = new Song(null, "another song", "Chris Christopherson", "phonk", Key.DMAJOR_BMINOR, DifficultyLevel.INTERMEDIATE, Instrument.OVERDRIVEN_GUITAR, new Score(null, Instrument.OVERDRIVEN_GUITAR, 300));
    }
    @Test
    public void editTitleTest1(){
        pl.editTitle("new title");
        assertEquals(pl.getTitle(), "new title");
    }
    @Test
    public void editTitleTest2(){
        pl.editTitle("sdf;lkajsdklfkasdjvkadnfvadfas;kdlfkasdkfhlasdf");
        assertNotEquals(pl.getTitle(), "sdf;lkajsdklfkasdjvkadnfvadfas;kdlfkasdkfhlasdf");
    }
    @Test
    public void editTitleTest3(){
        pl.editTitle(null);
        assertNotNull(pl.getTitle());
    }
    @Test
    public void addSongTest1(){
        pl.addSong(new Song(null, "", "", "", Key.AMAJOR_GbMINOR, DifficultyLevel.ADVANCED, Instrument.ACOUSTIC_BASS, new Score(null, Instrument.ACOUSTIC_BASS, 100)));
        assertEquals(pl.getSongs().size(), 1);
    }
    @Test
    public void addSongTest2(){
        pl.addSong(null);
        assertEquals(pl.getSongs().size(), 0);
    }
    @Test
    public void removeSongTest1(){
        pl.addSong(s);
        pl.removeSong(null);
        assertEquals(pl.getSongs().size(), 1);
    }
    @Test
    public void removeSongTest2(){
        pl.addSong(s);
        pl.removeSong(s);
        assertEquals(pl.getSongs().size(), 0);
    }
    @Test
    public void removeSongTest3(){
        pl.addSong(s);
        pl.removeSong(0);
        assertEquals(pl.getSongs().size(), 0);
    }
    @Test
    public void removeSongTest4(){
        pl.addSong(s);
        pl.removeSong(3);
        assertEquals(pl.getSongs().size(), 1);
    }
    @Test
    public void editDescriptionTest1(){
        pl.editDescription(null);
        assertNotNull(pl.getDescription());
    }
    @Test
    public void editDescriptionTest2(){
        pl.editDescription("new description");
        assertEquals(pl.getDescription(), "new description");
    }
    @Test
    public void editDescriptionTest3(){
        pl.editDescription("");
        assertEquals(pl.getDescription(), "");
    }
    @Test
    public void editDescriptionTest4(){
        pl.editDescription("sadkfhlkjadbjfkvbkjbhadkjfvbjkad. fjkvha.dkjfvkjbsdfk.nv.sdnfkj.vns,dfnvknsdf,.vlksfnd.vn,sdfhlvk sdf,j.vn");
        assertEquals(pl.getDescription(), "sadkfhlkjadbjfkvbkjbhadkjfvbjkad. fjkvha.dkjfvkjbsdfk.nv.sdnfkj.vns,dfnvknsdf,.vlksfnd.vn,sdfhlvk sdf,j.vn");
    }
    @Test
    public void sortByTitleTest1(){
        pl.addSong(s);
        pl.addSong(s);
        pl.sortByTitle();
        ArrayList<Song> songs = pl.getSongs();
        assert(songs.get(0).getTitle().compareTo(songs.get(1).getTitle()) <= 0);
    }
    @Test
    public void sortByTitleTest2(){
        pl.addSong(null);
        pl.addSong(s);
        assertEquals(pl.getSongs().get(0).getTitle(), s.getTitle());
        pl.sortByTitle();
        ArrayList<Song> songs = pl.getSongs();
        assertEquals(pl.getSongs().get(0).getTitle(), s.getTitle());
    }
    @Test
    public void sortByTitleTest3(){
        pl.addSong(null);
        pl.addSong(null);
        pl.sortByTitle();
        assertEquals(pl.getSongs().size(), 0);
    }
    @Test
    public void sortByTitleTest4(){
        pl.addSong(s);
        pl.addSong(as);
        pl.sortByTitle();
        ArrayList<Song> songs = pl.getSongs();
        assert(songs.get(0).getTitle().compareTo(songs.get(1).getTitle()) <= 0);
    }
    @Test
    public void sortByArtistTest1(){
        pl.addSong(s);
        pl.addSong(s);
        pl.sortByArtist();
        ArrayList<Song> songs = pl.getSongs();
        assert(songs.get(0).getArtist().compareTo(songs.get(1).getArtist()) <= 0);
    }
    @Test
    public void sortByArtistTest2(){
        pl.addSong(null);
        pl.addSong(null);
        pl.sortByArtist();
        assertEquals(pl.getSongs().size(), 0);
    }
    @Test
    public void sortByArtistTest3(){
        pl.addSong(null);
        pl.addSong(s);
        assertEquals(pl.getSongs().get(0).getArtist(), s.getArtist());
        pl.sortByArtist();
        ArrayList<Song> songs = pl.getSongs();
        assertEquals(songs.get(0).getTitle(), s.getTitle());
    }
    @Test
    public void sortByArtist4(){
        pl.addSong(s);
        pl.addSong(as);
        pl.sortByArtist();
        ArrayList<Song> songs = pl.getSongs();
        assert(songs.get(0).getArtist().compareTo(songs.get(1).getArtist()) <= 0);
    }
    @Test
    public void sortByGenreTest1(){
        pl.addSong(s);
        pl.addSong(s);
        pl.sortByGenre();
        ArrayList<Song> songs = pl.getSongs();
        assert(songs.get(0).getGenre().compareTo(songs.get(1).getGenre()) <= 0);
    }
    @Test
    public void sortByGenreTest2(){
        pl.addSong(null);
        pl.addSong(null);
        pl.sortByGenre();
        assertEquals(pl.getSongs().size(), 0);
    }
    @Test
    public void sortByGenreTest3(){
        pl.addSong(null);
        pl.addSong(s);
        assertEquals(pl.getSongs().get(0).getGenre(), s.getGenre());
        pl.sortByGenre();
        ArrayList<Song> songs = pl.getSongs();
        assertEquals(songs.get(0).getGenre(), s.getGenre());
    }
    @Test
    public void sortByGenreTest4(){
        pl.addSong(s);
        pl.addSong(as);
        pl.sortByGenre();
        ArrayList<Song> songs = pl.getSongs();
        assert(songs.get(0).getGenre().compareTo(songs.get(1).getGenre()) <= 0);
    }
    @Test
    public void sortByKeyTest1(){
        pl.addSong(s);
        pl.addSong(s);
        pl.sortByKey();
        ArrayList<Song> songs = pl.getSongs();
        assert(songs.get(0).getKey().ordinal() <= songs.get(1).getKey().ordinal());
    }
    @Test
    public void sortByKeyTest2(){
        pl.addSong(null);
        pl.addSong(s);
        pl.sortByKey();
        ArrayList<Song> songs = pl.getSongs();
        assertEquals(songs.get(0).getKey().ordinal(), s.getKey().ordinal());
    }
    @Test
    public void sortByKeyTest3(){
        pl.addSong(null);
        pl.addSong(null);
        pl.sortByKey();
        ArrayList<Song> songs = pl.getSongs();
        assertEquals(songs.size(), 0);
    }
    @Test
    public void sortByKeyTest4(){
        pl.addSong(s);
        pl.addSong(as);
        pl.sortByKey();
        ArrayList<Song> songs = pl.getSongs();
        assert(songs.get(0).getKey().ordinal() <= songs.get(1).getKey().ordinal());
    }
    @Test
    public void sortByDifficultyTest1(){
        pl.addSong(s);
        pl.addSong(s);
        pl.sortByKey();
        ArrayList<Song> songs = pl.getSongs();
        assert(songs.get(0).getDifficultyLevel().ordinal() <= songs.get(1).getDifficultyLevel().ordinal());
    }
    @Test
    public void sortByDifficultyTest2(){
        pl.addSong(null);
        pl.addSong(s);
        pl.sortByKey();
        ArrayList<Song> songs = pl.getSongs();
        assertEquals(songs.get(0).getKey().ordinal(), s.getKey().ordinal());
    }
    @Test
    public void sortByDifficultyTest3(){
        pl.addSong(null);
        pl.addSong(null);
        pl.sortByKey();
        ArrayList<Song> songs = pl.getSongs();
        assertEquals(songs.size(), 0);
    }
    @Test
    public void sortByDifficultyTest4(){
        pl.addSong(s);
        pl.addSong(as);
        pl.sortByKey();
        ArrayList<Song> songs = pl.getSongs();
        assert(songs.get(0).getDifficultyLevel().ordinal() <= songs.get(1).getDifficultyLevel().ordinal());
    }
}
