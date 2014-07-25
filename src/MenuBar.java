import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar{
	private JMenu fileMenu = new JMenu("File");
	private JMenu settingsMenu = new JMenu("Settings");
	private JMenu helptMenu = new JMenu("Help");
	private JMenu customizeMenu = new JMenu("Customize");
	private JMenu themes = new JMenu("Change Theme");
	private Game game;
	
	public MenuBar(Game game){
		add(fileMenu);
		add(settingsMenu);
		add(helptMenu);
		this.game = game;
		
		init();	
	}
	
	private void init(){
		JMenuItem closeAction = new CloseMenuItem("Close");
		JMenuItem musicAction = new toggleMusicMenuItem("Start/Stop music", game);
		JMenuItem loadAction = new LoadMenuItem("Load new Back of Card Image", game);
		JMenuItem loadBGAction = new LoadBGMenuItem("Load new background", game);
		JMenuItem loadSoundAction = new LoadSoundMenuItem("Load new background music", game);
		JMenuItem helpAction = new LoadHelpFile("Help");
		
		fileMenu.add(closeAction);
		settingsMenu.add(musicAction);
		settingsMenu.add(themes);
		settingsMenu.add(customizeMenu);
		helptMenu.add(helpAction);
		
		generateTheme("Christmas", "/resource/falling snow.gif", "/resource/snowman.jpg", "/resource/1st_noel.mid");
		generateTheme("Digimon", "/resource/Digimon.jpg", "/resource/agumon.gif", "/resource/Digimon.mid");
		generateTheme("Felt Table", "/resource/cardTablebg.jpg", "/resource/cards/NONENONE.gif", "/resource/I_Walk_The_Line.mid");
		generateTheme("Friday", "/resource/friday.gif", "/resource/rebecca-black.jpg", "/resource/friday.mid");
		generateTheme("Pokemon", "/resource/pokemon.jpg", "/resource/Pokemon-Card.JPG", "/resource/Pokemon.mid");
		generateTheme("Ragtime", "/resource/ragtime.gif", "/resource/Rag.jpg", "/resource/entertainer.mid");
		generateTheme("Sesame Street", "/resource/SesameStreet.jpg", "/resource/Grover.gif", "/resource/SesameStreet.mid");
		generateTheme("Space", "/resource/travelling-through-space.gif", "/resource/vader.jpg", "/resource/starWars.mid");
		generateTheme("Vegas", "/resource/vegas.gif", "/resource/vegas.jpg", "/resource/vivavegas.mid");
		generateTheme("Wood", "/resource/Wood-Background.jpg", "/resource/cards/NONENONE.gif", "/resource/The_Gambler.mid");
		customizeMenu.add(loadAction);
		customizeMenu.add(loadBGAction);
		customizeMenu.add(loadSoundAction);
		
		closeAction.addActionListener( (ActionListener) closeAction);
		loadBGAction.addActionListener( (ActionListener) loadBGAction);
		loadAction.addActionListener( (ActionListener) loadAction );
		musicAction.addActionListener( (ActionListener) musicAction);
		helpAction.addActionListener( (ActionListener) helpAction);
		loadSoundAction.addActionListener((ActionListener) loadSoundAction);
		}
	
	private void generateTheme(String name, String bg, String cardBack, String soundPath){

		java.net.URL imageURL1 = this.getClass().getResource( bg );
		java.net.URL imageURL2 = this.getClass().getResource( cardBack );
		//String sound = this.getClass().getResource( soundPath ).getPath();

		JMenuItem newTheme = new ThemeMenuItem( name, imageURL1, imageURL2, soundPath, game);
		
		newTheme.addActionListener((ActionListener) newTheme);
		themes.add(newTheme);
		
	}

}
