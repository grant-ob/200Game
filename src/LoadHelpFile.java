import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import java.io.IOException;
import java.net.URL;

@SuppressWarnings("serial")
public class LoadHelpFile extends JMenuItem implements ActionListener{

//	private JFrame frame;
//	private JTextPane textarea; // The area to display the file contents into
//	private JScrollPane scrollPane;

	  public LoadHelpFile(String name) {
	    super(name); 
   	  }
		
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			URL helpFile = this.getClass().getResource("/resource/Help.pdf");
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + helpFile.toString());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

/*		if(frame != null){
			frame.dispose();
		}
		frame = new JFrame("Help");
		textarea = new JTextPane();
		scrollPane = new JScrollPane(textarea);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(200, 200, 700, 600);
		frame.setVisible(true);
		
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    
	    textarea.setFont(new Font("SansSerif", Font.PLAIN, 15));
	    textarea.setEditable(false);
	    
	    frame.add(scrollPane);
	    setVisible(true);

		 File f;
	    BufferedReader in = null;
	    // Read and display the file contents. Since we're reading text, we
	    // use a FileReader instead of a FileInputStream.
	    f = new File(); // Create a file object
	    	
	    try {
	      in = new BufferedReader(new FileReader (f.getPath())); // And a char stream to read it
	      textarea.setText(""); // Clear the text area
	      textarea.read(in, null);
	      textarea.setFocusable(true);
	    }
	    // Display messages if something goes wrong
	    catch (IOException e1) {
	      System.out.print(e1.getClass().getName() + ": " + e1.getMessage());
	      this.setName("FileViewer: " + f.getName() + ": I/O Exception");
	    }
	    // Always be sure to close the input stream!
*/
	}
	
}
