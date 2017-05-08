package View;
import java.awt.BorderLayout;
import javax.swing.*;

import Controller.ConnectFourController;
import Model.ConnectFourModel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConnectFourJframe extends JFrame {
	
	/* 
	 *  Represents the board.
	 */
	private ImageIcon gameBoard;
	
	/*
	 *  Restart button.
	 */
	
	private JButton restartButton;
	
	/*
	 *  Restart icon.
	 */
	
	private ImageIcon restartIcon;
	
	/* 
	 *  Yellow marker. 
	 */
	
	private ImageIcon yellowMarker;
	
	/*
	 *  Red marker.
	 */
	
	private ImageIcon redMarker;
	
	/* 
	 *  Represents current marker, red or yellow.
	 */
	private ImageIcon currentMarker;
	
	/*
	 *  Represents the winning move.
	 */
	
	private ImageIcon winningMove;
	
	/*
	 *  The font of the text that shows when someone wins.
	 */
	
	private Font font;
	
	/*
	 *  Choose player name
	 */
	
	private JTextField playerName; 
	private JTextField playerName2; 
	private JButton btnSelectPlayerName;
	private JButton btnSelectPlayerName2;
	
	public ConnectFourJframe() {

		this.gameBoard = new ImageIcon ("Images/ConnectFour.png");
		this.yellowMarker = new ImageIcon("Images/Marker1.png");
		this.redMarker = new ImageIcon("Images/Marker2.png");
		this.winningMove = new ImageIcon("Images/WinnerMove.png");
		this.restartIcon = new ImageIcon("Images/RestartButton.png");
		this.restartButton = new JButton (this.restartIcon);
		
	}
	
	public JTextField getPlayerName(){
		return this.playerName;
	}
	
	public void setPlayerName(String name){
		this.playerName = playerName; //new
	}
	

	public JTextField getPlayerName2(){
		return this.playerName2;
	}
	
	public void setPlayerName2(String name2){
		this.playerName2 = playerName2; //new
	}
	// new
	public void addSelectPlayerListener(ActionListener e){
		this.btnSelectPlayerName.addActionListener(e);
	}
	
	/*ny
	public void addSelectPlayerListener2(ActionListener e){
		this.btnSelectPlayerName2.addActionListener(e);
	}
	 */
	public void addRestartButtonListener(ActionListener l) {
		this.restartButton.addActionListener(l);
	}
	
	public void addPanel(JPanel p) {
		getContentPane().add(p);
	}
	
	public JButton getRestartButton() {
		return this.restartButton;
	}
	
	public ImageIcon getRestartIcon() {
		return this.restartIcon;
	}
	
	public ImageIcon getYellowMarker (){
		return this.yellowMarker;
	}
	
	public ImageIcon getRedMarker (){
		return this.redMarker;
	}
	
	public ImageIcon getWinningMove(){
		return this.winningMove;
	}
	
	public ImageIcon getCurrentMarker() {
		return this.currentMarker;
	}
	
	public void setCurrentMarker(ImageIcon icon){
		this.currentMarker = icon;
	}
	
	public Font getFont(){
		return this.font;
	}
	
	public void setFont(Font font){
		this.font = font;
	}
	
	public ImageIcon getGameBoard(){
		return this.gameBoard;
	}
	
	public static void main(String[] args) {
		
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectFourJframe frame = new ConnectFourJframe();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		*/
		
		ConnectFourModel model = new ConnectFourModel();
		ConnectFourJframe view = new ConnectFourJframe();
		ConnectFourController controller = new ConnectFourController(view, model);
		
	}
}
