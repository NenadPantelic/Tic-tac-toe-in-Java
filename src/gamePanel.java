//import java.util.*;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;



public class gamePanel extends JPanel implements MouseListener{

	/*

	 For the readers of the code:

	 1) tilesPlays - matrix that represents states - which player turned field. Possible values: {1,2}
	 2) winningRow,winningCol and winningDiagonal - indicators (three positions with the same symbol in the row, columns and diagonal Possible values:>= 0
	 3) tilesOpened - matrix that determines if that field is opened or not. Possible values:{true,false}
	 4) finished - game ending indicator. Possible values:{true,false}

	 */ 

	public static final long serialVersionUID = 1L;
	private int DIMENSION_WIDTH = 300;
	private int DIMENSION_HEIGHT = 300;
	private int OFFSET = 50;

	private int playersTurned[][] = new int [3][3];
	private boolean tilesOpened [][] = new boolean [3][3];
	private int tilePlays[][] = new int[3][3];
	private int player;
	public static int score[] = new int[2];
	private int winner;
	Image[] images = new Image[3];
	ImageIcon[] imageIcons  =  new ImageIcon[3];
	private boolean finished = false;
	private int winningRow = -1,winningCol = -1,winningDiagonal = -1;


	Rectangle rectangles[][] = new Rectangle [3][3];
	int defaultRectDimensions = 100;


	public gamePanel(){


		score[0] = score[1] = 0;
		setSize(DIMENSION_HEIGHT,DIMENSION_WIDTH);
		setVisible(true);

		imageIcons[0]  = new ImageIcon("/home/nenadsi/Java games/Tic-tac-toe/images/x.png");
		imageIcons[1]  = new ImageIcon("/home/nenadsi/Java games/Tic-tac-toe/images/ox.png");
		imageIcons[2] = new ImageIcon("/home/nenadsi/Java games/Tic-tac-toe/images/winner.jpg");
		images[0] = imageIcons[0].getImage();
		images[1] = imageIcons[1].getImage();
		images[2] = imageIcons[2].getImage();

		player = 0;
		winner = 0;

		for (int i = 0;i<3;i++)
			for (int j = 0;j<3;j++){	
				rectangles[i][j] = new Rectangle(i * defaultRectDimensions + OFFSET,j * defaultRectDimensions + OFFSET,defaultRectDimensions,defaultRectDimensions);
				playersTurned[i][j] = 0;
				tilesOpened[i][j] = false;
				tilePlays[i][j] = 0;
			}


		addMouseListener(this);

	}

	//winning indicator - three consecutive places of the same symbol in the row, column or diagonals
	public void winIndicators(){

		int row = 0,col = 0;

		for(int col_iter = 0;col_iter < 3;col_iter++)
			if (tilePlays[row][col_iter] == tilePlays[row+1][col_iter] && tilePlays[row+1][col_iter] == tilePlays[row+2][col_iter] && tilePlays[row][col_iter] > 0){	

				winner = tilePlays[row][col_iter];
				winningCol = col_iter;
				score[winner-1]++;
				break;
			}

		for(int row_iter = 0;row_iter < 3;row_iter++)
			if (tilePlays[row_iter][col] == tilePlays[row_iter][col+1] && tilePlays[row_iter][col+1] == tilePlays[row_iter][col+2] && tilePlays[row_iter][col] > 0){	

				winner = tilePlays[row_iter][col];
				winningRow = row_iter;
				score[winner-1]++;
				break;
			}


		if (tilePlays[0][0] == tilePlays[1][1] && tilePlays[1][1] == tilePlays[2][2] && tilePlays[0][0] > 0){

			winner = tilePlays[0][0];
			winningDiagonal = 0;
			score[winner-1]++;
		}
		if (tilePlays[0][2] == tilePlays[1][1] && tilePlays[1][1] == tilePlays[2][0] && tilePlays[0][2] > 0){

			winner = tilePlays[0][2];
			winningDiagonal = 1;
			score[winner-1]++;
		}

	}



	//win method - display text and images
	public void win(Graphics g){

		winIndicators();

		if (winningRow >= 0 && !finished) {

			for (int i = 0;i < 3;i++) g.drawImage(images[2], winningRow * defaultRectDimensions + 52,i * defaultRectDimensions + 50,this);

			g.setFont(new Font("Arial",Font.BOLD,30));
			g.setColor(Color.yellow);
			g.drawString("Player "+ winner + " wins!", 75, 35);

		}

		if (winningCol >= 0 && !finished) {	
			for (int i = 0;i <3 ;i++) g.drawImage(images[2], i * defaultRectDimensions + 50,winningCol * defaultRectDimensions + 50,this);

			g.setFont(new Font("Arial",Font.BOLD,30));
			g.setColor(Color.yellow);
			g.drawString("Player "+ winner + " wins!", 75, 35);		
			finished = true;	
		}


		if (winningDiagonal == 0 && !finished){

			g.drawImage(images[2], 50,50,this);
			g.drawImage(images[2], 1 * defaultRectDimensions + 50,1 * defaultRectDimensions + 50,this);
			g.drawImage(images[2], 2 * defaultRectDimensions + 50,2 * defaultRectDimensions + 50,this);

			g.setFont(new Font("Arial",Font.BOLD,30));
			g.setColor(Color.yellow);
			g.drawString("Player "+ winner + " wins!", 75, 35);		
			finished = true;

		}

		if (winningDiagonal == 1 && !finished){

			g.drawImage(images[2],2 * defaultRectDimensions + 50 ,50,this);
			g.drawImage(images[2], 1 * defaultRectDimensions + 50,1*defaultRectDimensions + 50,this);
			g.drawImage(images[2],50,2 * defaultRectDimensions + 50,this);		
			finished = true;	
		}

	}



	//x-ox displaying
	public void paint(Graphics g){

		g.setColor(Color.RED);
		g.fillRect(0, 0, 400, 500);
		g.setColor(Color.cyan);
		g.fillRect(50, 50, 300, 300);

		for(int i = 0;i<3;i++)
			for(int j = 0;j<3;j++)
				if (tilePlays[i][j] != 0)
					g.drawImage(images[tilePlays[i][j]-1],i*defaultRectDimensions+50,j*defaultRectDimensions+50,this);


		g.setColor(Color.black);
		g.fillRect(149, 50, 1, 300);
		g.fillRect(249, 50, 1, 300);
		g.fillRect(50, 149, 300, 1);
		g.fillRect(50, 249, 300, 1);
		win(g);


	}


	//eventListener for mouse click
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		if (!finished){

			int clickPositionX = e.getX();
			int clickPositionY = e.getY();
			int tileRow = (clickPositionX + OFFSET) / defaultRectDimensions - 1;
			int tileCol = (clickPositionY + OFFSET) / defaultRectDimensions - 1;
			boolean goodMove = (tileRow >= 0 && tileRow <= 2 && tileCol >= 0 && tileCol <= 2);

			if (goodMove && !tilesOpened[tileRow][tileCol]){

				tilesOpened[tileRow][tileCol] = true;
				player %= 2;
				tilePlays[tileRow][tileCol] = ++player;
				repaint();

			}	
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}


}





