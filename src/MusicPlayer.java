import javax.sound.sampled.*;  //for handling audio files
import java.io.File;            // used to read song file from local system

public class MusicPlayer {
    private Clip clip;     // Clip is an interface in java used to handle audio playback
    private AudioInputStream audioStream;  // AudioInputStream is a java class that reads audio data from a file or input stream
    private long pausePosition = 0;  // stores time at which song is paused
    private boolean isPlaying = false;  // check if song is playing
    private boolean isPaused = false;  // check if song is paused

    public MusicPlayer() {
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public void play(Song song) {
        try {
            //gets file path of song
            File file = new File(song.getFilePath());
            //checks if song exists
            if (!file.exists()) {
                System.out.println("File not found: " + song.getFilePath());
                return;
            }

            //loads audio file
            audioStream = AudioSystem.getAudioInputStream(file);
            //creates an audio clip an opens it. clip allows control over sound playback
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            //starts playing song
            clip.start();
            isPlaying = true;
            isPaused = false;
            System.out.println("Playing: " + song.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        //checks if song is playing
        if (clip != null && isPlaying) {
            //saves current playback position where the song is stopped
            pausePosition = clip.getMicrosecondPosition();
            //stops the clip without resetting the position
            clip.stop();
            isPaused = true;
            isPlaying = false;
            System.out.println("Song paused.");
        }
    }

    public void resume() {
        //checks if a song is paused
        if (clip != null && isPaused) {
            //restores playback position and resumes form exactly where it was  paused
            clip.setMicrosecondPosition(pausePosition);
            // starts playing the song again
            clip.start();
            isPlaying = true;
            isPaused = false;
            System.out.println("Resuming song...");
        }
    }

    public void stop() {
        //checks if clip exists
        if (clip != null) {
            //stops the audio
            clip.stop();
            // releases system resources used by the audio clip
            clip.close();
            //resets status variables
            isPlaying = false;
            isPaused = false;
            pausePosition = 0;
            System.out.println("Playback stopped.");
        }
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}

