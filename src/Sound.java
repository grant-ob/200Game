import java.io.*;

import javax.sound.sampled.*;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;



public class Sound {

	private Sequence sequence;
	private Sequencer sequencer;

Sound() throws InvalidMidiDataException, IOException, MidiUnavailableException{
	// From file
    sequence = MidiSystem.getSequence(this.getClass().getResource("resource/The_Gambler.mid"));

 // Create a sequencer for the sequence
    sequencer = MidiSystem.getSequencer();
    sequencer.open();
}

public void play() {
  if (sequencer != null && sequence != null && sequencer.isOpen()) {
    try {
      sequencer.setSequence(sequence);
      sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
      sequencer.start();
    } catch (InvalidMidiDataException ex) {
      ex.printStackTrace();
    }
  }
}

public void stop(){
	if (sequencer != null && sequencer.isOpen()) {
	      sequencer.stop();
	}
}

public void setSequence(String filePath){
	 File file = new File(filePath); 
	 if(!file.exists() || file.isDirectory() || !file.canRead())
	 {
	    System.out.println("Error reading file");
	 } 
	 
	try{
		Sequence newSequence = MidiSystem.getSequence(this.getClass().getResource(filePath));
		sequencer.setSequence(newSequence);
		sequence = newSequence;
	}catch(InvalidMidiDataException e){
		System.out.println("Invalid midi!");
	} catch (IOException e) {
		System.out.println("Sound error");
		e.printStackTrace();
	}
	
}

public void setSequence(Sequence sound){
	try {
		sequencer.setSequence(sound);
		sequence = sound;
	} catch (InvalidMidiDataException e) {
		e.printStackTrace();
	}
	
		
	
}



}