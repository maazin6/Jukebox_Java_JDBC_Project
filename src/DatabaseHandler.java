import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class DatabaseHandler {
    private static final String DB_URL = "jdbc:mysql://Localhost:3306/musicplayerdb";
    private static final String DB_USER = "root";  // Change this
    private static final String DB_PASSWORD = "2002";  // Change this

    public List<Song> fetchSongs() {
        List<Song> songs = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM songs")) {

            while (rs.next()) {
                songs.add(new Song(rs.getInt("song_id"), rs.getString("song_name"), rs.getString("song_file_location")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }
}
