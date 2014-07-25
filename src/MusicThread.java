import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;


public class MusicThread extends Thread {
	
	private Sound sound;
	private boolean playing = false;
	
	public boolean isPlaying(){
		return playing;
	}
	
	public void startMusic(){
		sound.play();
		playing = true;
	}
	
	public void stopMusic(){
		sound.stop();
		playing = false;
	}
	
	public void setMusic(String filePath){
		sound.setSequence(filePath);
	}
	
	
	public void run() {		
		try {
			sound = new Sound();
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
