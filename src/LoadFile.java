import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.io.File;
//import javax.swing.JFrame;
import javax.swing.JMenuItem;
//import javax.swing.JScrollPane;
//import javax.swing.JTextPane;
//
//import java.awt.Font;
//import java.io.BufferedReader;
//import java.io.FileReader;
import java.io.IOException;

@SuppressWarnings("serial")
public class LoadFile extends JMenuItem implements ActionListener{
	private String fileString;

	  public LoadFile(String name, String fileString) {
	    super(name); // Create the menu button
	    this.fileString = fileString;
   	  }
		
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + fileString);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
