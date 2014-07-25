import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


@SuppressWarnings("serial")
public class ConsolePanel extends JPanel{
	
	private  JTextArea console = new JTextArea();
	private JScrollPane scrollPane = new JScrollPane(console);
	
	public static final int CONSOLE_AREA_WIDTH = GUIRunner.FRAME_WIDTH - GamePanel.gameAreaWidth;
	public static final int CONSOLE_AREA_HEIGHT = GUIRunner.FRAME_HEIGHT - 400;
	public static final int SCROLL_PANE_WIDTH = 320;
	public static final int SCROLL_PANE_HEIGHT = 150;
	
	
	public ConsolePanel(){

	//  Set up the ScrollPane
	setBorder(
	            BorderFactory.createCompoundBorder( BorderFactory.createRaisedBevelBorder(), BorderFactory.createCompoundBorder( 
	            		BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
	            		"Command Station"), BorderFactory.createEmptyBorder(5,5,5,5))));
	// Set up the console TextArea
	console.setBorder( BorderFactory.createLoweredBevelBorder());
	console.setLineWrap(true);
    console.setWrapStyleWord(true);
    console.setEditable(false);
	
    
	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	scrollPane.setPreferredSize(new Dimension(SCROLL_PANE_WIDTH,SCROLL_PANE_HEIGHT));
    add(scrollPane);
    
    //  Set up the consolePanel
    setBounds(GUIRunner.FRAME_WIDTH - CONSOLE_AREA_WIDTH, 
			GUIRunner.FRAME_HEIGHT - CONSOLE_AREA_HEIGHT, 
			CONSOLE_AREA_WIDTH-5, 
			CONSOLE_AREA_HEIGHT-49);
	}
	
	public void printLine(String string){
		console.append(string + "\n");
		console.setCaretPosition(console.getDocument().getLength() - 1);
	}
	
	public void clearConsole() {
		console.setText("");
	}
	
}
