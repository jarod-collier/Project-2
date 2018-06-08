package package1;

import javax.swing.JFrame;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.event.WindowEvent;

/**********************************************************************
Creates the GUI for the TicTacToe board

@author Jarod Collier and Ben Burger
@version 5/31/2018
**********************************************************************/

public class SuperTicTacToe {
	
	public static void main (String[] args) {

		JFrame frame = new JFrame ("Jarod and Ben's Super TicTacToe!");
		frame.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		frame.setVisible(false);

		SuperTicTacToePanel panel = new SuperTicTacToePanel();
		frame.getContentPane().add(panel);
		
		frame.pack();
//		frame.setSize(1200, 700);
		frame.setVisible(true);
    }
}
