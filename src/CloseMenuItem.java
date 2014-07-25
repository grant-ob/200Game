import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;


@SuppressWarnings("serial")
public class CloseMenuItem extends JMenuItem implements ActionListener {
	
	CloseMenuItem(String name){
		super(name);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}
}
