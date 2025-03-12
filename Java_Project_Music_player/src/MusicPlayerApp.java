import java.util.List;
import java.util.Scanner;

public class MusicPlayerApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DatabaseHandler dbHandler = new DatabaseHandler();
        MusicPlayer musicPlayer = new MusicPlayer();

        while (true) {
            System.out.println("\n=== Music Player Menu ===");
            System.out.println("1. Play Song");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    List<Song> songs = dbHandler.fetchSongs();
                    if (songs.isEmpty()) {
                        System.out.println("No songs available.");
                        break;
                    }

                    System.out.println("\nAvailable Songs:");
                    for (Song song : songs) {
                        System.out.println(song.getId() + ". " + song.getName());
                    }

                    System.out.print("\nEnter the song ID to play: ");
                    int songId = scanner.nextInt();
                    scanner.nextLine();

                    Song selectedSong = songs.stream().filter(s -> s.getId() == songId).findFirst().orElse(null);
                    if (selectedSong == null) {
                        System.out.println("Invalid song ID.");
                        break;
                    }

                    musicPlayer.play(selectedSong);
                    showInSongMenu(scanner, musicPlayer);
                    break;

                case 2:
                    System.out.println("Exiting Music Player. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        }
    }

    // In-Song Menu
    private static void showInSongMenu(Scanner scanner, MusicPlayer musicPlayer) {
        while (musicPlayer.isPlaying() || musicPlayer.isPaused()) {
            System.out.println("\n=== Song Controls ===");
            System.out.println("1. Pause");
            System.out.println("2. Resume");
            System.out.println("3. Stop");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    musicPlayer.pause();
                    break;
                case 2:
                    musicPlayer.resume();
                    break;
                case 3:
                    musicPlayer.stop();
                    return;
                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        }
    }
}
