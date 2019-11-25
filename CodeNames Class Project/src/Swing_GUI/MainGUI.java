package Swing_GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import code.Board;

//import sun.audio.*;
//import sun.audio.AudioPlayer;
//import sun.audio.AudioStream;
import java.applet.*;

public class MainGUI implements Runnable {
	public Board gameBoard;
	
	public JPanel codeNamesPanel;
	public JPanel spyMasterPanel;
	
	public static JFrame codeNames;
	public static JFrame spyMaster;

	/**
	 * @info  Creates New GUI Objects for Both the SpyMaster and CodeName Panel
	 */
	public MainGUI(Board Board) {
		gameBoard = Board;
		codeNames = new JFrame("Swing " +" CodeNames");
		spyMaster = new JFrame("Swing" + " SpyMaster");	
		Container color = codeNames.getContentPane();
		color.setBackground(Color.BLACK);
		codeNames.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		spyMaster.setBounds(50, 400, 900, 500);
	}
	
	/**
	 * @info run() Responsible for Both The Code Name and Spymaster Panel and gives Them their initial size
	 */
	@Override
	public void run() {
		codeNames.setSize(800, 400);
		spyMaster.setSize(800, 800);
		Image icon = Toolkit.getDefaultToolkit().getImage("CN.PNG");
		codeNames.setIconImage(icon);
		codeNamesPanel = new JPanel();
		spyMasterPanel = new JPanel();
		codeNames.getContentPane().add(codeNamesPanel);
		spyMaster.getContentPane().add(spyMasterPanel);	
		CodeNames codeNamesGUI = new CodeNames(gameBoard, codeNamesPanel);
		new SpyMaster(gameBoard, codeNamesGUI, spyMasterPanel);
		spyMasterTurn();
		codeNames.pack();
		spyMaster.pack();
		codeNames.setVisible(true);
		spyMaster.setVisible(true);
		codeNames.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		spyMaster.setLocation(300, 300);
		codeNames.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		spyMaster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	
	/**
	 * @info Lets Red Agents Know it's there turn at the start of the game
	 */
	public void spyMasterTurn() {
		if(gameBoard.currentTurn()  == 0)
			
			JOptionPane.showMessageDialog(null, "Red Squad : It's Your Turn");
	}
	
	/**
	 * 
	 * @param filePath
	 * Responsible for The Easter Egg "The Music That Plays During The Game
	 */
	public static void PlayMusic(String filePath) {
		
		
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"The Music File Cannot Be Found");
		}
		
	}
	

		public static void DelayTimer(int Seconds, String filePath) {
			Timer T = new Timer(555, new ActionListener(){
				
							@Override
							public void actionPerformed(ActionEvent arg0) {
								// TODO Auto-generafgfaated method stub
								PlayMusic(filePath);
							}
							
						});
		}
	/**
	 * @info Responsible for The Main Menu.
	 */
	public static void main(String[] args) {
		String[] options = new String[] {"2 Player Mode", "3 Player Mode"};
	    int gameMode = -1;
	    Boolean threePlayerGame = false;
	    
	    
	    do{
	    	
	    gameMode = JOptionPane.showOptionDialog(null, "Please Select Mode For Game", "Game Mode", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
	    }while(gameMode < 0 || gameMode > 1);
		
	    if(gameMode == 1){
	    	threePlayerGame = true;
	    	PlayMusic("Colors.wav");
	    	//DelayTimer(Seconds, filePath);
	    	
	    }else{
	    	threePlayerGame = false;
	    	PlayMusic("Rewrite.wav");
	    	//DelayTimer(gameMode, null);
	    }
	    		Board gameBoard = new Board(threePlayerGame);
		SwingUtilities.invokeLater(new MainGUI(gameBoard));

		}
}