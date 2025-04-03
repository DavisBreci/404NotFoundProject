package model;

import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import com.model.*;

/**
 * @author Ryan Mazzella
 */
public class DataLoaderTest extends DataConstants {
    /*
     * REMINDER: SWITCH TO THE JSON FILE APPROPRIATE FOR EACH TEST
     * Test JSON files are located in JSON/DataLoaderTest/
     */
    
    //getUsers loads data correctly (users_Complete.json)
    @Test
    public void testGetUsersReturnsFormattedData() {
        ArrayList<User> users = DataLoader.getUsers(); 
        assertEquals(3, users.size());
    }

    //getUsers handles empty file (users_Empty.json)
    @Test
    public void testGetUsersHandlesEmptyData() {
        ArrayList<User> users = DataLoader.getUsers();
        assertTrue(users.isEmpty());
    }

    //getUsers handles corrupt data (users_Corrupt.json)
    @Test
    public void testGetUsersHandlesCorruptData() {
        ArrayList<User> users = DataLoader.getUsers();
        assertTrue(users.isEmpty());
    }
    


    //getUserFromID loads correct user (users_Complete.json)
    @Test
    public void testGetUserFromIDReturnsCorrect() {
        User user = DataLoader.getUserFromID("d22d8a52-6b53-4508-b3e6-7c1e5d51fc1c");
        assertEquals("d22d8a52-6b53-4508-b3e6-7c1e5d51fc1c", user.id);
    }

    //getUserFromID correctly handles invalid ID (users_Complete.json)
    @Test
    public static void testGetUserFromIDHandlesInvalidID() {
        User user = DataLoader.getUserFromID("invalidUserID");
        assertNull(user);
    }

    //getUserFromID handles empty file (users_Empty.json)
    @Test
    public static void testGetUserFromIDHandlesEmptyData() {
        User user = DataLoader.getUserFromID("d22d8a52-6b53-4508-b3e6-7c1e5d51fc1c");
        assertNull(user);
    }

    //getUserFromID handles corrupt file (users_Corrupt.json)
    @Test
    public static void testGetUserFromIDHandlesCorruptData() {
        User user = DataLoader.getUserFromID("d22d8a52-6b53-4508-b3e6-7c1e5d51fc1c");
        assertNull(user);
    }



    //getTeachers loads data correctly (teachers_Complete.json)
    @Test
    public static void testGetTeachersReturnsFormattedData() {
        ArrayList<Teacher> teachers = DataLoader.getTeachers(); 
        assertEquals(1, teachers.size());
    }

    //getTeachers handles empty file (teachers_Empty.json)
    @Test
    public static void testTeachersUsersHandlesEmptyData() {
        ArrayList<Teacher> teachers = DataLoader.getTeachers();
        assertTrue(teachers.isEmpty());
    }

    //getTeachers handles corrupt data (teachers_Corrupt.json)
    @Test
    public static void testGetTeachersHandlesCorruptData() {
        ArrayList<Teacher> teachers = DataLoader.getTeachers();
        assertTrue(teachers.isEmpty());
    }



    //getAllSongs loads data correctly (songs_Complete.json)
    @Test
    public void testGetAllSongsReturnsFormattedData() {
        ArrayList<Song> songs = DataLoader.getAllSongs();
        assertEquals(2, songs.size());
    }

    //getAllSongs handles empty file (songs_Empty.json)
    @Test
    public void testGetAllSongsHandlesEmptyData() {
        ArrayList<Song> songs = DataLoader.getAllSongs();
        assertTrue(songs.isEmpty());
    }

    //getAllSongs handles corrupt data (songs_Corrupt.json)
    @Test
    public void testGetAllSongsHandlesCorruptData() {
        ArrayList<Song> songs = DataLoader.getAllSongs();
        assertTrue(songs.isEmpty());
    }



    //getSongFromID loads correct song (songs_Complete.json)
    @Test
    public void testGetSongFromIDReturnsCorrect() {
        Song song = DataLoader.getSongFromID("8a8e7e25-b701-479d-867c-54cab7eccb92");
        assertEquals("8a8e7e25-b701-479d-867c-54cab7eccb92", song.id);
    }

    //getSongFromID correctly handles invalid ID (songs_Complete.json)
    @Test
    public static void testGetSongFromIDHandlesInvalidID() {
        Song song = DataLoader.getSongFromID("invalidSongID");
        assertNull(song);
    }

    //getSongFromID handles empty file (songs_Empty.json)
    @Test
    public static void testGetSongFromIDHandlesEmptyData() {
        Song song = DataLoader.getSongFromID("8a8e7e25-b701-479d-867c-54cab7eccb92");
        assertNull(song);
    }

    //getSongFromID handles corrupt file (songs_Corrupt.json)
    @Test
    public static void testGetSongFromIDHandlesCorruptData() {
        Song song = DataLoader.getSongFromID("8a8e7e25-b701-479d-867c-54cab7eccb92");
        assertNull(song);
    }



    //getScoreFromID loads correct score (scores_Complete.json)
    @Test
    public void testGetScoreFromIDReturnsCorrect() {
        Score score = DataLoader.getScoreFromID("a42d710f-afcb-4bce-bfd7-ecb43e6a5a89");
        assertEquals("a42d710f-afcb-4bce-bfd7-ecb43e6a5a89", score.id);
    }

    //getScoreFromID correctly handles invalid ID (scores_Complete.json)
    @Test
    public static void testGetScoreFromIDHandlesInvalidID() {
        Score score = DataLoader.getScoreFromID("invalidScoreID");
        assertNull(score);
    }

    //getScoreFromID handles empty file (scores_Empty.json)
    @Test
    public static void testGetScoreFromIDHandlesEmptyData() {
        Score score = DataLoader.getScoreFromID("a42d710f-afcb-4bce-bfd7-ecb43e6a5a89");
        assertNull(score);
    }

    //getScoreFromID handles corrupt file (scores_Corrupt.json)
    @Test
    public static void testGetScoreFromIDHandlesCorruptData() {
        Score score = DataLoader.getScoreFromID("a42d710f-afcb-4bce-bfd7-ecb43e6a5a89");
        assertNull(score);
    }



    //getAllPlaylists loads data correctly (playlists_Complete.json)
    @Test
    public void testGetAllPlaylistsReturnsFormattedData() {
        ArrayList<Playlist> playlists = DataLoader.getAllPlaylists();
        assertEquals(2, playlists.size());
    }

    //getAllPlaylists handles empty file (playlists_Empty.json)
    @Test
    public void testGetAllPlaylistsHandlesEmptyData() {
        ArrayList<Playlist> playlists = DataLoader.getAllPlaylists();
        assertTrue(playlists.isEmpty());
    }

    //getAllPlaylists handles corrupt data (playlists_Corrupt.json)
    @Test
    public void testGetAllPlaylistsHandlesCorruptData() {
        ArrayList<Playlist> playlists = DataLoader.getAllPlaylists();
        assertTrue(playlists.isEmpty());
    }
    


    //getPlaylistFromID loads correct playlist (playlists_Complete.json)
    @Test
    public void testGetPlaylistFromIDReturnsCorrect() {
        Playlist playlist = DataLoader.getPlaylistFromID("39a48119-e368-40a9-9d4e-c10139338e7c");
        assertEquals("39a48119-e368-40a9-9d4e-c10139338e7c", playlist.id);
    }

    //getPlaylistFromID correctly handles invalid ID (playlists_Complete.json)
    @Test
    public static void testGetPlaylistFromIDHandlesInvalidID() {
        Playlist playlist = DataLoader.getPlaylistFromID("invalidScoreID");
        assertNull(playlist);
    }

    //getPlaylistFromID handles empty file (playlists_Empty.json)
    @Test
    public static void testGetPlaylistFromIDHandlesEmptyData() {
        Playlist playlist = DataLoader.getPlaylistFromID("39a48119-e368-40a9-9d4e-c10139338e7c");
        assertNull(playlist);
    }

    //getPlaylistFromID handles corrupt file (playlists_Corrupt.json)
    @Test
    public static void testGetPlaylistFromIDHandlesCorruptData() {
        Playlist playlist = DataLoader.getPlaylistFromID("39a48119-e368-40a9-9d4e-c10139338e7c");
        assertNull(playlist);
    }



    //getAllLessons loads data correctly (lessons_Complete.json)
    @Test
    public void testGetAllLessonsReturnsFormattedData() {
        ArrayList<Lesson> lessons = DataLoader.getAllLessons();
        assertEquals(15, lessons.size());
    }

    //getAllLessons handles empty file (lessons_Empty.json)
    @Test
    public void testGetAllLessonsHandlesEmptyData() {
        ArrayList<Lesson> lessons = DataLoader.getAllLessons();
        assertTrue(lessons.isEmpty());
    }

    //getAllLessons handles corrupt data (lessons_Corrupt.json)
    @Test
    public void testGetAllLessonsHandlesCorruptData() {
        ArrayList<Lesson> lessons = DataLoader.getAllLessons();
        assertTrue(lessons.isEmpty());
    }



    //getLessonFromID loads correct playlist (lessons_Complete.json)
    @Test
    public void testGetLessonFromIDReturnsCorrect() {
        Lesson lesson = DataLoader.getLessonFromID("61d2e47a-a8f9-41b2-88c3-1656a626c433");
        assertEquals("61d2e47a-a8f9-41b2-88c3-1656a626c433", lesson.id);
    }

    //getLessonFromID correctly handles invalid ID (lessons_Complete.json)
    @Test
    public static void testGetLessonFromIDHandlesInvalidID() {
        Lesson lesson = DataLoader.getLessonFromID("invalidScoreID");
        assertNull(lesson);
    }

    //getLessonFromID handles empty file (lessons_Empty.json)
    @Test
    public static void testGetLessonFromIDHandlesEmptyData() {
        Lesson lesson = DataLoader.getLessonFromID("61d2e47a-a8f9-41b2-88c3-1656a626c433");
        assertNull(lesson);
    }

    //getLessonFromID handles corrupt file (lessons_Corrupt.json)
    @Test
    public static void testGetLessonFromIDHandlesCorruptData() {
        Lesson lesson = DataLoader.getLessonFromID("61d2e47a-a8f9-41b2-88c3-1656a626c433");
        assertNull(lesson);
    }
}