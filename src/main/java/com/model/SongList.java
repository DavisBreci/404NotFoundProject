import java.util.ArrayList;

public class SongList {
    
    private static SongList songList;
    private ArrayList<Song> songs;

    private SongList() {
        songs = new ArrayList<>();
    }

    public static SongList getInstance() {
        if(songList == null) {
            songList = new SongList();
        }

        return songList;
    }

    public ArrayList<Song> getSongList() {
        return songs;
    }

    public boolean loadSong(Song s) {
        return true;
    }

    public Song getSongByTitle(String name) {
        return null;
    }

    public ArrayList<Song> getSongsByDifficulty(DifficultyLevel dv) {
        return null;
    }

    public ArrayList<Song> getSongsByKey(Key k) {
        return null;
    }

    public ArrayList<Song> getSongsByGenre(String genre) {
        return null;
    }

    public void addSong(Song s) {
        songs.add(s);
    }
}
