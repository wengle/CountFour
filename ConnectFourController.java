package Controller;

	import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
	import java.awt.Font;
	import java.awt.Graphics;
	import java.awt.Point;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.MouseEvent;
	import java.awt.event.MouseListener;
	import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
	import javax.swing.WindowConstants;

import Model.ConnectFourModel;
import Model.Players;
import View.ConnectFourJframe;

	public class ConnectFourController implements ActionListener {
		/**
		 * Represents the view or what the user sees.
		 */
		private final ConnectFourJframe view;
		
		/**
		 * Represents the model or the data for the game.
		 */
		private final ConnectFourModel model;
		
		/**
		 * Initializes a new instance of ConnectFourController with the specified view and model.
		 * Setups up the game window.
		 * @param view
		 * @param model
		 */
		public ConnectFourController(final ConnectFourJframe view, final ConnectFourModel model) {
			this.view = view;
			this.model = model;
			
			int frameWidth = 2 * this.model.getMargin() + this.model.getCols() * this.model.getTileSize();
			int frameHeight = 3 * this.model.getMargin() + this.model.getRows() * this.model.getTileSize();
			
			//Setup Frame
			this.view.getContentPane().setPreferredSize(new Dimension(frameWidth, frameHeight));
			this.view.setTitle("Connect Four");
			this.view.pack();
			this.view.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			this.view.setLocationRelativeTo(null);
			this.view.setResizable(false);
			
			int buttonWidth = 4 * this.model.getMargin();
			int buttonHeight = this.model.getMargin() / 1;
									
			//Setup player 1
			Players p1 = new Players();
			JTextField playerName = new JTextField();
			this.view.setPlayerName(playerName.getText());
			playerName.setBounds(0, 0, buttonWidth, buttonHeight);
			view.getContentPane().add(playerName, BorderLayout.NORTH);
			playerName.setColumns(10);
			
			JButton btnSelectPlayerName = new JButton("Select name player 1");
			btnSelectPlayerName.setBounds(285, 6, 152, 29);
			this.view.getContentPane().add(btnSelectPlayerName);
			
			//Setup player 2
			Players p2 = new Players();
			JTextField playerName2 = new JTextField();
			this.view.setPlayerName(playerName2.getText()); //new
			playerName2.setBounds(10, 10, buttonWidth, buttonHeight);
			view.getContentPane().add(playerName2, BorderLayout.NORTH);
			playerName2.setColumns(10);
			
			/*JButton btnSelectPlayerName2 = new JButton("Select name player 2");
			btnSelectPlayerName.setBounds(450, 6, 152, 29);
			this.view.getContentPane().add(btnSelectPlayerName2);
			 */
			
			//Setup Restart Button
			JPanel restartPanel = new JPanel();
			this.view.getRestartButton().setPreferredSize(new Dimension(buttonWidth, buttonHeight));
			restartPanel.add(this.view.getRestartButton(), BorderLayout.CENTER);
			this.view.add(restartPanel, BorderLayout.PAGE_END);
			
			//Setup Font
			this.view.setFont(new Font("Times New Roman", Font.BOLD, this.model.getFontSize()));
			this.view.addRestartButtonListener(new RestartButtonListener());
			this.view.addMouseListener(new PanelListener());
			this.view.addMouseMotionListener(new CursorListener());
			this.view.addPanel(new Panel());
			//this.view.addSelectPlayerListener(new SelectPlayerListener()); // new
			this.view.setVisible(true);
		}	

		class Panel extends JPanel {

			@Override
			/**
			 * Responsible for updating the view.
			 * Draws the dropping marker if a timer is running and then draws the game board
			 * to simulate the marker dropping inside the game board.
			 * Draws a marker which follows the user's cursor if no timer is running.
			 * Draws the win sequence when a Connect Four is found. 
			 */
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				//Set color of dropping marker to be the current players color
				if (model.getCurrentColor() == model.RED) {
					view.setCurrentMarker(view.getRedMarker());
				} else {
					view.setCurrentMarker(view.getYellowMarker());
				}
				
				if (model.getTimer().isRunning()) {
					//Draw falling marker
					g.drawImage (view.getCurrentMarker().getImage(), 
							model.getMargin() + model.getDroppingDisc().getX(),
							model.getDroppingDisc().getY(), 
							model.getTileSize(), 
							model.getTileSize(), 
							null);
				} else {
					//Draw marker that follows mouse cursor at top
					if (!model.getWinSequence())
						g.drawImage (view.getCurrentMarker().getImage(), 
								model.getMousePoint().x - (model.getTileSize() / 2),
								0, 
								model.getTileSize(), 
								model.getTileSize(), 
								null);
				}
				
				for (int i = 0; i < model.getRows(); i++) {
					for (int j = 0; j < model.getCols(); j++) {
						if (model.getGameBoard()[i][j]== model.RED) {
							//Draw red markers
							g.drawImage (view.getRedMarker().getImage(), 
									model.getMargin() + j * model.getTileSize(),
									2 * model.getMargin() + i * model.getTileSize(), 
									model.getTileSize(), 
									model.getTileSize(), 
									null);
						} else if (model.getGameBoard()[i][j]== model.YELLOW) {
							//Draw yellow Markers
							g.drawImage (view.getYellowMarker().getImage(), 
									model.getMargin() + j * model.getTileSize(),
									2 * model.getMargin() + i * model.getTileSize(), 
									model.getTileSize(), 
									model.getTileSize(), 
									null);
						}
						//Draw the gameBoard
						g.drawImage (view.getGameBoard().getImage(), 
								model.getMargin() + (j * model.getTileSize()),
								2 * model.getMargin() + i * model.getTileSize(), 
								model.getTileSize(), 
								model.getTileSize(), 
								null);
					}
				}
				
				if (model.getWinSequence()) drawWinSequence(g);
			}
		}
		
		class RestartButtonListener implements ActionListener {
			
			@Override
			/**
			 * Restarts the the game if the restart button is clicked.
			 */
			public void actionPerformed(ActionEvent e) {
				restart();
			}
		}
		
		//new
		class SelectPlayerListener implements ActionListener {
			public void actionPerformed(ActionEvent e){
				
				view.getPlayerName();
			}
		}
		
		
		class PanelListener implements MouseListener {
			
			@Override
			/**
			 * Required to implement by MouseListener.
			 */
			public void mouseClicked(MouseEvent e) {}

			@Override
			/**
			 * Required to implement by MouseListener.
			 */
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			/**
			 * Required to implement by MouseListener.
			 */
			public void mouseExited(MouseEvent e) {}

			@Override
			/**
			 * Responsible for updating the model.
			 * Retrieves and updates user clicks and starts the timer afterwards.
			 */
			public void mousePressed(MouseEvent e) {
				//If a disc is currently falling...return.
				if (model.getTimer().isRunning()) return;
				
				//If the user clicks outside the game board...return.
				if (e.getX() < model.getMargin() 
						|| e.getX() > model.getMargin() + model.getCols() * model.getTileSize()) return;
				
				//If in the win sequence...return.
				if (model.getWinSequence()) return;
				
				//Retrieves the location of where the user clicked.
				model.getClickPoint().y = (int) Math.floor((e.getY() - model.getMargin()) / model.getTileSize());
				model.getClickPoint().x = (int) Math.floor((e.getX() - model.getMargin()) / model.getTileSize());
				
				//If the user clicks a full column...return.
				if (model.getGameBoard()[0][model.getClickPoint().x] != model.EMPTY) return;
				
				setupDroppingDisc();
				
				//Begin the timer.
				model.getTimer().start();
			}

			@Override
			/**
			 * Required to implement by MouseListener.
			 */
			public void mouseReleased(MouseEvent e) {}	
		}
		
		class CursorListener implements MouseMotionListener {
			@Override
			/**
			 * Responsible for updating the model.
			 * Retrieves and updates the location of the user's mouse and
			 * tells the view to redraw.
			 */
			public void mouseMoved(MouseEvent e) {
				model.getMousePoint().x = e.getX();
				view.repaint();
			}
			
			@Override
			/**
			 * Required to implement by MouseMotionListener.
			 */
			public void mouseDragged(MouseEvent e) {}
		}

		@Override
		/**
		 * Called every time the timer fires. Updates the location of the dropping
		 * disc and tells the view to redraw the screen. Stops the timer once
		 * it has reached it's stopping y-coordinate and checks for Connect-Fours
		 * otherwise it switches player turns.
		 */
		public void actionPerformed(ActionEvent e) {
			//Increment Y-coordinate of falling disc.
			this.model.getDroppingDisc().setY(this.model.getDroppingDisc().getY()+10);
			this.view.repaint();
			
			//Check if dropping disc has reached bottom of game board.
			if (this.model.getDroppingDisc().getY() >= this.model.getDroppingDisc().getStopY() + this.model.getDiscSize()) {
				int row = this.model.getDroppingDisc().getStopY() / this.model.getTileSize();
				int col = this.model.getClickPoint().x;
				//Place a disc where the falling disc landed.
				this.model.getGameBoard()[row][col] = this.model.getCurrentColor();
				//Stop the timer.
				this.model.getTimer().stop();
				
				//Check if a Connect-Four is found. Else Switch turns.
				if (checkWin() > 0)
					this.model.setWinSequence(true);
				else
					switchColor();
			}
			
		}
		
		/**
		 * Highlights the Connect-four by drawing a transparent image over the discs
		 * and calls a method to draw the winning text.
		 * @param g
		 */
		public void drawWinSequence(Graphics g) {
			//Draw Connect Four
			for (int i = 0; i < this.model.getConnectFour().length; i++) {
				g.drawImage (view.getWinningMove().getImage(), 
						this.model.getMargin() + (this.model.getConnectFour()[i].y * this.model.getTileSize()),
						2 * this.model.getMargin() + this.model.getConnectFour()[i].x * this.model.getTileSize(), 
						this.model.getTileSize(), 
						this.model.getTileSize(), 
						null);
			}
			
			drawWinText(g);
		}
		
		/**
		 * Draws the winning text at the center of the top of the window.
		 * @param g
		 */
		public void drawWinText(Graphics g) {
			String str;
			g.setFont(view.getFont());
			
			if (this.model.getGameBoard()[this.model.getConnectFour()[0].x][this.model.getConnectFour()[0].y] == this.model.RED) {
				str = "Red Wins";
			} else {
				str = "Yellow Wins";
			}
			
			int strLength = g.getFontMetrics().stringWidth(str);
			int frameWidth = 2 * this.model.getMargin() + this.model.getCols() * this.model.getTileSize();
			
			g.drawString(str, (frameWidth / 2) - (strLength / 2), this.model.getMargin() + this.model.getMargin() / 2);
		}
		
		/**
		 * Responsible for preparing the dropping disc for dropping.
		 * Sets the location where it should be dropped and where it should stop dropping.
		 */
		public void setupDroppingDisc() {
			//Setup the timer.
			this.model.setTimer(new Timer(this.model.getDroppingDisc().getYVelocity(), this));
			//Set the X-Coordinate of disc as the clicked column
			this.model.getDroppingDisc().setX(this.model.getClickPoint().x * this.model.getTileSize());
			//Set Y-Coordinate as top of screen.
			this.model.getDroppingDisc().setY(0);

			//Step through each row of the clicked column.
			//Find the first empty spot and set the location of the dropping disc to stop there.
			for (int i = this.model.getRows() - 1; i >= 0; i--) {
				if (this.model.getGameBoard()[i][this.model.getClickPoint().x] == this.model.EMPTY) {
					this.model.getDroppingDisc().setStopY(i * this.model.getTileSize());
					break;
				}
			}
		}
		
		/**
		 * Switches the current color or turn.
		 */
		public void switchColor() {
			if (this.model.getCurrentColor() == this.model.RED) {
				this.model.setCurrentColor(this.model.YELLOW);
			} else {
				this.model.setCurrentColor(this.model.RED);
			}
		}
		
		/**
		 * Checks for Connect-Fours. Accomplishes this by comparing every possible
		 * starting location for a Connect-Four with its succeeding discs.
		 * @return The color of the connect four or 0 if none was found.
		 */
		public int checkWin() {
			int start = 0;
			//Check horizontal win
			for (int row = this.model.getRows() - 1; row >= 0; row--) {
				for (int col = 0; col < this.model.getCols() - 3; col++) {
					start = this.model.getGameBoard()[row][col];
					if (start != this.model.EMPTY 
							&& start == this.model.getGameBoard()[row][col + 1]
							&& start == this.model.getGameBoard()[row][col + 2]
							&& start == this.model.getGameBoard()[row][col + 3]) {
						for (int i = 0; i < 4; i++) {
							this.model.getConnectFour()[i] = new Point(row, col + i);
						}
						return start;
					}
				}
			}
			
			//Check vertical win
			for (int row = this.model.getRows() - 1; row >= 3; row--) {
				for (int col = 0; col < this.model.getCols(); col++) {
					start = this.model.getGameBoard()[row][col];
					if (start != this.model.EMPTY 
							&& start == this.model.getGameBoard()[row - 1][col]
							&& start == this.model.getGameBoard()[row - 2][col]
							&& start == this.model.getGameBoard()[row - 3][col]) {
						for (int i = 0; i < 4; i++) {
							this.model.getConnectFour()[i] = new Point(row - i, col);
						}
						return start;
					}
				}
			}
			
			//Check diagonal win from bottom left to top right
			for (int row = this.model.getRows() - 1; row >= 3; row--) {
				for (int col = 0; col < this.model.getCols() - 3; col++) {
					start = this.model.getGameBoard()[row][col];
					if (start != this.model.EMPTY 
							&& start == this.model.getGameBoard()[row - 1][col + 1]
							&& start == this.model.getGameBoard()[row - 2][col + 2]
							&& start == this.model.getGameBoard()[row - 3][col + 3]) {
						for (int i = 0; i < 4; i++) {
							this.model.getConnectFour()[i] = new Point(row - i, col + i);
						}
						return start;
					}
				}
			}
			
			//Check diagonal win from bottom right to top left
			for (int row = this.model.getRows() - 1; row >= 3; row--) {
				for (int col = this.model.getCols() - 1; col >= 3; col--) {
					start = this.model.getGameBoard()[row][col];
					if (start != this.model.EMPTY 
							&& start == this.model.getGameBoard()[row-1][col-1]
							&& start == this.model.getGameBoard()[row-2][col-2]
							&& start == this.model.getGameBoard()[row-3][col-3]) {
						for (int i = 0; i < 4; i++) {
							this.model.getConnectFour()[i] = new Point(row - i, col - i);
						}
						return start;
					}
				}
			}
			
			return 0;
		}
		
		/**
		 * Restarts the game by setting all the tiles in the game board to empty and 
		 * sets the current turn to red by default.
		 */
		public void restart() {
			for (int i = 0; i < model.getRows(); i++) {
				for (int j = 0; j < model.getCols(); j++) {
					model.getGameBoard()[i][j] = model.EMPTY;
				}
			}
				
			model.setCurrentColor(model.RED);
			model.setWinSequence(false);
			view.repaint();
		}
	}

