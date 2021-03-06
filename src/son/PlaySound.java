package son;

import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

/**
 * Internal class for threading
 * @author Alexandre Chanson
 */
class PlaySound implements Runnable{

    private final static int BUFFER_SIZE = 128000;
    private volatile boolean stop = false, paused = false;
    private AudioInputStream audioStream;
    private String name;

    PlaySound(AudioInputStream audioStream, String soundName) {
        this.audioStream = audioStream;
        this.name = soundName;
    }

    @Override
    public void run() {
        if(audioStream == null)
            return;

        AudioFormat audioFormat = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        SourceDataLine sourceDataLine;

        try {
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceDataLine.open(audioFormat);
        } catch (Exception e) {
            return;
        }

        sourceDataLine.start();

        int bytesRead = 0;
        byte[] bytes = new byte[BUFFER_SIZE];

        while (bytesRead != -1) {
            if (stop)
                break;
            if(!paused){
                try {
                    bytesRead = audioStream.read(bytes, 0, bytes.length);
                } catch (IOException ignored) {
                    //Should not happen as we are reading from memory
                    ignored.printStackTrace();
                }
                if (bytesRead >= 0)
                    sourceDataLine.write(bytes, 0, bytesRead);
            }else {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        sourceDataLine.drain();
        sourceDataLine.close();

        SoundPlayer.unregister(name);
    }

    void stop(){
        stop = true;
    }

    void pause(){
        paused = true;
    }

    void resume(){
        paused = false;
    }
}