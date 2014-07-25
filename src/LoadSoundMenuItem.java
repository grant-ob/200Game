import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;


public class LoadSoundMenuItem extends JMenuItem implements ActionListener{
	private Game game;
	private JFileChooser fc = new JFileChooser();
	
	LoadSoundMenuItem(String name, Game game){
		super(name);
		this.game = game;
		
		fc.addChoosableFileFilter(new SoundFilter());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
			
		int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String ext = Utils.getExtension(file);
            
            
            if (ext.equals( Utils.mid )) { 
            	fc.setCurrentDirectory(file);
            	game.setMusic(file.getPath()); 	
            }
            else{
                game.printToConsole("Extension invalid");
            }
            
        }    
        else {
        	game.printToConsole("No file selected");
        }
	}

}
