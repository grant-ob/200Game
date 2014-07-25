import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;


@SuppressWarnings("serial")
public class LoadBGMenuItem extends JMenuItem implements ActionListener{

	private final JFileChooser fc = new JFileChooser();
	private Game game;
	
	LoadBGMenuItem(String name, Game game){
		super(name);
		this.game = game;
		
		fc.addChoosableFileFilter(new ImageFilter());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String ext = Utils.getExtension(file);
            
            
            if (ext.equals( Utils.jpeg) ||
            	ext.equals(Utils.jpg)   ||
            	ext.equals(Utils.gif)   ||
            	ext.equals(Utils.png)   ||
            	ext.equals(Utils.tif)   ||
            	ext.equals(Utils.tiff)) { 
            	
            	fc.setCurrentDirectory(file);
            	try{
            		ImageIcon image = new ImageIcon(file.getPath()); 
            		game.setBackground(image);
            	}catch(Exception err){
            		game.printToConsole("error: Path not found");
            	}
                	
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
