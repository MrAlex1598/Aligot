package son;

import javax.sound.sampled.*;
import java.io.IOException;

/**
 * internal class for threading
 * @author Alexandre Chanson
 */
class LoopedSound implements Runnable {
    private final static int BUFFER_SIZE = 128000;
    private volatile boolean stop = false, timedStop = false;
    private AudioInputStream audioStream;
    private String name;

    LoopedSound(AudioInputStream audioStream, String soundName) {
        this.audioStream = audioStream;
        this.name = soundName;
    }

    @Override
    public void run() {
        if (audioStream == null)
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
            try {
                if (audioStream != null) {
                    bytesRead = audioStream.read(bytes, 0, bytes.length);
                }else
                    break;
            } catch (IOException ignored) {
                //Should not happen as we are reading from memory
            }
            if (bytesRead >= 0)
                sourceDataLine.write(bytes, 0, bytesRead);
            else{
                if (timedStop)
                    break;
                bytesRead = 0;
                bytes = new byte[BUFFER_SIZE];
                audioStream = SoundBank.get(name);
            }
        }

        sourceDataLine.drain();
        sourceDataLine.close();

        SoundPlayer.unregister(name);
    }

    void stop() {
        stop = true;
    }

    void timedStop(){
        timedStop = true;
    }

}
