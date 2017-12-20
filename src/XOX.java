/**
 *
 * @author nenadsi
 */

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class XOX extends JFrame implements ActionListener{

	public static final long serialVersionUID = 1L;
	
	public gamePanel panel;
	public XOX(){
		
		panel = new gamePanel();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) dim.getWidth()/3;
		int y = (int) dim.getHeight()/4;
		
		setSize(400,500);
		setLocation(x, y);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		add(panel);
		
		Button reset; 
		reset = new Button("New game");
		reset.setFont(new Font("Arial",Font.BOLD,30));
		reset.setForeground(Color.BLUE);
		reset.addActionListener(this);
		add(reset,BorderLayout.SOUTH);
		
	}
	
	
	@Override
	   public void actionPerformed(ActionEvent evt) {
		   new XOX();
		   this.dispose();
	   }
	
	
	public static void main(String args[]){
		
		new XOX();
		
	}
}
